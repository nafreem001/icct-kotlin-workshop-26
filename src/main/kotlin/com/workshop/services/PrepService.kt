package com.workshop.services

import com.workshop.data.SampleData
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import java.util.concurrent.ConcurrentHashMap

@Serializable
data class PrepProgress(
    val orderId: String,
    val totalItems: Int,
    val completedItems: Int,
    val inProgress: Boolean,
    val estimatedSecondsRemaining: Int
)

object PrepService {

    // This ConcurrentHashMap tracks the preparation state for each order.
    // ConcurrentHashMap is thread-safe, so multiple coroutines can update it simultaneously.
    private val activePreps = ConcurrentHashMap<String, PrepState>()

    private data class PrepState(
        val totalItems: Int,
        var completedItems: Int = 0,
        val startedAt: Long = System.currentTimeMillis(),
        val delayPerItemMs: Long = 500,
        var job: Job? = null
    )

    // Exercise 2D: Implement this method
    // Start preparing an order by launching concurrent coroutines for each item.
    //
    // Steps:
    // 1. Look up the order in SampleData.orders
    // 2. Calculate totalItems (sum of all item quantities)
    // 3. Create a PrepState and store it in activePreps
    // 4. Use scope.launch to start a parent coroutine
    // 5. Inside, use order.items.flatMap to create one coroutine per unit:
    //      (1..lineItem.quantity).map { launch { delay(delayPerItemMs); state.completedItems++ } }
    // 6. Wait for all item jobs with .forEach { it.join() }
    // 7. Return the current PrepProgress via getPrepStatus()
    //
    // Key concept: launch {} runs concurrently. If you have 6 items each with 500ms delay,
    // they should all complete in ~500ms total (not 3000ms sequentially).
    fun startPreparation(orderId: String, scope: CoroutineScope): Result<PrepProgress> {
        val order = SampleData.orders.find {
            it.id == orderId
        } ?: return Result.failure(Exception("Order not found: $orderId"))

        if (activePreps.containsKey(orderId)) {
            return Result.success(getPrepStatus(orderId)!!)
        }

        val totalItems = order.items.sumOf {
            it.quantity
        }
        val state = PrepState(totalItems = totalItems)
        activePreps[orderId] = state

        // TODO: Launch coroutines to prepare each item concurrently
        // Hint: use scope.launch { } and inside it, create a launch { } for each unit

        return Result.success(getPrepStatus(orderId)!!)
    }

    // Exercise 2D: Implement this method
    // Return the current preparation status for an order.
    // Look up the PrepState in activePreps and convert it to a PrepProgress.
    fun getPrepStatus(orderId: String): PrepProgress? {
        val state = activePreps[orderId] ?: return null

        // TODO: Build and return a PrepProgress from the state
        // - inProgress should be true if completedItems < totalItems
        // - estimatedSecondsRemaining can be a rough estimate
        return null
    }
}
