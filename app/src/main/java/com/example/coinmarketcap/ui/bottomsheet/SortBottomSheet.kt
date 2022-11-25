package com.example.coinmarketcap.ui.bottomsheet

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.util.Pair
import androidx.fragment.app.setFragmentResult
import com.example.coinmarketcap.R
import com.example.coinmarketcap.databinding.BottomsheetSortBinding
import com.example.coinmarketcap.ui.base.BaseBottomSheetDialogFragment
import com.example.data.typedef.SortDirType
import com.example.data.typedef.SortType
import com.example.data.utils.Constants
import com.example.domain.entities.SortModel
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by marshall-mathers on 11/24/2022.
 */

@AndroidEntryPoint
class SortBottomSheet : BaseBottomSheetDialogFragment<BottomsheetSortBinding>() {

    private var sortType = SortType.NAME
    private var sortOrder = SortDirType.ASC

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): BottomsheetSortBinding {
        return BottomsheetSortBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        fillInputs(arguments)
    }

    private fun fillInputs(arguments: Bundle?) {
        arguments?.let {
            val sortModel: SortModel = arguments.getParcelable(Constants.EXTRA_MODEL_SORT)!!
            sortModel.sort?.let {
                when (it) {
                    SortType.NAME -> {
                        binding.name.isChecked = true
                    }
                    SortType.PRICE -> {
                        binding.price.isChecked = true
                    }
                    SortType.NAMARKET_CAPE -> {
                        binding.marketcap.isChecked = true
                    }
                    SortType.CIRCULATING_SUPPLY -> {
                        binding.csupply.isChecked = true
                    }
                    else -> {

                    }
                }
                sortType = it
            }

            sortModel.sort_dir?.let {
                when (it) {
                    SortDirType.ASC -> {
                        binding.ascending.isChecked = true
                    }
                    SortDirType.DESC -> {
                        binding.descending.isChecked = true
                    }

                    else -> {

                    }
                }
                sortOrder = it
            }
        }
    }


    override fun onClickListeners() {

        binding.sortTypeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            sortType = when (checkedId) {
                R.id.name -> {
                    SortType.NAME
                }
                R.id.price -> {
                    SortType.PRICE
                }
                R.id.marketcap -> {
                    SortType.NAMARKET_CAPE
                }
                R.id.csupply -> {
                    SortType.CIRCULATING_SUPPLY
                }
                else -> {
                    SortType.NAME
                }
            }
        }

        binding.orderRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            sortOrder = when (checkedId) {
                R.id.ascending -> {
                    SortDirType.ASC
                }
                R.id.descending -> {
                    SortDirType.DESC
                }
                else -> {
                    SortDirType.ASC
                }
            }
        }

        binding.sortBtn.setOnClickListener {

            Log.e("sortBtn", "RK_BOTTOMSHEET_RESULT: " + sortType + " " + sortOrder)

            setFragmentResult(
                Constants.RK_BOTTOMSHEET_RESULT,
                bundleOf(Constants.EXTRA_MODEL_SORT to SortModel(sortType, sortOrder))
            )
            dismiss()
        }
    }


    companion object {
        val TAG: String = SortBottomSheet::class.java.simpleName
    }

}