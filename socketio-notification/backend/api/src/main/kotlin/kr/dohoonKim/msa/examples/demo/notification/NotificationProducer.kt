package kr.dohoonKim.msa.examples.demo.notification

import com.fasterxml.jackson.databind.ObjectMapper
import kr.dohoonKim.msa.examples.demo.notification.dto.NotificationSendResponseDto
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class NotificationProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {

    private val TOPIC = "notification-event"

    fun publishNotificationEvent(message: NotificationSendResponseDto) {
        kafkaTemplate.send(TOPIC, message.createdAt.toString(), objectMapper.writeValueAsString(message))
    }
}