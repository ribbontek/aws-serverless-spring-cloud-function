package com.ribbontek.cloudfunctions.functions

import com.ribbontek.cloudfunctions.client.TodoClient
import com.ribbontek.cloudfunctions.util.logger
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

data class CustomScheduledEvent(
    val ids: List<Int>
)

@Component
class ScheduledFeignExample(
    private val todoClient: TodoClient
) {

    private val log = logger()

    @Bean
    fun scheduledFeignJob(): (CustomScheduledEvent) -> Unit = { event ->
        log.info("Found event $event")
        runBlocking {
            with(todoClient.getTodos()) {
                mapAsync {
                    delay(100) // some processing
                    log.info("Processed todo: $it")
                }
                log.info("Total todos: $size")
            }
        }
    }

    private suspend fun <T, R> Iterable<T>.mapAsync(transform: suspend (T) -> R): List<R> = coroutineScope {
        map { async { transform(it) } }.awaitAll()
    }
}
