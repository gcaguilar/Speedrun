package com.gcaguilar.android.speedrun.remote.mapper

import com.gcaguilar.android.speedrun.data.game.model.Game
import com.gcaguilar.android.speedrun.remote.model.GameEntity

class GameEntityMapper : EntityMapper<GameEntity, Game> {
    override fun mapFromRemote(type: GameEntity): Game {
        return Game(type.id,
                type.names.international,
                type.assetsModel.coverSmallEntity.uri?.let { it } ?: "",
                type.assetsModel.coverMedium.uri?.let { it } ?: "")
    }
}