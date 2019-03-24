package com.gcaguilar.android.speedrun.remote.model

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
        @field:SerializedName("data")
        val player: PlayerEntity
)