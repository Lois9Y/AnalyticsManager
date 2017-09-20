package at.nineyards.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import at.nineyards.analytics.AnalyticsManager
import at.nineyards.analytics.AnalyticsProviderType

/**
 * Created by Lois-9Y on 19/09/2017.
 */


class MainActivity : AppCompatActivity() {

    var analyticsManager : AnalyticsManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsManager = AnalyticsManager.getInstance()
                ?.initDefinitions(this.javaClass.classLoader.getResourceAsStream("event_definition.json"))
                ?.setProvider(ToastProvider())
        setContentView(R.layout.activity_main)
        send_event.setOnClickListener {
            analyticsManager?.sendEvent("someevent","test",10.0)
        }
    }

    inner class ToastProvider :AnalyticsProviderType{
        override fun logEvent(eventName: String, params: Bundle) {
            val text = "ToastProvider \n logEvent='$eventName'\n"+params.toString()
//            params.keySet().forEach {
//                text += " key=$it value="+params.get(it)+"\n"
//            }
            Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show();
        }
    }
}
