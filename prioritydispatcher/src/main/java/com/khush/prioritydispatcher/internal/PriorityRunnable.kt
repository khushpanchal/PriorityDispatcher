package com.khush.prioritydispatcher.internal

internal class PriorityRunnable(
    val priority: Priority,
    val orderSequence: Int,
    private val runnable: Runnable
) : Runnable {

    override fun run() {
        runnable.run()
    }
}