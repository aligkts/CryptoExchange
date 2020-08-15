package com.aligkts.cryptoexchange.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.databinding.ItemCoinDetailBinding
import com.aligkts.cryptoexchange.extension.playAnimation
import com.aligkts.cryptoexchange.model.dto.response.DItem
import com.aligkts.cryptoexchange.util.AutoUpdatableAdapter
import kotlin.properties.Delegates

/**
 * Created by Ali Göktaş on 14,August,2020
 */

class CoinDetailAdapter : RecyclerView.Adapter<CoinDetailAdapter.CoinDetailViewHolder>(),
    AutoUpdatableAdapter {

    var items: List<DItem> by Delegates.observable(emptyList()) {
            prop, old, new ->
        autoNotify(old, new) { o, n -> o.desc == n.desc }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinDetailViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCoinDetailBinding.inflate(layoutInflater, parent, false)
        return CoinDetailViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CoinDetailViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class CoinDetailViewHolder(val binding: ItemCoinDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: DItem) = with(binding) {
            item = model
            containerCoinDetail.playAnimation(R.anim.fade, 500L)
            executePendingBindings()
        }
    }
}