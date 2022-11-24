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
            when (sortModel.sort) {
                SortType.NAME -> {
                    binding.nameAsc.isChecked = true
                }
                SortType.PRICE -> {
                    binding.priceAsc.isChecked = true
                }
                SortType.NAMARKET_CAPE -> {
                    binding.marketcapAsc.isChecked = true
                }
                SortType.CIRCULATING_SUPPLY -> {
                    binding.csupplyAsc.isChecked = true
                }

                else -> {

                }
            }
        }
    }


    override fun onClickListeners() {

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val sortModel = when (checkedId) {
                R.id.name_asc -> {
                    SortModel(SortType.NAME, SortDirType.ASC)
                }
                R.id.price_asc -> {
                    SortModel(SortType.PRICE, SortDirType.ASC)
                }
                R.id.marketcap_asc -> {
                    SortModel(SortType.NAMARKET_CAPE, SortDirType.ASC)
                }
                R.id.csupply_asc -> {
                    SortModel(SortType.CIRCULATING_SUPPLY, SortDirType.ASC)
                }
                else -> {
                    SortModel(SortType.NAME, SortDirType.ASC)
                }
            }

            setFragmentResult(Constants.RK_BOTTOMSHEET_RESULT, bundleOf(Constants.EXTRA_MODEL_SORT to sortModel))
            dismiss()
        }
    }


    companion object {
        val TAG: String = SortBottomSheet::class.java.simpleName
    }

}