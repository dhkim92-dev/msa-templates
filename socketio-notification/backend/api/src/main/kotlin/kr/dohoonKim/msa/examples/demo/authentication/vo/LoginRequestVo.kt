package kr.dohoonKim.msa.examples.demo.authentication.vo

import jakarta.validation.constraints.NotNull

data class LoginRequestVo(

    @field: NotNull
    val nickname: String,

    @field: NotNull
    val password: String
){
}