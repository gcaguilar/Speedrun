package com.gcaguilar.android.speedrun.remote.model

import com.google.gson.annotations.SerializedName

const val video = "videos"
const val player = "players"
const val time = "times"

data class RunsEntity(
        @field:SerializedName(video)
        val videosEntity: VideosEntity,
        @field:SerializedName(player)
        val playerList: List<PlayersItem>,
        @field:SerializedName(time)
        val timeEntity: TimesEntity
)
