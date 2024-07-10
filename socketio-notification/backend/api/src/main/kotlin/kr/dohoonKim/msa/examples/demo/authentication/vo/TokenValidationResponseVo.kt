package kr.dohoonKim.msa.examples.demo.authentication.vo

import java.time.LocalDateTime

data class TokenValidationResponseVo(
        val issuer: String,
        val expiredAt: LocalDateTime,
        val nickname: String,
        val isValid: Boolean) {
}