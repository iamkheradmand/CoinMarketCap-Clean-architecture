package com.example.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.db.AppDatabase
import com.example.data.model.local.CoinInfoEntity
import com.example.domain.CoinRepository
import com.example.domain.entities.ApiResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect

/**
 * Created by Amir mohammad Kheradmand on 11/24/2022.
 */

@HiltWorker
class UpdateDatabaseWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    val repository: CoinRepository,
) : CoroutineWorker(context, workerParams) {

    companion object {
        private const val TAG = "UpdateDatabaseWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            Log.d(TAG, "doWorke")
//            val result = repository.updateDatabaseFromServer(1)
            val result = repository.clearDatabase()
            Log.d(TAG, "Result.success()")
            Result.success()
        } catch (e: Exception) {
            Log.d(TAG, "Error seeding database", e)
            Result.failure()
        }
    }
}