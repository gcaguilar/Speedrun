package com.gcaguilar.android.speedrun.data.game.repository

import com.gcaguilar.android.speedrun.data.game.model.Player
import com.gcaguilar.android.speedrun.data.game.source.PlayerDataStore
import io.reactivex.Single

class PlayerDataRepository(
        private val playerDataStore: PlayerDataStore
) : PlayerRepository {
    override fun getUserById(userId: String): Single<Player> {
        return playerDataStore.getUserById(userId)
    }
}