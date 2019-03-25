package com.gcaguilar.android.speedrun.data.game.source

import com.gcaguilar.android.speedrun.data.game.model.Game
import com.gcaguilar.android.speedrun.data.game.model.Runs
import io.reactivex.Single

interface GameDataStore {

    fun getGameList(): Single<List<Game>>

    fun getRuns(id: String): Single<Runs>
}