package ch.brontofundus.demos.quartz.jobs

import ch.brontofundus.demos.quartz.props.EnvPropsConfiguration
import ch.brontofundus.demos.quartz.repository.OrderRepository
import ch.brontofundus.demos.quartz.service.SomeService
import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.quartz.PersistJobDataAfterExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.transaction.annotation.Transactional
import java.util.*

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
open class ProcessOrderJob : QuartzJobBean() {

    @Autowired
    private var envPropsConfiguration: EnvPropsConfiguration? = null

    @Autowired
    private var orderRepository: OrderRepository? = null

    @Autowired
    private var someService: SomeService? = null

    @Transactional
    override fun executeInternal(context: JobExecutionContext) {
        val orderUid : UUID = context.jobDetail.jobDataMap.get(ORDER_UID_KEY) as UUID

        val order = orderRepository!!.getReferenceById(orderUid)
        order.startedAt = Date()

        someService!!.saySomething("${envPropsConfiguration!!.name} Order processing in progress!!!")
        Thread.sleep(2345)

        order.completedAt = Date()
        order.state = "COMPLETED"

        context.jobDetail.jobDataMap.put(EXECUTED_INSTANCE_NAME_KEY, envPropsConfiguration!!.name)
    }

}
