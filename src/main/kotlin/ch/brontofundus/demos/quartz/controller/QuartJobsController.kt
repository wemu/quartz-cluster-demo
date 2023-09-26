package ch.brontofundus.demos.quartz.controller

import ch.brontofundus.demos.quartz.jobs.NonConcurrentQuartzFromRestCallJob
import ch.brontofundus.demos.quartz.jobs.ConcurrentQuartzFromRestCallJob
import ch.brontofundus.demos.quartz.props.EnvPropsConfiguration
import ch.brontofundus.demos.quartz.service.SchedulerAdministrationService
import org.flywaydb.core.Flyway
import org.quartz.*
import org.slf4j.LoggerFactory
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView
import java.time.Instant.now
import java.util.*

@Controller
class QuartJobsController(
    val envPropsConfiguration: EnvPropsConfiguration,
    val flyway: Flyway,
    val schedulerFactoryBean: SchedulerFactoryBean,
    val schedulerAdministrationService: SchedulerAdministrationService) {

    private val logger = LoggerFactory.getLogger(QuartJobsController::class.java)

    init {
        logger.info("NAME: ${envPropsConfiguration.name}")
        logger.info("WAIT: ${envPropsConfiguration.waitmillis}")
    }

    @GetMapping("/name")
    fun readName(): String {
        return envPropsConfiguration.name
    }

    @GetMapping("/flyway")
    fun readFlywayVersion(): String {
        return flyway.info().current().version.version
    }

    @GetMapping("/scheduler/standby")
    fun readSchedulerStatusStandby(): Boolean {
        return schedulerAdministrationService.isInStandby()
    }

    @PostMapping("/scheduler/standby")
    fun enableStandby(model: Model): RedirectView {
        logger.info("Setting scheduler to standby.")
        schedulerAdministrationService.enableStandby()

        return RedirectView("/")
    }

    @PostMapping("/scheduler/resume")
    fun resumeFromStandby(model: Model): RedirectView {
        logger.info("Resume scheduler.")
        schedulerAdministrationService.resumeStandby()

        return RedirectView("/")
    }

    @PostMapping("/scheduler/concurrent-job")
    fun startConcurrentNewJob(): RedirectView {
        logger.info("Starting a new Job on: ${envPropsConfiguration.name}")

        val uuid = UUID.randomUUID().toString()
        val jobIdentity = "PUT-JOB-${envPropsConfiguration.name}-$uuid"

        val job: JobDetail = JobBuilder
            .newJob(ConcurrentQuartzFromRestCallJob::class.java)
            .withIdentity(jobIdentity)
            .requestRecovery()
            .storeDurably()
            .withDescription("concurrent, via a PUT.")
            .build()

        val in15Secs = Date.from(now().plusSeconds(15))

        val trigger: Trigger = TriggerBuilder.newTrigger()
            .startAt(in15Secs)
            .build()

        logger.info("Scheduler Auto Startup: ${schedulerFactoryBean.isAutoStartup}")
        logger.info("Scheduler Running: ${schedulerFactoryBean.isRunning}")

        schedulerFactoryBean.scheduler.scheduleJob(job, trigger)

        return RedirectView("/")
    }

    @PostMapping("/scheduler/non-concurrent-job")
    fun startNewNonConcurrentJob(): RedirectView {
        logger.info("Starting a new non-concurrent Job on: ${envPropsConfiguration.name}")

        val uuid = UUID.randomUUID().toString()
        val jobIdentity = "PUT-JOB-NC-${envPropsConfiguration.name}-$uuid"

        val job: JobDetail = JobBuilder
            .newJob(NonConcurrentQuartzFromRestCallJob::class.java)
            .withIdentity(jobIdentity)
            .requestRecovery()
            .storeDurably()
            .withDescription("non-concurrent, via PUT.")
            .build()

        val in15Secs = Date.from(now().plusSeconds(15))

        val trigger: Trigger = TriggerBuilder.newTrigger()
            .startAt(in15Secs)
            .build()

        logger.info("Scheduler Auto Startup: ${schedulerFactoryBean.isAutoStartup}")
        logger.info("Scheduler Running: ${schedulerFactoryBean.isRunning}")

        schedulerFactoryBean.scheduler.scheduleJob(job, trigger)

        return RedirectView("/")
    }

    @PostMapping("/triggers/standby")
    fun triggersStandby(model: Model): RedirectView {
        logger.info("Triggers to standby.")
        schedulerAdministrationService.pauseAllTriggers()

        return RedirectView("/")
    }

    @PostMapping("/triggers/resume")
    fun triggersResume(model: Model): RedirectView {
        logger.info("Triggers resume.")
        schedulerAdministrationService.resumeAllTriggers()

        return RedirectView("/")
    }

}
