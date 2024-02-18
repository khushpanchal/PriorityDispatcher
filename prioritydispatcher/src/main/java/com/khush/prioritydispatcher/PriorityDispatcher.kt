package com.khush.prioritydispatcher

import com.khush.prioritydispatcher.PriorityDispatcher.backgroundTaskExecutorService
import com.khush.prioritydispatcher.PriorityDispatcher.immediateTaskExecutorService
import com.khush.prioritydispatcher.internal.CustomPriorityDispatcher
import com.khush.prioritydispatcher.internal.Priority
import com.khush.prioritydispatcher.internal.PriorityRunnable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.Runnable
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.CoroutineContext

/**
 * Priority dispatcher: Execute coroutines based on the priority queue
 *
 * There are two thread pools created:
 *
 * 1. Execution of task based on [Priority]
 *
 * Check [backgroundTaskExecutorService]
 *
 * ```
 * CoroutineScope(PriorityDispatcher.low()).launch {
 *  //write task with low priority (add last in the priority queue)
 * }
 *
 * CoroutineScope(PriorityDispatcher.medium()).launch {
 *  //write task with high priority (add first in the priority queue)
 * }
 *
 * CoroutineScope(PriorityDispatcher.high()).launch {
 *  //write task with medium level priority (default)
 * }
 * ```
 *
 * 2. Execution of immediate task
 *
 * Check [immediateTaskExecutorService]
 *
 * ```
 * CoroutineScope(PriorityDispatcher.immediate()).launch {
 *  //write task with immediate priority (task executed immediately)
 * }
 * ```
 *
 */
object PriorityDispatcher {

    private val cores = Runtime.getRuntime().availableProcessors()
    private const val DEFAULT_INITIAL_CAPACITY = 11
    private val sequence = AtomicInteger(1)

    /**
     * Background task executor service based on [PriorityBlockingQueue]
     *
     * Only [cores] task runs at a time in parallel, additional task gets queued up based on PriorityQueue
     *
     * For any two [PriorityRunnable] preference given based out of [Priority]
     *
     * In case of equal [Priority] task executed in FIFO order
     *
     */
    private val backgroundTaskExecutorService: ExecutorService by lazy {
        ThreadPoolExecutor(
            cores, cores, 0L, TimeUnit.MILLISECONDS,
            PriorityBlockingQueue(DEFAULT_INITIAL_CAPACITY) { o1, o2 ->
                //+ve --> o2>o1, -ve --> o1>o2
                val p1 = o1 as PriorityRunnable
                val p2 = o2 as PriorityRunnable
                if (p1.priority == p2.priority) p1.orderSequence - p2.orderSequence //less orderSequence, high priority (FIFO)
                else p2.priority.ordinal - p1.priority.ordinal //high ordinal, high priority
            }
        )
    }

    /**
     * Immediate task executor service based on [Executors.newCachedThreadPool]
     *
     * Check [Executors.newCachedThreadPool] for more details
     *
     */
    private val immediateTaskExecutorService: ExecutorService by lazy {
        Executors.newCachedThreadPool()
    }

    /**
     * Low: Creates [CustomPriorityDispatcher] with low priority
     *
     * @return [CoroutineDispatcher]
     */
    fun low(): CoroutineDispatcher {
        return CustomPriorityDispatcher(
            backgroundTaskExecutorService,
            sequence.incrementAndGet(),
            Priority.LOW
        )
    }

    /**
     * Medium: Creates [CustomPriorityDispatcher] with medium priority
     *
     * @return [CoroutineDispatcher]
     */
    fun medium(): CoroutineDispatcher {
        return CustomPriorityDispatcher(
            backgroundTaskExecutorService,
            sequence.incrementAndGet(),
            Priority.MEDIUM
        )
    }

    /**
     * High: Creates [CustomPriorityDispatcher] with high priority
     *
     * @return [CoroutineDispatcher]
     */
    fun high(): CoroutineDispatcher {
        return CustomPriorityDispatcher(
            backgroundTaskExecutorService,
            sequence.incrementAndGet(),
            Priority.HIGH
        )
    }

    /**
     * Immediate: Creates [ExecutorCoroutineDispatcher] and execute task on [immediateTaskExecutorService]
     *
     * @return [CoroutineDispatcher]
     */
    fun immediate(): CoroutineDispatcher {
        return object : ExecutorCoroutineDispatcher() {
            override val executor: Executor
                get() = immediateTaskExecutorService

            override fun close() {
                (executor as? ExecutorService)?.shutdown()
            }

            override fun dispatch(context: CoroutineContext, block: Runnable) {
                executor.execute(block)
            }
        }
    }
}




