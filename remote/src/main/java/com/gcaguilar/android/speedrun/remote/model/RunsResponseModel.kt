package com.gcaguilar.android.speedrun.remote.model

import com.google.gson.annotations.SerializedName

private const val data = "data"

data class RunsResponseModel(
        @field:SerializedName(data)
        val runResponseList: List<RunsEntity>)