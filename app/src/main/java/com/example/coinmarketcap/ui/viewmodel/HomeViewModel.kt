package com.example.coinmarketcap.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.domain.entities.FilterModel
import com.example.domain.entities.SortModel
import com.example.domain.usecase.GetCoinsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

/**
 * Created by Amir mohammad Kheradmand on 11/24/2022.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(val getCoinsListUseCase: GetCoinsListUseCase) :
    ViewModel() {

    fun getCoinsList(page: Int) = liveData(Dispatchers.IO) {
        Log.e("HomeViewModel", "getCoinsList: $page", )
        getCoinsListUseCase.getCoinsList(page).collect {
            emit(it)
        }
    }

    fun getCoinsListByQuery(page: Int, sortModel: SortModel?, filterModel: FilterModel?) =
        liveData(Dispatchers.IO) {
            Log.e("HomeViewModel", "getCoinsListByQuery")
            getCoinsListUseCase.getCoinsListByQuery(
                page,
                sortModel,
                filterModel
            ).collect {
                emit(it)
            }
        }

    fun getDB() = liveData(Dispatchers.IO) {
        getCoinsListUseCase.getCoinsListLocal().collect {
            emit(it)
        }
    }

    fun getFromRoomByPage(page: Int) = liveData(Dispatchers.IO) {
        getCoinsListUseCase
            .getFromRoomByPage(page)
            .distinctUntilChanged()
            .collect {
                if (it.isNotEmpty())
                    emit(it)
                else
                    getCoinsListUseCase.getCoinsList(page).collect {

                    }
            }
    }

}