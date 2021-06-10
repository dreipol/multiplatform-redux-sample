/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

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