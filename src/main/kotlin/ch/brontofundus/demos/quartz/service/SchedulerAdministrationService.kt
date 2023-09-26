package ch.brontofundus.demos.quartz.service

import ch.brontofundus.demos.quartz.jobs.EXECUTED_INSTANCE_NAME_KEY
import ch.brontofundus.demos.quartz.service.dto.JobInfo
import ch.brontofundus.demos.quartz.service.dto.TriggerInfo
import org.quartz.*
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

                val executedOnInstance = jobDetail.jobDataMap.getString(EXECUTED_INSTANCE_NAME_KEY)

                jobInfos.add(JobInfo(jobDetail.key.toString(), jobDetail.description, triggerInfos, executedOnInstance))
            }
        }

        return jobInfos.sortedBy { jobInfo -> jobInfo.key }
    }

}
