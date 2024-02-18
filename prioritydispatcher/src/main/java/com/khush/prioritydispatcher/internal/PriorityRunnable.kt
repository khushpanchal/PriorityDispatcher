package com.khush.prioritydispatcher.internal

/**
 * Priority runnable - Implements [Runnable] and pass to the executor for execution based on PriorityQueue
 *
 * @property priority [Priority] of the task
 * @property orderSequence Order at which task gets queued
 * @property runnable Actual Runnable to be executed by Executor
 */
internal class PriorityRunnable(
    val priority: Priority,
    val orderSequence: Int,
    private val runnable: Runnable
) : Runnable {

    override fun run() {
        runnable.run()
    }
}