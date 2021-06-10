/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.redux.middleware

import ch.dreipol.dreimultiplatform.kermit
import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.PrintableAction
import org.reduxkotlin.middleware

fun loggerMiddleware() = middleware<AppState> { _, next, action ->
    val actionLogName =
        when (action) {
            is Function<*> -> "Thunk Function"
            is PrintableAction -> action.toString()
            else -> action::class.simpleName
        }
    kermit().d {
        "\n********************************************\n" +
            "******** DISPATCHED action: $actionLogName\n" +
            "********************************************"
    }
    next(action)
}