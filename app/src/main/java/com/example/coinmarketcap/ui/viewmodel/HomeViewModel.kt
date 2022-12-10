package com.example.coinmarketcap.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.data.utils.Utils
import com.example.domain.entities.FilterModel
import com.example.domain.entities.SortModel
import com.example.domain.usecase.GetCoinsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Amir mohammad Kheradmand on 11/24/2022.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    val coinsListUseCase: GetCoinsListUseCase
) : ViewModel() {

    fun getFromRoomByPage(page: Int) = liveData(Dispatchers.IO) {
        emit(coinsListUseCase.getFromRoomByPage(page))
//        coinsListUseCase
//            .getFromRoomByPage(page)
//            .distinctUntilChanged()
//            .collect {
//                emit(it)
//            }
    }

    fun requestUpdateDatabase(page: Int) {
        viewModelScope.launch {
            coinsListUseCase.updateDatabaseFromServer(page)
        }
    }

    fun getDatabaseSize() = liveData(Dispatchers.IO) {
        coinsListUseCase.getDatabaseSize().collect {
            emit(it)
        }
    }

    fun getCoinsListByQuery(page: Int, sortModel: SortModel?, filterModel: FilterModel?) =
        liveData(Dispatchers.IO) {
            coinsListUseCase.getCoinsListByQuery(
                page, sortModel, filterModel
            ).collect {
                emit(it)
            }
        }

    fun errorListener() = liveData(Dispatchers.IO) {
        coinsListUseCase.errorListener().collect{
            emit(it)
        }
    }

    fun getCoinsList(page: Int) = liveData(Dispatchers.IO) {
        coinsListUseCase.getCoinsListByFlow(page).collect {
            emit(it)
        }
    }

}