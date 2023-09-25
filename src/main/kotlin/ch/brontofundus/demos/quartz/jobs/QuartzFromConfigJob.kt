package ch.brontofundus.demos.quartz.jobs

import ch.brontofundus.demos.quartz.props.EnvPropsConfiguration
import ch.brontofundus.demos.quartz.service.SomeService
import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.quartz.QuartzJobBean

@DisallowConcurrentExecution
class QuartzFromConfigJob : QuartzJobBean() {

    @Autowired
    private var envPropsConfiguration: EnvPropsConfiguration? = null
        set
    @Autowired
    private var someService: SomeService? = null
        set

    private val logger = LoggerFactory.getLogger(QuartzFromConfigJob::class.java)

    override fun executeInternal(context: JobExecutionContext) {
        logger.info("${envPropsConfiguration!!.name}: Job (configured via Spring Beans) is STARTING!")

        logger.info("    We Are: ${envPropsConfiguration!!.name} and we pause for ${envPropsConfiguration!!.waitmillis}")
        someService!!.saySomething(envPropsConfiguration!!.name)

        Thread.sleep(envPropsConfiguration!!.waitmillis)

        logger.info("    ${envPropsConfiguration!!.name}: Job (from Spring Config) has FINISHED.")
    }

}
