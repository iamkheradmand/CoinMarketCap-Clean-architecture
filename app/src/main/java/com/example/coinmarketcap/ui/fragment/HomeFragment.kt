package com.example.coinmarketcap.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coinmarketcap.databinding.FragmentHomeBinding
import com.example.coinmarketcap.databinding.FragmentSplashBinding
import com.example.coinmarketcap.ui.adapter.CoinsListAdapter
import com.example.coinmarketcap.ui.base.BaseFragment
import com.example.coinmarketcap.ui.viewmodel.SplashViewModel
import com.example.data.utils.Utils
import com.example.domain.entities.ApiResult
import com.example.domain.entities.CoinDomainModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * Created by Amir mohammad Kheradmand on 11/24/2022.
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), CoinsListAdapter.ItemListener {

    @Inject
    lateinit var picasso: Picasso

    private val viewModel: SplashViewModel by viewModels()
    private var coinsListAdapter: CoinsListAdapter? = null

    private var pageNumber: Int = 0

    private var loading: Boolean = false
    private var loadAllItems: Boolean = false

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        initRecyclerView()
        getCoinsList(++pageNumber)
    }

    override fun onClickListeners() {
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

    private fun getCoinsList(pageNumber: Int) {
        viewModel.getCoinsList().observe(viewLifecycleOwner, { result ->
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

//        viewModel.getDB().observe(viewLifecycleOwner, { result ->
//            if (result != null && result.size > 0)
//                Log.e("SplashFragment", "getDB Success" + result.size)
//            else
//                Log.e("SplashFragment", "getDB Success is empty")
//
//        })
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onBackPressed() {

    }

    fun refreshLayout() {
        if (coinsListAdapter!!.hasItem()) {
            getCoinsList(++pageNumber)
        } else {
            resetPageNumber()
            getCoinsList(++pageNumber)
        }
    }

    private fun resetPageNumber() {
        pageNumber = 1
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun dismissLoading() {
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