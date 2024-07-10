package kr.dohoonKim.msa.examples.demo.notification

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository : JpaRepository<Notification, Long> {

    fun findByMemberIdOrderByCreatedAtDesc(memberId: Long) : List<Notification>

}