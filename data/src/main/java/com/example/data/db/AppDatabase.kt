package com.example.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.*
import com.example.data.model.local.CoinInfoEntity
import com.example.data.utils.Constants
import com.example.data.worker.UpdateDatabaseWorker
import com.example.domain.CoinRepository

/**
 * Created by Amir mohammad Kheradmand on 11/24/2022.
 */

@Database(entities = [CoinInfoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao

    companion object {

        private const val TAG = "AppDatabase"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        val constraints = Constraints
                            .Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build()

                        val request = OneTimeWorkRequestBuilder<UpdateDatabaseWorker>()
                            .setConstraints(constraints)
                            .build()
//                        WorkManager.getInstance(context).enqueue(request)
                    }
                }
                )
                .build()
        }
    }

}