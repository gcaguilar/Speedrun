package com.gcaguilar.android.speedrun.data.game.repository

import com.gcaguilar.android.speedrun.data.game.model.Player
import io.reactivex.Single

interface PlayerRepository {
    fun getUserById(userId: String): Single<Player>
}