package com.example.coinmarketcap.ui.base

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding


/**
 * Created by Amir Mohammad Kheradmand on 12/4/2022.
 */

class BindingImpl<VB : ViewBinding> : IBinding<VB> {

    private var _binding: VB? = null

    override fun setVBinding(vb: VB) {
        Log.e("BindingImpl", "setVBinding")
        _binding = vb
    }

    override fun getVBinding(): VB? {
        return _binding
    }

    override fun onDestroyViewI() {
        Log.e("BindingImpl", "onDestroyViewI")
        _binding = null
    }

    val lifecycleObserver = object : DefaultLifecycleObserver {

        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
            Log.d("BindingImpl", "DefaultLifecycleObserver - onCreate")
        }

        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            Log.d("BindingImpl", "DefaultLifecycleObserver - onDestroy")
        }
    }

    override fun getViewLifecycleObserver(): DefaultLifecycleObserver {
        return lifecycleObserver
    }
}