package com.example.data.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

object Utils {

    fun hasConnection(context: Context): Boolean {
        val activeNetworkInfo =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }


}