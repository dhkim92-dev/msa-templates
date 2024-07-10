package kr.dohoonKim.msa.examples.demo.authentication

import kr.dohoonKim.msa.examples.demo.authentication.dto.LoginRequestDto
import kr.dohoonKim.msa.examples.demo.authentication.dto.LoginResponseDto
import kr.dohoonKim.msa.examples.demo.authentication.dto.TokenValidationRequestDto
import kr.dohoonKim.msa.examples.demo.authentication.dto.TokenValidationResponseDto
import kr.dohoonKim.msa.examples.demo.common.error.UnauthorizedException
import kr.dohoonKim.msa.examples.demo.member.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZoneId

@Service
class AuthenticationService(
    private val memberRepository: MemberRepository,

    private val jwtService: JwtService
){

    @Transactional(readOnly = true)
    fun signIn(request: LoginRequestDto): LoginResponseDto {
        val member = this.memberRepository.findByNickname(request.principal)
            ?: throw UnauthorizedException()

        if(member.password != request.credentials) {
            throw UnauthorizedException()
        }

        return LoginResponseDto(token = jwtService.createToken(member.nickname))
    }

    @Transactional(readOnly = true)
    fun verifyToken(request: TokenValidationRequestDto): TokenValidationResponseDto {
        val jwt = jwtService.validateToken(request.token)

        return TokenValidationResponseDto(
            issuer = jwt.issuer,
            expiredAt = jwt.expiresAt
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime(),
            nickname = jwt.subject,
            isValid = true
        )
    }
}