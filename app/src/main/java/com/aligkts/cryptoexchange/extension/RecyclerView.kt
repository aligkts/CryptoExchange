package com.aligkts.cryptoexchange.extension

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Ali Göktaş on 11.08.2020
 */
fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, itemViewType)
    }
    return this
}