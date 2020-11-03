package ch.dreipol.multiplatform.reduxsample.utils

import android.content.Context
import androidx.work.*
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.database.Reminder
import ch.dreipol.multiplatform.reduxsample.shared.database.SettingsDataStore
import ch.dreipol.multiplatform.reduxsample.shared.database.getNextReminder
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

@ExperimentalTime
fun updateReminder(context: Context, reminder: Reminder?) {
    val workManager = WorkManager.getInstance(context)
    if (reminder == null) {
        workManager.cancelUniqueWork(ReminderWorker.WORKER_NAME)
        return
    }
    val workRequest = ReminderWorker.createWorkRequest(reminder)
    workManager.enqueueUniqueWork(ReminderWorker.WORKER_NAME, ExistingWorkPolicy.REPLACE, workRequest)
}

@ExperimentalTime
fun setNextReminderIfPresent(context: Context) {
    val settingsDataStore = SettingsDataStore()
    val zip = settingsDataStore.getSettings()?.zip ?: return
    val notification = settingsDataStore.getNotificationSettings().firstOrNull() ?: return
    val reminder = notification.getNextReminder(zip)
    updateReminder(context, reminder)
}

class ReminderWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    companion object {
        const val WORKER_NAME = "REMINDER_WORKER"
        private const val DISPOSAL_TYPES = "DISPOSAL_TYPES"

        @ExperimentalTime
        fun createWorkRequest(reminder: Reminder): OneTimeWorkRequest {
            val initialDelay = reminder.dateTime.toInstant(TimeZone.UTC).minus(Clock.System.now())
            val data = Data.Builder().putStringArray(DISPOSAL_TYPES, reminder.disposalTypes.map { it.name }.toTypedArray()).build()
            return OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(initialDelay.toLongMilliseconds(), TimeUnit.MILLISECONDS)
                .setInputData(data)
                .build()
        }
    }

    @ExperimentalTime
    override fun doWork(): Result {
        val disposalTypes = inputData.getStringArray(DISPOSAL_TYPES)?.map { DisposalType.valueOf(it) } ?: return Result.failure()
        disposalTypes.forEach { showReminderNotification(applicationContext, it) }
        setNextReminderIfPresent(applicationContext)
        return Result.success()
    }
}