package at.nineyards.analytics

import android.content.Context
import android.os.Bundle

/**
 * Created by Lois-9Y on 19/09/2017.
 */
interface AnalyticsProviderType{
    fun logEvent(eventName :String, params : Bundle)
}