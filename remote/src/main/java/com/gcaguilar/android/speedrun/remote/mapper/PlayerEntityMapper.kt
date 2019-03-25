package com.gcaguilar.android.speedrun.remote.mapper

import com.gcaguilar.android.speedrun.data.game.model.Player
import com.gcaguilar.android.speedrun.remote.model.PlayerEntity

class PlayerEntityMapper : EntityMapper<PlayerEntity, Player> {
    override fun mapFromRemote(type: PlayerEntity): Player {
        return Player(type.names.international)
    }

}