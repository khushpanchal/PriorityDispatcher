package com.khush.prioritydispatcher.internal

/**
 * Priority - Three levels of Priority: [LOW], [MEDIUM], [HIGH]
 * Order of enum class is important, as it is used for comparison in priority queue
 *
 */
internal enum class Priority {
    /**
     * Low level Priority
     *
     */
    LOW,

    /**
     * Medium level Priority
     *
     */
    MEDIUM,

    /**
     * High level Priority
     *
     */
    HIGH
}