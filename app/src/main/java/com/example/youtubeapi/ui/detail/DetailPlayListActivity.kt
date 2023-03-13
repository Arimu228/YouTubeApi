package com.example.youtubeapi.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.example.youtubeapi.base.BaseActivity
import com.example.youtubeapi.databinding.ActivityDetailPlayListBinding
import com.example.youtubeapi.model.Item
import com.example.youtubeapi.ui.playList.PLayListsAdapter

class DetailPlayListActivity : BaseActivity< DetailViewModel,ActivityDetailPlayListBinding>() {
    private lateinit var adapter: PLayListsAdapter

    private val id: String?
        get() = intent.getStringExtra(ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = PLayListsAdapter(this::onItemClick)
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityDetailPlayListBinding {
        return ActivityDetailPlayListBinding.inflate(inflater)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        setItemList()
    }

    private fun setItemList() {
        id?.let { id ->
            viewModel.getItemList(id = id).observe(this) {
                binding.rvPlayLists.adapter = adapter

                adapter.setItemList(it.items)

            }
        }
    }
    private fun onItemClick(list: Item){
    }
    companion object {
        const val ID = "id"
    }


}




