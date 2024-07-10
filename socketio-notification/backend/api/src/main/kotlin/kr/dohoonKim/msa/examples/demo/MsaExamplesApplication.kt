package kr.dohoonKim.msa.examples.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class MsaExamplesApplication

fun main(args: Array<String>) {
	runApplication<MsaExamplesApplication>(*args)
}
