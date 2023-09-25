package ch.brontofundus.demos.quartz

import ch.brontofundus.demos.quartz.jobs.QuartzFromConfigJob
import org.quartz.CronScheduleBuilder.cronSchedule
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuartzJobConfiguration {

    @Bean
    fun writeNameWithQuartzJobDetail(): JobDetail {
        return JobBuilder
            .newJob(QuartzFromConfigJob::class.java)
            .withIdentity("Quartz-From-Spring-Conf-NameWriter-Job")
            .withDescription("Job configured in Spring @Configuration class")
            .storeDurably()
            .requestRecovery()
            .build()
    }

    @Bean
    fun writeNameWithQuartzTrigger(jd: JobDetail): Trigger {
        return TriggerBuilder
            .newTrigger()
            .withIdentity("Quartz-From-Spring-Conf-NameWriter-Trigger")
            .forJob(jd)
            .withSchedule(cronSchedule("0/20 * * ? * * *"))
            .startNow()
            .build()
    }

}
