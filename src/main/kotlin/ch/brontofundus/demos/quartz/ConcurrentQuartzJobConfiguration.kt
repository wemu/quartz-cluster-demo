package ch.brontofundus.demos.quartz

import ch.brontofundus.demos.quartz.jobs.ConcurrentQuartzFromConfigJob
import org.quartz.CronScheduleBuilder.cronSchedule
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConcurrentQuartzJobConfiguration {

    @Bean
    @Qualifier("jobDetailConcurrent")
    fun writeNameWithConcurrentQuartzJobDetail(): JobDetail {
        return JobBuilder
            .newJob(ConcurrentQuartzFromConfigJob::class.java)
            .withIdentity("Concurrent-Spring-Conf-NameWriter-Job")
            .withDescription("Concurrent Job configured with Spring @Configuration")
            .storeDurably()
            .requestRecovery()
            .build()
    }

    @Bean
    @Qualifier("triggerConcurrent")
    fun writeNameWithConcurrentQuartzTrigger(@Qualifier("jobDetailConcurrent") jd: JobDetail): Trigger {
        return TriggerBuilder
            .newTrigger()
            .withIdentity("Concurrent-Spring-Conf-NameWriter-Trigger")
            .forJob(jd)
            .withSchedule(cronSchedule("0/10 * * ? * * *"))
            .startNow()
            .build()
    }

}
