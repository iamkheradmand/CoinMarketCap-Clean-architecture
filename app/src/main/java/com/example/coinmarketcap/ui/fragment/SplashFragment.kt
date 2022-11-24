package com.example.coinmarketcap.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint

import androidx.fragment.app.viewModels
import com.example.coinmarketcap.databinding.FragmentSplashBinding
import com.example.coinmarketcap.ui.base.BaseFragment
import com.example.coinmarketcap.ui.viewmodel.SplashViewModel
import com.example.domain.entities.ApiResult

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val viewModel: SplashViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCoinsList()
    }

    private fun getCoinsList() {
//        viewModel.getCoinsList().observe(viewLifecycleOwner, { result ->
//                when (result) {
//                    is ApiResult.Success -> {
//                        Log.e("SplashFragment", "Success" + result.data!![0].name)
//                    }
//
//                    is ApiResult.Failure -> {
//                        Log.e("SplashFragment", "Failure" + result.message)
//                    }
//                }
//            })

        viewModel.getDB().observe(viewLifecycleOwner, { result ->
            if (result != null && result.size > 0)
                Log.e("SplashFragment", "getDB Success" + result.size)
            else
                Log.e("SplashFragment", "getDB Success is empty")

        })
    }

    override fun onResume() {
        super.onResume()
    }


    private fun goToHome() {
//        if (findNavController().currentDestination!!.id == R.id.splashFragment)
//            navigateTo(R.id.action_splashFragment_to_homeMainFragment)
    }


    override fun onBackPressed() {

    }

    override fun initViews() {

    }

    override fun onClickListeners() {
    }

}