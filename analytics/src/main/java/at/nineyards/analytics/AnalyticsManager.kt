package at.nineyards.analytics

import android.content.Context
import android.os.Bundle
import android.util.Log
import at.nineyards.analytics.model.AnalyticsDefinition
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okio.Okio
import java.io.File
import java.io.InputStream
import java.nio.file.FileSystem
import java.io.BufferedReader
import java.io.InputStreamReader


/**
 * Created by Lois-9Y on 19/09/2017.
 */
class AnalyticsManager(json: InputStream){
    private val TAG = AnalyticsManager::class.java.simpleName
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private var provider :AnalyticsProviderType = DummyProvider()
    private var definition : AnalyticsDefinition?
    private val constantsMap = mutableMapOf<String,String>()
    private val parameterMap = mutableMapOf<String,Pair<String,String>>()
    private val eventMap = mutableMapOf<String,List<String>>()
    init {
        val text = json.bufferedReader().use { it.readText() }
        definition = moshi.adapter(AnalyticsDefinition::class.java).fromJson(text)

        definition?.constantList?.forEach {
            constantsMap.put(it.name,it.value)
        }
        definition?.parameterList?.forEach {
            parameterMap.put(it.reference,Pair(it.name,it.type))
        }
        definition?.eventList?.forEach {
            eventMap.put(it.name,it.parameters)
        }
    }

    fun setProvider(provider : AnalyticsProviderType){
        this.provider = provider
    }

    fun sendEvent(name : String, vararg params: Any){
        val bundle = Bundle()
        eventMap[name]?.withIndex()?.forEach {
            bundle.addAnalyticsParameter(parameterMap[it.value]?.first,parameterMap[it.value]?.second,params[it.index])
        }
        provider.logEvent(name,bundle)
    }

    fun getConstantsMap() :Map<String,String> = constantsMap.toMap()

    private fun Bundle.addAnalyticsParameter(paraName:String?,paraType:String?,paraValue: Any?) {
        if(paraName == null ||paraType == null ||paraValue == null){
            Log.e(TAG, "Invalid Analytics Parameter: name=$paraName, type=$paraType, value=$paraValue")
            return
        }
        converterFunction[paraType]?.invoke(paraName,paraValue,this)
    }

    private val converterFunction = mapOf<String, (String, Any, Bundle) -> Unit>(
            "string" to { paraName, paraValue, bundle -> bundle.putString(paraName, paraValue as String) },
            "int" to { paraName, paraValue, bundle -> bundle.putInt(paraName, paraValue as Int) },
            "long" to { paraName, paraValue, bundle -> bundle.putLong(paraName, paraValue as Long) },
            "double" to { paraName, paraValue, bundle -> bundle.putDouble(paraName, paraValue as Double) },
            "float" to { paraName, paraValue, bundle -> bundle.putFloat(paraName, paraValue as Float) }
    )
}