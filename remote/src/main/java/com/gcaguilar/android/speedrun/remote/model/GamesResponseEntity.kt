package com.gcaguilar.android.speedrun.remote.model

import com.google.gson.annotations.SerializedName

private const val data = "data"

data class GamesResponseModel(
        @field:SerializedName(data)
        val gameResponseList: List<GameEntity>)