package com.example.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.google.gson.Gson

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

object Utils {

    fun hasConnection(context: Context): Boolean {
        val activeNetworkInfo =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    fun <T> jsonConverter(json: String, clazz: Class<T>): T {
        return Gson().fromJson(json, clazz)
    }

    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}