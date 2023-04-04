package com.example.youtubeapi.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.youtubeapi.databinding.ActivityDetailPlayListBinding

abstract class BaseActivity<VM : ViewModel, VB : ViewBinding> : AppCompatActivity() {

     lateinit var binding: VB
    protected lateinit var viewModel: VM

    protected abstract fun inflateViewBinding(inflater: LayoutInflater): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateViewBinding(layoutInflater)
        setContentView(binding.root)

        checkInternet()
        initViewModel()
        initView()
        initListener()

    }

    open fun initView() {}
    open fun initListener() {}
    open fun checkInternet() {}
    open fun initViewModel() {}

}