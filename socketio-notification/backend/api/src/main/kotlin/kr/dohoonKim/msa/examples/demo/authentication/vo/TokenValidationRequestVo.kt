package kr.dohoonKim.msa.examples.demo.authentication.vo

import jakarta.validation.constraints.NotNull

data class TokenValidationRequestVo(
    @field: NotNull
    val token: String=""
){
}