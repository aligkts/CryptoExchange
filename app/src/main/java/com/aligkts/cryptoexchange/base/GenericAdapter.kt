package com.aligkts.cryptoexchange.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.aligkts.cryptoexchange.extension.inflate
import com.aligkts.cryptoexchange.extension.listen

/**
 * Created by Ali Göktaş on 11.08.2020
 */
class GenericAdapter<T : RecyclerViewItem>(val items: List<T>,
                                           val callback : (GenericViewHolder, T)-> Unit,
                                           val onClick : ((T) -> Unit)?) : RecyclerView.Adapter<GenericAdapter.GenericViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        return GenericViewHolder(parent.inflate(viewType, false)).listen { position, type ->
            onClick?.invoke(items[position])
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].resId

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) = callback.invoke(holder, items[position])

    class GenericViewHolder(val binding : ViewDataBinding): RecyclerView.ViewHolder(binding.root)
}