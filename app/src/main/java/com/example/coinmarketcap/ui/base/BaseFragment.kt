package com.example.coinmarketcap.ui.base

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.coinmarketcap.R
import com.example.data.utils.Utils
import com.google.android.material.button.MaterialButton

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

abstract class BaseFragment<VB : ViewBinding>(val bindingInflater: (layoutInflater: LayoutInflater) -> VB) :
    Fragment(), IBinding<VB> by BindingImpl() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.e("BaseFragment", "onBackPressed")
                onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setVBinding(bindingInflater(inflater))
        return getVBinding()!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        onClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("BaseFragment", "onDestroyView")
        onDestroyViewI()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("BaseFragment", "onDestroy")
    }

    fun navigateTo(destination: Int) {
        findNavController().navigate(destination)
    }

    fun navigateTo(destination: Int, bundle: Bundle) {
        findNavController().navigate(destination, bundle)
    }

    fun finish() {
        findNavController().popBackStack()
    }


    fun isViewNotNull() = getVBinding() != null

    fun hasConnection(): Boolean {
        if (Utils.hasConnection(requireContext()))
            return true
        else
            Utils.showToast(requireContext(), getString(R.string.err_no_connection))
        return false
    }


    abstract fun initViews()
    abstract fun onClickListeners()
    abstract fun onBackPressed()

}