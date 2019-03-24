package com.gcaguilar.android.speedrun.ui.di

import com.gcaguilar.android.speedrun.data.common.executor.JobExecutor
import com.gcaguilar.android.speedrun.data.common.executor.PostExecutionThread
import com.gcaguilar.android.speedrun.data.common.executor.ThreadExecutor
import com.gcaguilar.android.speedrun.data.game.interactor.GetGameListUseCase
import com.gcaguilar.android.speedrun.data.game.interactor.GetRunsUseCase
import com.gcaguilar.android.speedrun.data.game.repository.GameDataRepository
import com.gcaguilar.android.speedrun.data.game.repository.GameRepository
import com.gcaguilar.android.speedrun.data.game.repository.PlayerDataRepository
import com.gcaguilar.android.speedrun.data.game.repository.PlayerRepository
import com.gcaguilar.android.speedrun.data.game.source.GameDataStore
import com.gcaguilar.android.speedrun.data.game.source.GameDataStoreFactory
import com.gcaguilar.android.speedrun.data.game.source.PlayerDataStore
import com.gcaguilar.android.speedrun.data.game.source.PlayerDataStoreFactory
import com.gcaguilar.android.speedrun.remote.SpeedRunRemoteImpl

import com.gcaguilar.android.speedrun.remote.SpeedRunServiceFactory
import com.gcaguilar.android.speedrun.remote.mapper.GameEntityMapper
import com.gcaguilar.android.speedrun.remote.mapper.PlayerEntityMapper
import com.gcaguilar.android.speedrun.remote.mapper.RunsEntityMapper
import com.gcaguilar.android.speedrun.ui.BuildConfig
import com.gcaguilar.android.speedrun.ui.UiThread
import com.gcaguilar.android.speedrun.ui.detail.DetailViewModel
import com.gcaguilar.android.speedrun.ui.game.GameListViewModel
import com.gcaguilar.android.speedrun.ui.main.NavigationViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val applicationModule = module(override = true) {


    single { JobExecutor() as ThreadExecutor }
    single { UiThread() as PostExecutionThread }

    factory { SpeedRunServiceFactory.makeBuffeooService(BuildConfig.DEBUG) }
    viewModel { NavigationViewModel() }
}

val gameModule = module("Game", override = true) {
    factory { PlayerEntityMapper() }
    factory { RunsEntityMapper() }
    factory { GameEntityMapper() }

    factory<GameDataStore> { SpeedRunRemoteImpl(get(), get(), get(), get()) }
    factory<PlayerDataStore> { SpeedRunRemoteImpl(get(), get(), get(), get()) }

    factory { GameDataStoreFactory(get()) }
    factory { PlayerDataStoreFactory(get()) }

    factory<GameRepository> { GameDataRepository(get()) }
    factory<PlayerRepository> { PlayerDataRepository(get()) }

    factory { GetGameListUseCase(get(), get(), get()) }
    factory { GetRunsUseCase(get(), get(), get(), get()) }
    viewModel { GameListViewModel(get()) }
    viewModel { DetailViewModel(get()) }

}