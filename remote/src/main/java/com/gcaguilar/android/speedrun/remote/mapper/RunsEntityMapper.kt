package com.gcaguilar.android.speedrun.remote.mapper

import com.gcaguilar.android.speedrun.data.game.model.Runs
import com.gcaguilar.android.speedrun.remote.model.RunsEntity

class RunsEntityMapper : EntityMapper<RunsEntity, Runs> {
    override fun mapFromRemote(type: RunsEntity): Runs {
        return Runs(type.playerList.first().id ?: "Cant recover id",
                type.timeEntity.realtime?.let { it } ?: "PM0",
                type.videosEntity.links.first().uri)
    }
}