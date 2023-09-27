package ch.brontofundus.demos.quartz.repository.entitiy

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "orders")
class Order(
    @Id @Column(name = "uiuiuiid") var uiuiuiid: UUID,
    @Column(name = "state") var state: String,
    @Column(name = "created_at") var createdAt: Date,
    @Column(name = "started_at") var startedAt: Date?,
    @Column(name = "completed_at") var completedAt: Date?,
    @Column(name = "comment") var comment: String
)
