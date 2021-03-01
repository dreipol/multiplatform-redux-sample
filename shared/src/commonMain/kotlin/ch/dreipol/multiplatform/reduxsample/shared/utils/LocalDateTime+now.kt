package ch.dreipol.multiplatform.reduxsample.shared.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.Companion.now(): LocalDateTime {
    val instant = Clock.System.now()
    return instant.toLocalDateTime(TimeZone.currentSystemDefault())
}