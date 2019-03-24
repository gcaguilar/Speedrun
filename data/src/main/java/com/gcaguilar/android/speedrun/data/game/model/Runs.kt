package com.gcaguilar.android.speedrun.data.game.model

data class Runs(val id: String,
                val playerName: String? = null,
                val time: String,
                val videoUri: String) {
    constructor(id: String, time: String, videoUri: String) : this(id, "", time, videoUri)

}