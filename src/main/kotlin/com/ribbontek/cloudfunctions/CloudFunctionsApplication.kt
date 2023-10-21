package com.ribbontek.cloudfunctions

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean

data class HealthFirstMemberRequest(val id: String)
data class HealthFirstMemberResponse(val memberId: String, val coverage: Coverage)
enum class Coverage {
    MEDICAL
}

@SpringBootApplication
@EnableFeignClients
class CloudFunctionsApplication {

    @Bean
    fun members(): (HealthFirstMemberRequest) -> HealthFirstMemberResponse {
        return { member -> HealthFirstMemberResponse(member.id, Coverage.MEDICAL) }
    }
}

fun main(args: Array<String>) {
    runApplication<CloudFunctionsApplication>(*args)
}
