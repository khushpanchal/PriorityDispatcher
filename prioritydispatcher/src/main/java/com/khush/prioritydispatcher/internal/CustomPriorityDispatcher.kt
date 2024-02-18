package com.khush.prioritydispatcher.internal

import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.Runnable
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import kotlin.coroutines.CoroutineContext

/**
 * Custom priority dispatcher - On every dispatch, create [PriorityRunnable] and execute on [executor]
 *
 * @property executor ThreadPoolExecutor with PriorityBlockingQueue
 * @property sequence Order at which task gets queued
 * @property priority [Priority] of the task
 */
internal class CustomPriorityDispatcher(
    override val executor: Executor,
    private val sequence: Int,
    private val priority: Priority
) : ExecutorCoroutineDispatcher() {

    override fun close() {
        (executor as? ExecutorService)?.shutdown()
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        val runnable = PriorityRunnable(priority, sequence, block)
        executor.execute(runnable)
    }
}