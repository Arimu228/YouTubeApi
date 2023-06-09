package com.example.youtubeapi.ui.playList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtubeapi.databinding.ItemPlayListsBinding
import com.example.youtubeapi.data.remote.model.Item

class PLayListsAdapter(var onItemClick: (Item) -> Unit) :
    RecyclerView.Adapter<PLayListsAdapter.Viewholder>() {
    private var playLists = arrayListOf<Item>()

    inner class Viewholder(private var binding: ItemPlayListsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(playLists: Item) {
            Glide.with(binding.imagePlayLists).load(playLists.snippet.thumbnails.maxres.url)
                .into(binding.imagePlayLists)
            binding.textPlayLists.text = playLists.snippet.title
            binding.descPlayLists.text = "${playLists.contentDetails.itemCount} video series"
            itemView.setOnClickListener {
                onItemClick.invoke(playLists)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        return Viewholder(
            ItemPlayListsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPlayList(list: List<Item>) {
        playLists.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.onBind(playLists[position])
    }

    override fun getItemCount(): Int = playLists.size


}




