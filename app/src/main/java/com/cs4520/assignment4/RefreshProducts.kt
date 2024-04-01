package com.cs4520.assignment4

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class RefreshProducts(
    private val context: Context, workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val appContext = applicationContext
        // Using repository as it covers necessary functionality.
        // Avoids repetitive functionality.
        val repository = ProductRepository(appContext)

        return try {
            val result = repository.getProducts()

            if (result.isSuccess) {
                Result.success()
            } else {
                Result.retry()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }
}