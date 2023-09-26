package ch.brontofundus.demos.quartz

import ch.brontofundus.demos.quartz.listener.ConcurrentJobListener
import ch.brontofundus.demos.quartz.props.EnvPropsConfiguration
import org.quartz.Scheduler
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration

@Configuration
class ConcurrentQuartzListenerConfiguration : InitializingBean {

    @Autowired
    lateinit var scheduler: Scheduler

    @Autowired
    lateinit var envPropsConfiguration: EnvPropsConfiguration

    override fun afterPropertiesSet() {
        scheduler.listenerManager.addJobListener(ConcurrentJobListener(envPropsConfiguration))
    }

}
