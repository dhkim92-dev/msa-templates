package kr.dohoonKim.msa.examples.demo.authentication.dto

import java.time.LocalDateTime

class TokenValidationResponseDto(

    val issuer: String,

    val expiredAt: LocalDateTime,

    val nickname: String,

    val isValid: Boolean
){
}