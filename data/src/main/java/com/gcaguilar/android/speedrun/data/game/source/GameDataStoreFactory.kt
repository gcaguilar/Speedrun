package com.gcaguilar.android.speedrun.data.game.source

class GameDataStoreFactory(
        private val remote: GameDataStore
) {
    fun getDataStore(): GameDataStore {
        return getRemoteDataStore()
    }

    private fun getRemoteDataStore(): GameDataStore {
        return remote
    }
}