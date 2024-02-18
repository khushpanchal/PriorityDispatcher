package com.khush.prioritydispatcher

import com.khush.prioritydispatcher.internal.CustomPriorityDispatcher
import com.khush.prioritydispatcher.internal.Priority
import com.khush.prioritydispatcher.internal.PriorityRunnable
import kotlinx.coroutines.CoroutineDispatcher
import java.util.concurrent.ExecutorService
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger


object PriorityDispatcher {

    private val cores = Runtime.getRuntime().availableProcessors()
    private const val DEFAULT_INITIAL_CAPACITY = 11
    private val sequence = AtomicInteger(1)

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

    fun low(): CoroutineDispatcher {
        return CustomPriorityDispatcher(
            backgroundTaskExecutorService,
            sequence.incrementAndGet(),
            Priority.LOW
        )
    }

    fun medium(): CoroutineDispatcher {
        return CustomPriorityDispatcher(
            backgroundTaskExecutorService,
            sequence.incrementAndGet(),
            Priority.MEDIUM
        )
    }

    fun high(): CoroutineDispatcher {
        return CustomPriorityDispatcher(
            backgroundTaskExecutorService,
            sequence.incrementAndGet(),
            Priority.HIGH
        )
    }

}




