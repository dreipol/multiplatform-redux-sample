package ch.dreipol.rezhycle.utils

import io.ktor.http.*

object GoogleMapsUrlBuilder {

    fun getUrlToRoute(origin: String?, destination: String): String {
        val builder = getBaseRouteUrl()
        origin?.let { builder.parameters.append("origin", origin) }
        builder.parameters.append("destination", destination)
        builder.parameters.append("travelmode", "driving")
        return builder.buildString()
    }

    private fun getBaseRouteUrl(): URLBuilder {
        val builder = URLBuilder(protocol = URLProtocol.HTTPS, host = "www.google.com").path("maps", "dir/")
        builder.parameters.append("api", "1")
        return builder
    }
}