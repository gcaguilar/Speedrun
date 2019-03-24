package com.gcaguilar.android.speedrun.remote.model

import com.google.gson.annotations.SerializedName

private const val logoGame = "logo"
private const val cover = "cover-medium"

data class AssetsModel(
        @field:SerializedName(logoGame)
        val coverSmallEntity: CoverSmallEntity,
        @field:SerializedName(cover)
        val coverMedium: CoverMediumModel
)