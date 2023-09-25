package ch.brontofundus.demos.quartz.service.dto

data class JobInfo(val key: String,
                   val description: String?,
                   val triggerInfos: MutableList<TriggerInfo>)
