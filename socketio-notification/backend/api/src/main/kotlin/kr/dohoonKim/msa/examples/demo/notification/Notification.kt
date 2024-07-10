package kr.dohoonKim.msa.examples.demo.notification

import jakarta.persistence.*
import kr.dohoonKim.msa.examples.demo.member.Member
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class Notification(
    @Column
    val message: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    val member: Member
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @CreatedDate
    lateinit var createdAt: LocalDateTime
}