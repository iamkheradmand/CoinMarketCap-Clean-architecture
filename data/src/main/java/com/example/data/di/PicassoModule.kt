package com.example.data.di

import android.content.Context
import android.util.Log
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

@Module
@InstallIn(SingletonComponent::class)
object PicassoModule {

    @Provides
    @Singleton
    fun picasso(
        @ApplicationContext context: Context?,
        okHttp3Downloader: OkHttp3Downloader?
    ): Picasso {
        return Picasso.Builder(context!!)
            .downloader(okHttp3Downloader!!)
            .build()
    }

    @Provides
    fun okHttp3Downloader(okHttpClient: OkHttpClient?): OkHttp3Downloader? {
        return OkHttp3Downloader(okHttpClient)
    }
}