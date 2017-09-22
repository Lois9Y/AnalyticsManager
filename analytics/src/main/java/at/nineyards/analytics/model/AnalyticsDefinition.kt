package at.nineyards.analytics.model

import com.squareup.moshi.Json

/**
 * Created by Lois-9Y on 19/09/2017.
 */
data class AnalyticsDefinition(
        @Json(name = "event_list") val eventList : List<AnalyticsEvent>,
        @Json(name = "parameter_list") val parameterList : List<AnalyticsParameter>,
        @Json(name = "constant_list") val constantList : List<AnalyticsConstant>
)