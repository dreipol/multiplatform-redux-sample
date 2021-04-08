package ch.dreipol.multiplatform.reduxsample.shared.redux

actual fun AppState.freezeState(): AppState {
    return this
}