package ch.brontofundus.demos.quartz.scheduler

import ch.brontofundus.demos.quartz.props.EnvPropsConfiguration
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SpringScheduledJobs(val envPropsConfiguration: EnvPropsConfiguration) {

    private val logger = LoggerFactory.getLogger(SpringScheduledJobs::class.java)

    @Scheduled(cron = "51 * * * * *")
    fun tellLogOurName() {
        logger.info("@Scheduled Method, for name: ${envPropsConfiguration.name}")
    }

}
