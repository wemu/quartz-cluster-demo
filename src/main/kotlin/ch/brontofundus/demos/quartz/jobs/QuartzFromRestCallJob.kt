package ch.brontofundus.demos.quartz.jobs

import ch.brontofundus.demos.quartz.props.EnvPropsConfiguration
import ch.brontofundus.demos.quartz.service.SomeService
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.quartz.QuartzJobBean

class QuartzFromRestCallJob : QuartzJobBean() {

    @Autowired
    private var envPropsConfiguration: EnvPropsConfiguration? = null
        set

    @Autowired
    private var someService: SomeService? = null
        set

    private val logger = LoggerFactory.getLogger(QuartzFromRestCallJob::class.java)

    override fun executeInternal(context: JobExecutionContext) {
        logger.info("${envPropsConfiguration!!.name}: Job (added via Rest Call) is STARTING!")

        logger.info("    Rest: We Are: ${envPropsConfiguration!!.name} and we pause for ${envPropsConfiguration!!.waitmillis}")
        someService!!.saySomething(envPropsConfiguration!!.name)

        Thread.sleep(envPropsConfiguration!!.waitmillis)

        logger.info("    ${envPropsConfiguration!!.name}: Job (from Rest Call) has FINISHED.")
    }

}
