package com.example.youtubeapi.ui.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtubeapi.databinding.ItemPlayListsBinding
import com.example.youtubeapi.model.Item

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {

    private var items = arrayListOf<Item>()

    inner class DetailViewHolder(private val binding: ItemPlayListsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Item) {
            Glide.with(binding.imagePlayLists).load(item.snippet.thumbnails.medium.url).into(binding.imagePlayLists)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            ItemPlayListsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItemsList(list: List<Item>) {
        items.addAll(list)
        notifyDataSetChanged()
    }



    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size


}