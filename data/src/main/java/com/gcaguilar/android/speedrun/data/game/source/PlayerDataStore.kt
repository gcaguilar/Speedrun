package com.gcaguilar.android.speedrun.data.game.source

import com.gcaguilar.android.speedrun.data.game.model.Player
import io.reactivex.Single

interface PlayerDataStore {
    fun getUserById(userId: String): Single<Player>
}