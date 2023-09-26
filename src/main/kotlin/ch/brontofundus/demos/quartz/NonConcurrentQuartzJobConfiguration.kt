package ch.brontofundus.demos.quartz

import ch.brontofundus.demos.quartz.jobs.NonConcurrentQuartzFromConfigJob
import org.quartz.CronScheduleBuilder.cronSchedule
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NonConcurrentQuartzJobConfiguration {

    @Bean
    @Qualifier("jobDetailNonConcurrent")
    fun writeNameWithNonConcurrentQuartzJobDetail(): JobDetail {
        return JobBuilder
            .newJob(NonConcurrentQuartzFromConfigJob::class.java)
            .withIdentity("Non-Concurrent-Spring-Conf-NameWriter-Job")
            .withDescription("Non-Concurrent Job configured with Spring @Configuration")
            .storeDurably()
            .requestRecovery()
            .build()
    }

    @Bean
    fun writeNameWithNonConcurrentQuartzTrigger(@Qualifier("jobDetailNonConcurrent") jd: JobDetail): Trigger {
        return TriggerBuilder
            .newTrigger()
            .withIdentity("Non-Concurrent-Spring-Conf-NameWriter-Trigger")
            .forJob(jd)
            .withSchedule(cronSchedule("0/10 * * ? * * *"))
            .startNow()
            .build()
    }

}
