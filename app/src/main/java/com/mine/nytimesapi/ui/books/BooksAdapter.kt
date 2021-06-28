package com.mine.nytimesapi.ui.books

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mine.nytimesapi.data.entities.PopularBooks
import com.mine.nytimesapi.databinding.ItemBookBinding

class BooksAdapter : RecyclerView.Adapter<ImageViewHolder>() {


    private val items = ArrayList<PopularBooks>()

    fun setItems(items: ArrayList<PopularBooks>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding: ItemBookBinding =
            ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size
}

class ImageViewHolder(
    private val itemBinding: ItemBookBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(item: PopularBooks) {
        itemBinding.title.text = item.title
        itemBinding.description.text = item.description
        itemBinding.price.text = item.price.toString()
        itemBinding.author.text = item.author
    }


}