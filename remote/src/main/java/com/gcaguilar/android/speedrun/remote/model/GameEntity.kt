package com.gcaguilar.android.speedrun.remote.model

import com.google.gson.annotations.SerializedName

private const val assets = "assets"

data class GameModel(val id: String,
                     val names: NameEntity,
                     @field:SerializedName(assets)
                     val assetsModel: AssetsModel)