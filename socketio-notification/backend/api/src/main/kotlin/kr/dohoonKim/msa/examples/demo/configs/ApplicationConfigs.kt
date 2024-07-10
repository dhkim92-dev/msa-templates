package kr.dohoonKim.msa.examples.demo.configs

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.time.format.DateTimeFormatter

@Configuration
class ApplicationConfigs: WebMvcConfigurer{

    @Bean
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder.simpleDateFormat("yyyy-MM-dd")
            builder.serializers(LocalDateSerializer(DateTimeFormatter.ISO_DATE))
            builder.serializers(LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME))
        }
    }
}