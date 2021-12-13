package com.kryptos.cryptotracker.main.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kryptos.cryptotracker.databinding.CoinHistoryItemBinding
import com.kryptos.data.models.CryptoModel

class CoinHistoryAdapter : ListAdapter<CryptoModel, CoinHistoryAdapter.CoinHistoryViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinHistoryViewHolder {
        return CoinHistoryViewHolder(CoinHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CoinHistoryViewHolder, position: Int) {
        holder.bindValue(getItem(position))
    }

    class CoinHistoryViewHolder(private val coinHistoryItemBinding: CoinHistoryItemBinding) : RecyclerView.ViewHolder(coinHistoryItemBinding.root) {

        fun bindValue(cryptoModel: CryptoModel) {
            coinHistoryItemBinding.cryptoDate.text = cryptoModel.time.updatedAt
            coinHistoryItemBinding.cryptoRate.text = cryptoModel.bpi.usdRate.rate.toString()
            coinHistoryItemBinding.currencyCode.text = cryptoModel.bpi.usdRate.currencyCode
        }
    }

    companion object {
        val callback = object : DiffUtil.ItemCallback<CryptoModel>() {
            override fun areItemsTheSame(oldItem: CryptoModel, newItem: CryptoModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CryptoModel, newItem: CryptoModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}
