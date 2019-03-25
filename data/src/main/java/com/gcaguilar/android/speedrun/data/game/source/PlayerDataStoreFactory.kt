package com.gcaguilar.android.speedrun.data.game.source

class PlayerDataStoreFactory(
        private val remote: PlayerDataStore
) {
    fun getDataStore() {
        getRemoteDataStore()
    }

    fun getRemoteDataStore(): PlayerDataStore {
        return remote
    }
}