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
            } else if (bundle.containsKey(Constants.EXTRA_MODEL_FILTER)) {
                filterModel = bundle.getParcelable(Constants.EXTRA_MODEL_FILTER)!!
            }
            applyFilter()
        }
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        showLoading()
//        databaseSizeChangeListener()
        initRecyclerView()
//        getCoinsListLocal()
        getCoinsListRemote()
        hasConnection()
    }


    override fun onClickListeners() {

        binding.sortBtn.setOnClickListener {
            if (hasConnection())
                showSortBottomSheet()
        }

        binding.filterBtn.setOnClickListener {
            if (hasConnection())
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
            sortBottomSheet.arguments = bundleOf(Constants.EXTRA_MODEL_SORT to sortModel)
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



    private fun getCoinsListRemote() {
        if (queryMode)
            viewModel.getCoinsListByQuery(++pageNumber, sortModel, filterModel)
                .observe(viewLifecycleOwner, { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            dismissLoading()
                            coinsListAdapter?.updateItems(result.data as ArrayList<CoinDomainModel>)
                        }

                        is ApiResult.Failure -> {
                            dismissLoading()
                            Utils.showToast(requireContext(), result.message ?: "error")
                        }
                    }
                })
        else
            viewModel.getCoinsList(++pageNumber)
                .observe(viewLifecycleOwner, { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            dismissLoading()
                            coinsListAdapter?.updateItems(result.data as ArrayList<CoinDomainModel>)
                        }

                        is ApiResult.Failure -> {
                            dismissLoading()
                            coinsListAdapter?.setLoaded()
                            Utils.showToast(requireContext(), result.message ?: "error")
                        }
                    }
                })
    }

    /*
    * If the database size becomes zero, it means that the old data is deleted
    * and the database is being updated
    * */
    private fun databaseSizeChangeListener() {
        viewModel.getDatabaseSize().observe(viewLifecycleOwner, { size ->
            Log.d("HomeFragment", "database size = $size ")
            if (size == 0) {
                pageNumber = 1
                clearAdapter()
                showLoading()
            }
        })
    }

    private fun getCoinsListLocal() {
        viewModel.getFromRoomByPage(++pageNumber).observe(viewLifecycleOwner, { result ->
            Log.d("HomeFragment", "getCoinsListLocal page = $pageNumber result = ${result.size} ")
            if (hasConnection() && result.isEmpty() && loading) {
                viewModel.requestUpdateDatabase(pageNumber)
            } else {
                dismissLoading()
                coinsListAdapter?.updateItems(result as ArrayList<CoinDomainModel>)
            }
//            this.pageNumber = pageNumber
//            if (result.isEmpty())
//                --this.pageNumber
        })
    }

    override fun onBackPressed() {

    }

    private fun applyFilter() {
        queryMode = true
        reset()
        getCoinsListRemote()
    }


    private fun clearAdapter() {
        coinsListAdapter?.clear()
    }

    fun refreshLayout() {
        Log.d("HomeFragment", "refreshLayout: $pageNumber")
        getCoinsListRemote()

//        if (queryMode) {
//            getCoinsListRemote()
//        } else
//            getCoinsListLocal()
    }

    private fun reset() {
        Log.d("HomeFragment", "reset: ")
        resetPageNumber()
        clearAdapter()
        showLoading()
    }

    private fun resetPageNumber() {
        pageNumber = 0
        Log.d("HomeFragment", "resetPageNumber = $pageNumber ")

    }

    private fun showLoading() {
        loading = true
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