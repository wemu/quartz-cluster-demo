package ch.brontofundus.demos.quartz.service

import ch.brontofundus.demos.quartz.service.dto.JobInfo
import ch.brontofundus.demos.quartz.service.dto.TriggerInfo
import org.quartz.CronTrigger
import org.quartz.Scheduler
import org.quartz.SimpleTrigger
import org.quartz.impl.matchers.GroupMatcher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SchedulerAdministrationService(val scheduler: Scheduler) {

    @Transactional(readOnly = true)
    fun isInStandby(): Boolean {
        return scheduler.isInStandbyMode
    }

    @Transactional
    fun resumeStandby() {
        scheduler.start()
    }

    @Transactional
    fun enableStandby() {
        scheduler.standby()
    }

    @Transactional
    fun pauseAllTriggers() {
        scheduler.pauseAll()
    }

    @Transactional
    fun resumeAllTriggers() {
        scheduler.resumeAll()
    }

    fun allTriggersPaused(): Boolean {
        val allGroupNames: List<String> = scheduler.triggerGroupNames
        val pausedGroupNames: Set<String> = scheduler.pausedTriggerGroups

        return pausedGroupNames.containsAll(allGroupNames)
    }

    @Transactional(readOnly = true)
    fun readAllJobs(): List<JobInfo> {
        val jobInfos = mutableListOf<JobInfo>()
        scheduler.triggerGroupNames
        for (jobGroupName in scheduler.jobGroupNames) {
            for (jobKey in scheduler.getJobKeys(GroupMatcher.jobGroupEquals(jobGroupName))) {
                val jobDetail = scheduler.getJobDetail(jobKey)

                val triggerInfos = mutableListOf<TriggerInfo>()
                val triggers = scheduler.getTriggersOfJob(jobKey)
                for(trigger in triggers) {
                    val triggerState = scheduler.getTriggerState(trigger.key)

                    val info: String = when(trigger) {
                        is CronTrigger -> trigger.cronExpression
                        is SimpleTrigger -> trigger.nextFireTime?.toString() ?: ""
                        else -> trigger.toString()
                    }

                    triggerInfos.add(TriggerInfo(trigger.key, trigger.startTime, triggerState.name, info))
                }

                val ranOnInstances = StringBuilder()
                jobDetail.jobDataMap.entries.forEach {
                    ranOnInstances.append(it.key)
                    ranOnInstances.append("=")
                    ranOnInstances.append(it.value)
                    ranOnInstances.append(", ")
                }

                val disallowConcurrentExecution = jobDetail.isConcurrentExectionDisallowed

                jobInfos.add(JobInfo(
                    jobDetail.key.toString(),
                    jobDetail.description,
                    triggerInfos,
                    ranOnInstances.toString(),
                    disallowConcurrentExecution))
            }
        }

        return jobInfos.sortedBy { jobInfo -> jobInfo.key }
    }

}
