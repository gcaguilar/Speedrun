package com.gcaguilar.android.speedrun.data.game.source

import com.gcaguilar.android.speedrun.data.game.Game
import io.reactivex.Single

interface GameDataStore {

    fun getGameList(): Single<List<Game>>

}