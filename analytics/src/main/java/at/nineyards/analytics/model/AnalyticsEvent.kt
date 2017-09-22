package at.nineyards.analytics.model

/**
 * Created by Lois-9Y on 19/09/2017.
 */
data class AnalyticsEvent(
        val name: String,
        val parameters: List<String>?
)