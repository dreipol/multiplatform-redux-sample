package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal

enum class NavigationActions {
    BACK,
    DASHBOARD
}

data class DisposalsLoaded(val disposals: List<Disposal>)