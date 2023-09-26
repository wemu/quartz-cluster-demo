package ch.brontofundus.demos.quartz.jobs

import ch.brontofundus.demos.quartz.props.EnvPropsConfiguration
import ch.brontofundus.demos.quartz.service.SomeService
import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.quartz.PersistJobDataAfterExecution
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.quartz.QuartzJobBean

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
class NonConcurrentQuartzFromConfigJob : QuartzJobBean() {

    @Autowired
    private var envPropsConfiguration: EnvPropsConfiguration? = null
        set
    @Autowired
    private var someService: SomeService? = null
        set

    private val logger = LoggerFactory.getLogger(NonConcurrentQuartzFromConfigJob::class.java)

    override fun executeInternal(context: JobExecutionContext) {
        logger.info("${envPropsConfiguration!!.name}: Non-Concurrent Job (configured via Spring Beans) is STARTING!")

        logger.info("    ${envPropsConfiguration!!.name} will pause for ${envPropsConfiguration!!.waitmillis}ms")
        someService!!.saySomething(envPropsConfiguration!!.name)

        Thread.sleep(envPropsConfiguration!!.waitmillis)

        context.jobDetail.jobDataMap.put(EXECUTED_INSTANCE_NAME_KEY + envPropsConfiguration!!.name, envPropsConfiguration!!.name)
        context.jobDetail.jobDataMap.put(LAST_EXECUTED_INSTANCE_NAME_KEY, envPropsConfiguration!!.name)

        logger.info("    ${envPropsConfiguration!!.name}: Non-Concurrent Job (from Spring Config) has FINISHED.")
    }

}
