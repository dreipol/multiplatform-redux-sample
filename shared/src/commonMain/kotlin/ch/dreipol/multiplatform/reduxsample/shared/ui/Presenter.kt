/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.dreimultiplatform.reduxkotlin.*
import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState

typealias BaseView = ViewWithProvider<AppState>

// a Presenter typed to our app's State type for convenience
fun <V : BaseView> presenter(actions: PresenterBuilder<AppState, V>): Presenter<View, AppState> {
    return createGenericPresenter(actions) as Presenter<View, AppState>
}

fun <V : BaseView> presenterWithViewArg(actions: PresenterBuilderWithViewArg<AppState, V>): Presenter<View, AppState> {
    return createGenericPresenter(actions) as Presenter<View, AppState>
}