package kr.dohoonKim.msa.examples.demo.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import kr.dohoonKim.msa.examples.demo.common.error.UnauthorizedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Date

@Service
class JwtService(
    @Value("\${jwt.secret}")
    private val secret: String
){
    private val issuer = "identification.dohoon-kim.kr"

    val verifier: JWTVerifier = JWT.require(Algorithm.HMAC256(secret))
        .withIssuer(issuer)
        .build()

    fun createToken(nickname: String): String {
        val now = Date()

        return JWT.create()
            .withIssuer("identification.dohoon-kim.kr")
            .withSubject(nickname)
            .withExpiresAt(Date(now.time + 24*3600*1000L))
            .sign(Algorithm.HMAC256(secret))
    }

    fun validateToken(token: String): DecodedJWT {
        var decodedJwt : DecodedJWT?
        try {
            decodedJwt = verifier.verify(token)
        } catch(e: Exception) {
            e.printStackTrace()
            throw UnauthorizedException()
        }

        return decodedJwt
    }
}