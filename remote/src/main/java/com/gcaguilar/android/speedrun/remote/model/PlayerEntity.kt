package com.gcaguilar.android.speedrun.remote.model

import com.google.gson.annotations.SerializedName


data class PlayerEntity(
        @field:SerializedName("names")
        val names: NameEntity)