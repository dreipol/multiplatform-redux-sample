package ch.dreipol.multiplatform.reduxsample.shared.redux.middleware

import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.OpenedWithReminderNotification
import ch.dreipol.multiplatform.reduxsample.shared.utils.SettingsHelper
import ch.dreipol.multiplatform.reduxsample.shared.utils.getAppConfiguration
import org.reduxkotlin.middleware

fun storeRatingMiddleware() = middleware<AppState> { _, next, action ->

    when (action) {
        is OpenedWithReminderNotification -> {
            if (SettingsHelper.hasRatingShown().not()) {
                showRating()
            }
            Unit
        }
        else -> next(action)
    }
}

private fun showRating() {
    getAppConfiguration().platformFeatures.showRatingDialog()
    SettingsHelper.setRatingShowed(true)
}