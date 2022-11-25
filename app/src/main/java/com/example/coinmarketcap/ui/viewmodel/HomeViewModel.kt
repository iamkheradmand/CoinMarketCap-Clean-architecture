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

    /*
    * The data is first received from the database. if the database is empty, it receives the data
    * from the server and updates the database.
    * */
    fun getFromRoomByPage(page: Int) = liveData(Dispatchers.IO) {
        Log.e("HomeFragment", "HomeViewModel page = $page ")
        coinsListUseCase
            .getFromRoomByPage(page)
            .distinctUntilChanged()
            .collect {
//                if (it.isEmpty() && !Utils.hasConnection(context))//dismiss paging loading in offline mode
//                    emit(it)
//                else if (it.isNotEmpty())
//                    emit(it)
//                else {
//                    Log.e("HomeFragment", "HomeViewModel ELSE page = $page ")
//
//                }
                emit(it)
            }
    }

    fun requestUpdateDatabase(page: Int) {
        viewModelScope.launch {
            Log.e("HomeFragment", "HomeViewModel updateDatabaseFromServer page = $page ")
            if (page > 1)
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

    fun getCoinsList(page: Int) = liveData(Dispatchers.IO) {
        coinsListUseCase.getCoinsListByFlow(page).collect {
            emit(it)
        }
    }

}