package com.ribbontek.cloudfunctions.functions

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.ribbontek.cloudfunctions.functions.TeapotStatus.EMPTY
import com.ribbontek.cloudfunctions.functions.TeapotStatus.FULL
import com.ribbontek.cloudfunctions.functions.TeapotStatus.IN_USE
import com.ribbontek.cloudfunctions.util.fromJson
import com.ribbontek.cloudfunctions.util.logger
import com.ribbontek.cloudfunctions.util.toJson
import jakarta.validation.constraints.NotBlank
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

enum class TeapotStatus {
    EMPTY, FULL, IN_USE
}

data class Teapot(
    @NotBlank
    val id: String,
    var status: TeapotStatus
)

@Component
class ApiGatewayFunctionExample {

    private val log = logger()

    @Bean
    fun teapot(): (APIGatewayProxyRequestEvent) -> APIGatewayProxyResponseEvent {
        return { event ->
            log.info("Found event {}", event)
            val teapot = event.body.fromJson<Teapot>()
            log.info("Teapot with id ${teapot.id} has status ${teapot.status}")
            when (teapot.status) {
                EMPTY -> APIGatewayProxyResponseEvent().withStatusCode(418).withBody("I'm a teapot")
                else -> APIGatewayProxyResponseEvent().withStatusCode(200).withBody(teapot.migrate().toJson())
            }
        }
    }

    private fun Teapot.migrate(): Teapot {
        return when (status) {
            FULL -> apply { status = IN_USE }
            IN_USE -> apply { status = EMPTY }
            EMPTY -> apply { status = FULL }
        }
    }
}
