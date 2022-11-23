package com.example.coinmarketcap

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

@HiltAndroidApp
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
    }
}