package com.example.youtubeapi.di

import com.example.youtubeapi.ui.detail.DetailViewModel
import com.example.youtubeapi.ui.playList.PlayListViewModel
import com.example.youtubeapi.ui.video.VideoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModules = module {
    viewModel { PlayListViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { VideoViewModel(get()) }
}