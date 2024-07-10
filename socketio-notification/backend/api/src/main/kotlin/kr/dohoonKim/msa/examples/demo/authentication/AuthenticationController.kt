package kr.dohoonKim.msa.examples.demo.authentication

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kr.dohoonKim.msa.examples.demo.authentication.dto.LoginRequestDto
import kr.dohoonKim.msa.examples.demo.authentication.dto.TokenValidationRequestDto
import kr.dohoonKim.msa.examples.demo.authentication.vo.LoginRequestVo
import kr.dohoonKim.msa.examples.demo.authentication.vo.LoginResponseVo
import kr.dohoonKim.msa.examples.demo.authentication.vo.TokenValidationRequestVo
import kr.dohoonKim.msa.examples.demo.authentication.vo.TokenValidationResponseVo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/")
@Tag(name = "Authentication API")
class AuthenticationController(private val authenticationService: AuthenticationService){

    @Operation(summary = "로그인", description = "로그인")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "로그인 성공"),
    ])
    @PostMapping("sign-in")
    fun login(@RequestBody request: LoginRequestVo): LoginResponseVo {
        val responseDto = authenticationService.signIn(request = LoginRequestDto(principal = request.nickname, credentials = request.password))
        return LoginResponseVo(token = responseDto.token)
    }

    @Operation(summary = "JWT 토큰을 검증한다.", description = "JWT 토큰 검증")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "정상 토큰"),
    ])
    @PostMapping("/validation/jwt")
    fun validateJwt(@RequestBody request: TokenValidationRequestVo) : TokenValidationResponseVo {
        println(request.toString())
        val responseDto = authenticationService.verifyToken(TokenValidationRequestDto(
            token = request.token
        ))

        return TokenValidationResponseVo(
            issuer = responseDto.issuer,
            nickname = responseDto.nickname,
            expiredAt = responseDto.expiredAt,
            isValid = true
        )
    }

}