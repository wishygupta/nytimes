package com.mine.nytimesapi.ui.bestselller

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.mine.nytimesapi.R
import com.mine.nytimesapi.data.entities.BestSellers
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters


class BestSellerSection(
    private val header: String,
    private val items: List<BestSellers>,
    private val listener: BestSellerItemClickListener
) : Section(
    SectionParameters.builder()
        .itemResourceId(R.layout.item_best_seller)
        .headerResourceId(R.layout.item_header)
        .build()
) {

    interface BestSellerItemClickListener {
        fun onBestSellerClicked(encodedName: String)
    }

    override fun getContentItemsTotal(): Int {
        return items.size
    }


    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return ItemViewHolder(view)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder: ItemViewHolder = holder as ItemViewHolder
        val pos=position+1
        itemHolder.title.text = pos.toString().plus(". ").plus(items[position].name)
        itemHolder.publishDate.text = items[position].publishDate
        itemHolder.itemView.setOnClickListener { v ->
            listener.onBestSellerClicked(items[position].encodedName)
        }

    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return HeaderViewHolder(view)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        val itemHolder: HeaderViewHolder = holder as HeaderViewHolder
        itemHolder.title.text = header.plus("(").plus(items.size).plus(")")
    }
}

internal class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: MaterialTextView
    val publishDate: MaterialTextView

    init {
        title = itemView.findViewById(R.id.title)
        publishDate = itemView.findViewById(R.id.publishDate)
    }
}

internal class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: MaterialTextView

    init {
        title = itemView.findViewById(R.id.headerTitle)
    }
}

