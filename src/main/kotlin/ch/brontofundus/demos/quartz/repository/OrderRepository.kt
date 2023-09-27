package ch.brontofundus.demos.quartz.repository

import ch.brontofundus.demos.quartz.repository.entitiy.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderRepository : JpaRepository<Order, UUID>
