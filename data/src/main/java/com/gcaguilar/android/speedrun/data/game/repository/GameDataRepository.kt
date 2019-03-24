package com.gcaguilar.android.speedrun.data.game.repository

import com.gcaguilar.android.speedrun.data.game.model.Game
import com.gcaguilar.android.speedrun.data.game.model.Runs
import com.gcaguilar.android.speedrun.data.game.source.GameDataStore
import io.reactivex.Single

class GameDataRepository(
        private val factory: GameDataStore
) : GameRepository {
    override fun getRuns(id: String): Single<Runs> {
        return factory.getRuns(id)
    }

    override fun getGameList(): Single<List<Game>> {
        return factory.getGameList()
    }

}