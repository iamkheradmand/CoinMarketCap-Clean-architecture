package com.example.coinmarketcap.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import dagger.hilt.android.AndroidEntryPoint

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coinmarketcap.databinding.FragmentHomeBinding
import com.example.coinmarketcap.ui.adapter.CoinsListAdapter
import com.example.coinmarketcap.ui.base.BaseFragment
import com.example.coinmarketcap.ui.bottomsheet.FilterBottomSheet
import com.example.coinmarketcap.ui.bottomsheet.SortBottomSheet
import com.example.coinmarketcap.ui.viewmodel.HomeViewModel
import com.example.data.utils.Constants
import com.example.data.utils.Utils
import com.example.domain.entities.ApiResult
import com.example.domain.entities.CoinDomainModel
import com.example.domain.entities.FilterModel
import com.example.domain.entities.SortModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * Created by Amir mohammad Kheradmand on 11/24/2022.
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), CoinsListAdapter.ItemListener {

    @Inject
    lateinit var picasso: Picasso

    private val viewModel: HomeViewModel by viewModels()

    // recyclerview
    private var coinsListAdapter: CoinsListAdapter? = null
    private var pageNumber: Int = 0
    private var loading: Boolean = false
    private var loadAllItems: Boolean = false

    //sort & filter
    private var filterModel: FilterModel? = null
    private var sortModel: SortModel? = null
    private var queryMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.setFragmentResultListener(
            Constants.RK_BOTTOMSHEET_RESULT,
            this
        ) { key, bundle ->
            if (bundle.containsKey(Constants.EXTRA_MODEL_SORT)) {
                sortModel = bundle.getParcelable(Constants.EXTRA_MODEL_SORT)!!
                applyFilter()
            } else if (bundle.containsKey(Constants.EXTRA_MODEL_FILTER)) {
                filterModel = bundle.getParcelable(Constants.EXTRA_MODEL_FILTER)!!
                Log.e(
                    "HomeFragment",
                    "filterModel: " + filterModel!!.volume_24_min + " = " + filterModel!!.percent_change_24_max
                )
                applyFilter()
            }
        }
    }

    private fun applyFilter() {
        queryMode = true
        resetPageNumber()
        clearAdapter()
        showLoading()
        getCoinsListRemote(++pageNumber)
    }


    private fun clearAdapter() {
        coinsListAdapter?.clear()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        initRecyclerView()
        getCoinsListRemote(++pageNumber)
//        getCoinsListLocal(++pageNumber)
    }

    private fun getCoinsListLocal(pageNumber: Int) {
        Log.e("HomeFragment", "getCoinsListLocal pageNumber = $pageNumber")
        viewModel.getFromRoomByPage(pageNumber).observe(viewLifecycleOwner, { result ->
            dismissLoading()
            if (result != null && result.size > 0) {
                result.map {
                    Log.e("HomeFragment", "getDB Success = " + it.name)
                }
                coinsListAdapter?.updateItems(result as ArrayList<CoinDomainModel>)
            } else
                Log.e("HomeFragment", "getDB Success is empty")

        })
    }

    override fun onClickListeners() {

        binding.sortBtn.setOnClickListener {
            showSortBottomSheet()
        }

        binding.filterBtn.setOnClickListener {
            showFilterBottomSheet()
        }

    }

    private fun showFilterBottomSheet() {
        val filterBottomSheet = FilterBottomSheet()
        if (filterModel != null)
            filterBottomSheet.arguments = bundleOf(Constants.EXTRA_MODEL_FILTER to filterModel)
        filterBottomSheet.show(childFragmentManager, FilterBottomSheet.TAG)

    }

    private fun showSortBottomSheet() {
        val sortBottomSheet = SortBottomSheet()
        if (sortModel != null)
            sortBottomSheet.arguments = bundleOf(Constants.EXTRA_MODEL_FILTER to sortModel)
        sortBottomSheet.show(childFragmentManager, SortBottomSheet.TAG)
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            val linearLayoutManager = LinearLayoutManager(requireContext())
            layoutManager = linearLayoutManager
            coinsListAdapter = CoinsListAdapter(requireContext(), picasso, this@HomeFragment)
//            adapter?.setItemClickListener(this@HomeFragment)
            adapter = coinsListAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        val visibleItemCount = linearLayoutManager.childCount
                        val totalItemCount = linearLayoutManager.itemCount
                        val firstVisibleItemPosition =
                            linearLayoutManager.findFirstVisibleItemPosition()
                        val lastItem = firstVisibleItemPosition + visibleItemCount
                        if (loading == false) {
                            if (lastItem >= totalItemCount) {
                                loading = true
                                coinsListAdapter?.setLoading()
                                refreshLayout()
                            }
                        }
                    }
                }
            }) // end of addOnScrollListener
        }

    }

    private fun getCoinsListRemote(pageNumber: Int) {
        if (!queryMode)
            viewModel.getCoinsList(pageNumber).observe(viewLifecycleOwner, { result ->
                when (result) {
                    is ApiResult.Success -> {
                        dismissLoading()
                        coinsListAdapter?.updateItems(result.data as ArrayList<CoinDomainModel>)
                        Log.e("HomeFragment", "Success" + result.data!![0].name)
                    }

                    is ApiResult.Failure -> {
                        dismissLoading()
                        Utils.showToast(requireContext(), result.message ?: "error")
                        Log.e("HomeFragment", "Failure" + result.message)
                    }
                }
            })
        else
            viewModel.getCoinsListByQuery(pageNumber, sortModel, filterModel)
                .observe(viewLifecycleOwner, { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            dismissLoading()
                            coinsListAdapter?.updateItems(result.data as ArrayList<CoinDomainModel>)
                            Log.e("SplashFragment", "Success" + result.data!![0].name)
                        }

                        is ApiResult.Failure -> {
                            dismissLoading()
                            Utils.showToast(requireContext(), result.message ?: "error")
                            Log.e("SplashFragment", "Failure" + result.message)
                        }
                    }
                })

    }

    override fun onBackPressed() {

    }

    fun refreshLayout() {
        Log.e("HomeFragment", "refreshLayout")
//        getCoinsListLocal(++pageNumber)
        if (coinsListAdapter!!.hasItem()) {
            getCoinsListRemote(++pageNumber)
        } else {
            resetPageNumber()
            getCoinsListRemote(++pageNumber)
        }
    }

    private fun resetPageNumber() {
        pageNumber = 0
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun dismissLoading() {
        loading = false
        binding.progressBar.visibility = View.GONE
    }

    override fun isEndOfList() {

    }

    override fun onDestroyView() {
        if (isViewNotNull()) {
            binding.recyclerView.adapter = null
        }
        coinsListAdapter = null
        super.onDestroyView()
    }

}