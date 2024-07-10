package kr.dohoonKim.msa.examples.demo.notification

import com.fasterxml.jackson.databind.ObjectMapper
import kr.dohoonKim.msa.examples.demo.common.error.MemberNotFoundException
import kr.dohoonKim.msa.examples.demo.member.MemberRepository
import kr.dohoonKim.msa.examples.demo.notification.dto.NotificationSendRequestDto
import kr.dohoonKim.msa.examples.demo.notification.dto.NotificationSendResponseDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class NotificationService(
    private val memberRepository: MemberRepository,
    private val notificationRepository: NotificationRepository,
){

    @Transactional
    fun sendMessage(request: NotificationSendRequestDto): NotificationSendResponseDto {

        val receiver = memberRepository.findByNickname(request.receiver)
            ?: throw MemberNotFoundException()

        val notification = notificationRepository.save(Notification(member = receiver, message = request.msg))

        return NotificationSendResponseDto(
            receiver = notification.member.nickname,
            message = notification.message,
            createdAt = notification.createdAt
        )
    }
}