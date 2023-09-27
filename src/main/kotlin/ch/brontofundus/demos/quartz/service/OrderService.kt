package ch.brontofundus.demos.quartz.service

import ch.brontofundus.demos.quartz.controller.dto.OrderDto
import ch.brontofundus.demos.quartz.jobs.ORDER_UID_KEY
import ch.brontofundus.demos.quartz.jobs.ProcessOrderJob
import ch.brontofundus.demos.quartz.props.EnvPropsConfiguration
import ch.brontofundus.demos.quartz.repository.OrderRepository
import ch.brontofundus.demos.quartz.repository.entitiy.Order
import org.quartz.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class OrderService(
    val orderRepository: OrderRepository,
    val scheduler: Scheduler,
    var envPropsConfiguration: EnvPropsConfiguration
) {
    @Transactional
    fun createNewOrder() {
        val orderUid = UUID.randomUUID()

        val uuid = UUID.randomUUID().toString()
        val jobIdentity = "PUT-JOB-ORDER-NC-${envPropsConfiguration.name}-$uuid"

        val job: JobDetail = JobBuilder
            .newJob(ProcessOrderJob::class.java)
            .withIdentity(jobIdentity)
            .requestRecovery()
            .storeDurably()
            .withDescription("non-concurrent, via OrderService.")
            .build()
        job.jobDataMap[ORDER_UID_KEY] = orderUid

        val in90Secs = Date.from(Instant.now().plusSeconds(90))
        val trigger: Trigger = TriggerBuilder.newTrigger()
            .startAt(in90Secs)
            .build()

        val newOrder = Order(
            orderUid,
            "NEW",
            Date(),
            null,
            null,
            "Trigger=${trigger.key} // Job=${jobIdentity}"
        )
        orderRepository.save(newOrder)

        scheduler.scheduleJob(job, trigger)
    }

    fun readAllOrders(): List<OrderDto> {
        val orders = orderRepository.findAll()

        return orders.map { order ->  OrderDto(
            order.uiuiuiid.toString(),
            order.state,
            order.createdAt,
            order.startedAt,
            order.completedAt,
            order.comment
        )}
    }

}
