package at.nineyards.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Created by Lois-9Y on 19/09/2017.
 */
class FirebaseProvider(appContext : Context) : AnalyticsProviderType{

    private var firebaseAnalytics = FirebaseAnalytics.getInstance(appContext)

    override fun logEvent(eventName: String, params: Bundle) {
        firebaseAnalytics.logEvent(eventName,params)
    }
}