package com.gcaguilar.android.speedrun.remote.model

import com.google.gson.annotations.SerializedName

data class TimesEntity(

        @field:SerializedName("realtime")
        val realtime: String? = null,

        @field:SerializedName("realtime_t")
        val realtimeT: Int? = null,

        @field:SerializedName("realtime_noloads_t")
        val realtimeNoloadsT: Int? = null,

        @field:SerializedName("primary_t")
        val primaryT: Int? = null,

        @field:SerializedName("ingame_t")
        val ingameT: Int? = null,

        @field:SerializedName("realtime_noloads")
        val realtimeNoloads: Any? = null,

        @field:SerializedName("ingame")
        val ingame: Any? = null,

        @field:SerializedName("primary")
        val primary: String? = null
)