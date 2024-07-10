package kr.dohoonKim.msa.examples.demo.member

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long
) {

    var nickname: String = ""
    protected set

    var password: String = ""
    protected set

    fun changePassword(password: String) {
        this.password = password
    }
}