package com.aligkts.cryptoexchange.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.databinding.ItemCoinBinding
import com.aligkts.cryptoexchange.extension.listen
import com.aligkts.cryptoexchange.extension.playAnimation
import com.aligkts.cryptoexchange.model.dto.response.CoinItemDTO
import kotlin.properties.Delegates

class CoinAdapter(private val onItemClick: (coinItem: CoinItemDTO) -> Unit) : RecyclerView.Adapter<CoinAdapter.CoinItemDTOViewHolder>(), AutoUpdatableAdapter {

    var items: List<CoinItemDTO> by Delegates.observable(emptyList()) {
        prop, old, new ->
        autoNotify(old, new) { o, n -> o.code == n.code }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinItemDTOViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCoinBinding.inflate(layoutInflater, parent, false)
        return CoinItemDTOViewHolder(binding).listen { position, type ->
            onItemClick.invoke(items[position])
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CoinItemDTOViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class CoinItemDTOViewHolder(val binding: ItemCoinBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CoinItemDTO) = with(binding) {
            item = model
            containerCoinItem.playAnimation(R.anim.fade, 500L)
            executePendingBindings()
        }
    }
}