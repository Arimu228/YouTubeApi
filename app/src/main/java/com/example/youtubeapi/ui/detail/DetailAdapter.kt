package com.example.youtubeapi.ui.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtubeapi.data.remote.model.Item
import com.example.youtubeapi.databinding.DetailItemListBinding
import kotlin.reflect.KFunction1

class DetailAdapter(private val onNextClick: KFunction1<Int, Unit>) :
    RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {
    private var items = arrayListOf<Item>()


    inner class DetailViewHolder(private var binding: DetailItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Item) {
            Glide.with(binding.imageDetail).load(item.snippet.thumbnails.medium.url)
                .into(binding.imageDetail)
            binding.textDetail.text = item.snippet.title
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            DetailItemListBinding.inflate(
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