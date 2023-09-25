package ch.brontofundus.demos.quartz.service.dto

import org.quartz.TriggerKey
import java.util.*

data class TriggerInfo(val key: TriggerKey, val startTime: Date, val state: String, val schedule: String)
