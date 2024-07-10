package kr.dohoonKim.msa.examples.demo.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {

    fun findByNickname(nickname: String): Member?
}