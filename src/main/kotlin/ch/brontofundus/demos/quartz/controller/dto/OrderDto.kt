package ch.brontofundus.demos.quartz.controller.dto

import java.util.*

data class OrderDto(
    val id: String,
    val state: String,
    val createdAt: Date,
    val startedAt: Date?,
    val completedAt: Date?,
    val comment: String
)