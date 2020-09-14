package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal

enum class NavigationActions {
    BACK,
    DASHBOARD
}

data class DisposalsChanged(val disposals: List<Disposal>)

class DisposalsSynced