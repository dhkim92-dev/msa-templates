package kr.dohoonKim.msa.examples.demo.notification.dto

import java.time.LocalDateTime

class NotificationSendResponseDto(
    val message: String,
    val receiver: String,
    val createdAt: LocalDateTime) {
}