package com.example.coinmarketcap.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding


/**
 * Created by Amir Mohammad Kheradmand on 12/4/2022.
 */

interface IBinding<VB : ViewBinding> {

    fun getVBinding() : VB?

    fun setVBinding(vb : VB)

    fun onDestroyViewI()

    fun getViewLifecycleObserver():DefaultLifecycleObserver

}