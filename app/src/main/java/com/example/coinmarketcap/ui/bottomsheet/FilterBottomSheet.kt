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
import com.example.coinmarketcap.databinding.BottomsheetFilterBinding
import com.example.coinmarketcap.databinding.BottomsheetSortBinding
import com.example.coinmarketcap.ui.base.BaseBottomSheetDialogFragment
import com.example.data.typedef.SortDirType
import com.example.data.typedef.SortType
import com.example.data.utils.Constants
import com.example.domain.entities.FilterModel
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
class FilterBottomSheet : BaseBottomSheetDialogFragment<BottomsheetFilterBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): BottomsheetFilterBinding {
        return BottomsheetFilterBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        fillInputs(arguments)
    }

    private fun fillInputs(arguments: Bundle?) {
        arguments?.let {
            Log.e(TAG, "fillInputs: arguments")
            val filterModel: FilterModel = arguments.getParcelable(Constants.EXTRA_MODEL_FILTER)!!
            filterModel.percent_change_24_min?.let {
                Log.e(TAG, "fillInputs: percent_change_24_min")
                binding.percentSlider.setValues(filterModel.percent_change_24_min?.toFloat(), filterModel.percent_change_24_max?.toFloat())
            }

            filterModel.volume_24_min?.let {
                Log.e(TAG, "fillInputs: volume_24_min")
                binding.volumeSlider.setValues(filterModel.volume_24_min?.toFloat(), filterModel.volume_24_max?.toFloat())
            }
        }
    }

    override fun onClickListeners() {

        binding.filterBtn.setOnClickListener {
            val percent = binding.percentSlider.values
            val volume = binding.volumeSlider.values
            val filterModel = FilterModel(
                percent_change_24_min = percent[0].toLong(),
                percent_change_24_max = percent[1].toLong(),
                volume_24_min = volume[0].toLong(),
                volume_24_max = volume[1].toLong()
            )
            setFragmentResult(
                Constants.RK_BOTTOMSHEET_RESULT,
                bundleOf(Constants.EXTRA_MODEL_FILTER to filterModel)
            )
            dismiss()
        }

//
    }


    companion object {
        val TAG: String = FilterBottomSheet::class.java.simpleName
    }

}