package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.delight.NotificationSettings
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings

data class SettingsLoadedAction(val settings: Settings, val notificationSettings: List<NotificationSettings>)

data class ZipUpdatedAction(val zip: Int?)

data class UpdateShowDisposalType(val disposalType: DisposalType, val show: Boolean)