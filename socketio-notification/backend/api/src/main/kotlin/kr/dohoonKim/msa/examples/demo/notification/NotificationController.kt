package kr.dohoonKim.msa.examples.demo.notification

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kr.dohoonKim.msa.examples.demo.notification.dto.NotificationSendRequestDto
import kr.dohoonKim.msa.examples.demo.notification.vo.NotificationSendRequestVo
import kr.dohoonKim.msa.examples.demo.notification.vo.NotificationSendResponseVo
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/")
@Tag(name = "Notification API")
class NotificationController(private val notificationService: NotificationService){

    @Operation(summary = "노티피케이션 전송", description = "특정 사용자에게 노티피케이션을 전송한다.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "노티피케이션 전송 성공"),
    ])
    @PostMapping("notification")
    @ResponseStatus(code = HttpStatus.CREATED)
    fun sendNotification(@RequestBody request: NotificationSendRequestVo): NotificationSendResponseVo {
        val responseDto = notificationService.sendMessage(request = NotificationSendRequestDto(receiver = request.receiver, msg = request.msg))
        notificationService.publishSendNotificationRedisTopic(responseDto)
        return NotificationSendResponseVo(responseDto.receiver, responseDto.message, responseDto.createdAt)
    }
}