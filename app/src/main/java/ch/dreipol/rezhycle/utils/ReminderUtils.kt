/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.rezhycle.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.*
import ch.dreipol.multiplatform.reduxsample.shared.database.*
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

@ExperimentalTime
fun updateReminders(context: Context, reminders: List<Reminder>) {
    val workManager = WorkManager.getInstance(context)
    workManager.cancelAllWorkByTag(ReminderWorker.WORKER_TAG_NAME)
    if (reminders.isEmpty()) {
        return
    }
    reminders.forEach {
        val workRequest = ReminderWorker.createWorkRequest(context, it)
        workManager.enqueue(workRequest)
    }
}

@ExperimentalTime
fun setNextReminders(context: Context) {
    val settingsDataStore = SettingsDataStore()
    val zip = settingsDataStore.getSettings()?.zip ?: return
    val notification = settingsDataStore.getNotificationSettings().firstOrNull() ?: return
    val reminders = notification.getNextReminders(zip)
    updateReminders(context, reminders)
}

class ReminderWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    companion object {
        const val WORKER_TAG_NAME = "REMINDER_WORKER"
        private const val DISPOSAL_TYPES = "DISPOSAL_TYPES"
        private const val NOTIFICATION_TEXTS = "NOTIFICATION_TEXTS"

        @ExperimentalTime
        fun createWorkRequest(context: Context, reminder: Reminder): OneTimeWorkRequest {
            val initialDelay = reminder.remindDateTime.toInstant(TimeZone.currentSystemDefault()).minus(Clock.System.now())
            val data = Data.Builder()
                .putStringArray(DISPOSAL_TYPES, reminder.disposals.map { it.disposalType.name }.toTypedArray())
                .putStringArray(
                    NOTIFICATION_TEXTS,
                    reminder.disposals.map { getReminderNotificationText(context, it) }.toTypedArray()
                )
                .build()
            return OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(initialDelay.toLongMilliseconds(), TimeUnit.MILLISECONDS)
                .setInputData(data)
                .addTag(WORKER_TAG_NAME)
                .build()
        }
    }

    @ExperimentalTime
    override fun doWork(): Result {
        val disposalTypes = inputData.getStringArray(DISPOSAL_TYPES)?.map { DisposalType.valueOf(it) } ?: return Result.failure()
        val notificationTexts = inputData.getStringArray(NOTIFICATION_TEXTS)?.toList() ?: return Result.failure()
        for (i in disposalTypes.indices) {
            showReminderNotification(applicationContext, disposalTypes[i], notificationTexts[i])
        }
        setNextReminders(applicationContext)
        return Result.success()
    }
}

class BootReceiver : BroadcastReceiver() {
    @ExperimentalTime
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) {
            return
        }
        setNextReminders(context)
    }
}