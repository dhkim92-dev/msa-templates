package kr.dohoonKim.msa.examples.demo.notification.vo

import java.time.LocalDateTime

data class NotificationSendResponseVo(
    val receiver: String,
    val message: String,
    val createdAt: LocalDateTime
) {
}