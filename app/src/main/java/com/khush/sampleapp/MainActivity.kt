package com.khush.sampleapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.khush.prioritydispatcher.PriorityDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testing()
    }

    private fun testing() {
        val currTime = System.currentTimeMillis()
        //Task 1 to 8 will run first irrespective of priority as it is executed first and busy all 8 threads in pool
        //All task (except immediate) will then add into the queue based on priority (high > medium > low)
        for (i in 1..8) {
            lifecycleScope.launch(PriorityDispatcher.medium()) {
                if (isActive) {
                    log("Task $i executed - Medium priority at ${(System.currentTimeMillis() - currTime)/1000}th second")
                    longRunningTask()
                }
            }
        }

        for (i in 9..16) {
            lifecycleScope.launch(PriorityDispatcher.low()) {
                if (isActive) {
                    log("Task $i executed - Low priority at ${(System.currentTimeMillis() - currTime)/1000}th second")
                    longRunningTask()
                }
            }
        }

        for (i in 17..24) {
            lifecycleScope.launch(PriorityDispatcher.medium()) {
                if (isActive) {
                    log("Task $i executed - Medium priority at ${(System.currentTimeMillis() - currTime)/1000}th second")
                    longRunningTask()
                }
            }
        }

        for (i in 25..32) {
            lifecycleScope.launch(PriorityDispatcher.high()) {
                if (isActive) {
                    log("Task $i executed - High priority at ${(System.currentTimeMillis() - currTime)/1000}th second")
                    longRunningTask()
                }
            }
        }

        //Task 33 to 40 will run immediately irrespective of anything
        for (i in 33..40) {
            lifecycleScope.launch(PriorityDispatcher.immediate()) {
                if (isActive) {
                    log("Task $i executed - Immediate priority at ${(System.currentTimeMillis() - currTime)/1000}th second")
                    longRunningTask()
                }
            }
        }
    }

    private fun longRunningTask() {
        Thread.sleep(5000) //2 seconds
    }

    private suspend fun longRunningTaskWithSuspension() {
        Thread.sleep(2000)
        withContext(Dispatchers.IO) { Thread.sleep(1000) }
        Thread.sleep(1000)
    }

    private fun log(msg: Any?, tag: String = "Testing") {
        Log.i(tag, msg.toString())
    }
}