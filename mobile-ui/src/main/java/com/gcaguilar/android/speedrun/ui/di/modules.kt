package com.gcaguilar.android.speedrun.ui.di

import com.gcaguilar.android.speedrun.data.BufferooDataRepository
import com.gcaguilar.android.speedrun.data.browse.interactor.GetBufferoos
import com.gcaguilar.android.speedrun.data.executor.JobExecutor
import com.gcaguilar.android.speedrun.data.executor.PostExecutionThread
import com.gcaguilar.android.speedrun.data.executor.ThreadExecutor
import com.gcaguilar.android.speedrun.data.repository.BufferooRepository
import com.gcaguilar.android.speedrun.data.source.BufferooDataStore
import com.gcaguilar.android.speedrun.data.source.BufferooDataStoreFactory
import com.gcaguilar.android.speedrun.remote.BufferooRemoteImpl
import com.gcaguilar.android.speedrun.remote.BufferooServiceFactory
import com.gcaguilar.android.speedrun.remote.mapper.BufferooEntityMapper
import com.gcaguilar.android.speedrun.ui.UiThread
import com.gcaguilar.android.speedrun.ui.browse.BrowseAdapter
import com.gcaguilar.android.speedrun.ui.browse.BrowseBufferoosViewModel
import org.buffer.android.boilerplate.ui.BuildConfig
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val applicationModule = module(override = true) {


    factory { BufferooEntityMapper() }

    single { JobExecutor() as ThreadExecutor }
    single { UiThread() as PostExecutionThread }

    factory<BufferooDataStore>("remote") { BufferooRemoteImpl(get(), get()) }
    factory { BufferooDataStoreFactory(get("remote")) }

    factory { BufferooEntityMapper() }
    factory { BufferooServiceFactory.makeBuffeooService(BuildConfig.DEBUG) }

    factory<BufferooRepository> { BufferooDataRepository(get()) }
}

val browseModule = module("Browse", override = true) {
    factory { BrowseAdapter() }
    factory { GetBufferoos(get(), get(), get()) }
    viewModel { BrowseBufferoosViewModel(get()) }
}