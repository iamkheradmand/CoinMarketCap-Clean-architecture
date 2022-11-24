package com.example.coinmarketcap.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.coinmarketcap.databinding.AdapterCoinsListBinding
import com.example.coinmarketcap.databinding.AdapterLoadingBinding
import com.example.data.typedef.ItemViewType
import com.example.domain.entities.CoinDomainModel
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

/**
 * Created by marshall-mathers on 11/24/2022.
 */

class CoinsListAdapter constructor(
    val context: Context,
    val picasso: Picasso,
    val itemListener: ItemListener?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var arrayList = ArrayList<CoinDomainModel?>()
    private var itemOnClickListener: CoinsListAdapter.ItemOnClickListener? = null
    private var totalItemCount: Int = 500 //default

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemViewType.LOADING -> {
                val binding = AdapterLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadingViewHolder(binding)
            }
            else  -> {
                val binding = AdapterCoinsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder ->{
                val model = arrayList[position]
                model?.let {
                    holder.binding.symbol.text = it.symbol
                    holder.binding.name.text = it.name
                    holder.binding.price.text = "${it.priceByUsd} $"
                    picasso.load(it.logo)
                        .into(holder.binding.logo)
                }
            }

            is LoadingViewHolder ->{
                val loadingViewHolder: LoadingViewHolder = holder
                loadingViewHolder.binding.progressBar.isIndeterminate = true
            }
        }
    }

    override fun getItemCount(): Int = arrayList.size

    fun updateItems(list: ArrayList<CoinDomainModel>) {
        setLoaded()
        arrayList.addAll(list)

        if (arrayList.size == totalItemCount) {
            itemListener?.isEndOfList()
        }
        notifyDataSetChanged()
    }


    fun setLoaded() {
        for (i in 0 until itemCount) {
            if (arrayList[i] == null) {
                arrayList.removeAt(i)
                notifyItemRemoved(i)
            }
        }
    }

    fun setLoading() {
        if (itemCount != 0) {
            this.arrayList.add(null)
            notifyItemInserted(itemCount - 1)
        }
    }


    fun hasItem(): Boolean {
        return arrayList.size != 0
    }

    fun clear() {
        arrayList.clear()
        notifyDataSetChanged()
    }


    fun setItemClickListener(itemOnClickListener: CoinsListAdapter.ItemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener
    }


    inner class ViewHolder(val binding: AdapterCoinsListBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            arrayList[adapterPosition]?.let {
                itemOnClickListener?.itemOnClick(arrayList[adapterPosition]!!)
            }
        }
    }

    inner class LoadingViewHolder(val binding: AdapterLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    interface ItemOnClickListener {
        fun itemOnClick(coinDomainModel: CoinDomainModel)
    }

    override fun getItemViewType(position: Int): Int {
        if (arrayList[position] == null)
            return ItemViewType.LOADING
        return ItemViewType.DEFAULT
    }

    interface ItemListener {
        fun isEndOfList()
    }


}
