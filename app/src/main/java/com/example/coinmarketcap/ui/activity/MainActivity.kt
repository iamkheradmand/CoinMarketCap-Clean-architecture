package com.example.coinmarketcap.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coinmarketcap.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

}