package at.nineyards.analytics

import android.os.Bundle
import android.util.Log

/**
 * Created by Lois-9Y on 19/09/2017.
 */
internal class DummyProvider : AnalyticsProviderType{
    override fun logEvent(eventName: String, params: Bundle) {
        System.out.println("DummyProvider \n logEvent='$eventName'")
    }
}