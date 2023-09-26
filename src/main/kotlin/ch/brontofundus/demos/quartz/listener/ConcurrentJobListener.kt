package ch.brontofundus.demos.quartz.listener

import ch.brontofundus.demos.quartz.props.EnvPropsConfiguration
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.quartz.JobListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ConcurrentJobListener(val envPropsConfiguration: EnvPropsConfiguration) : JobListener {

    val logger: Logger = LoggerFactory.getLogger(ConcurrentJobListener::class.java)

    override fun getName(): String {
        return "concurrent-job-listener"
    }

    override fun jobToBeExecuted(context: JobExecutionContext?) {

    }

    override fun jobExecutionVetoed(context: JobExecutionContext?) {

    }

    override fun jobWasExecuted(context: JobExecutionContext?, jobException: JobExecutionException?) {
        logger.info(envPropsConfiguration.name + ": job with key '${context!!.jobDetail.key}' was executed.")
    }

}
