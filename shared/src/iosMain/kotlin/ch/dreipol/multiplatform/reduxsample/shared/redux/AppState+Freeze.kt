package ch.dreipol.multiplatform.reduxsample.shared.redux

import kotlin.native.concurrent.freeze

actual fun AppState.freezeState(): AppState {
    return this.freeze()
}