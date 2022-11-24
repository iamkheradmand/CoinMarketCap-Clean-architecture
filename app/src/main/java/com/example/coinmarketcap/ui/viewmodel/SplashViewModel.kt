package com.example.coinmarketcap.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.domain.usecase.GetCoinsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

@HiltViewModel
class SplashViewModel @Inject constructor (val getCoinsListUseCase: GetCoinsListUseCase) : ViewModel() {

    fun getCoinsList() = liveData(Dispatchers.IO) {
        getCoinsListUseCase.getCoinsList().collect {
            emit(it)
        }
    }

    fun getDB() = liveData(Dispatchers.IO) {
        getCoinsListUseCase.getCoinsListLocal().collect {
            emit(it)
        }
    }

}