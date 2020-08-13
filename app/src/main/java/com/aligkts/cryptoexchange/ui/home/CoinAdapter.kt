package com.aligkts.cryptoexchange.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aligkts.cryptoexchange.databinding.ItemCoinBinding
import com.aligkts.cryptoexchange.model.dto.response.CoinItemDTO

class CoinAdapter(private val onItemClick: (coinItem: CoinItemDTO) -> Unit) : ListAdapter<CoinItemDTO, CoinAdapter.CoinItemDTOViewHolder>(Companion) {

    class CoinItemDTOViewHolder(val binding: ItemCoinBinding) : RecyclerView.ViewHolder(binding.root)

    companion object: DiffUtil.ItemCallback<CoinItemDTO>() {
        override fun areItemsTheSame(oldItem: CoinItemDTO, newItem: CoinItemDTO): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: CoinItemDTO, newItem: CoinItemDTO): Boolean = oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinItemDTOViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCoinBinding.inflate(layoutInflater, parent, false)
        return CoinItemDTOViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinItemDTOViewHolder, position: Int) {
        holder.binding.item = getItem(position)
        holder.binding.executePendingBindings()
        holder.binding.containerCoinItem.setOnClickListener {
            onItemClick.invoke(getItem(position))
        }
    }
}