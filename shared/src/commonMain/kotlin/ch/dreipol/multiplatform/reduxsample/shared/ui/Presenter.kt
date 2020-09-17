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