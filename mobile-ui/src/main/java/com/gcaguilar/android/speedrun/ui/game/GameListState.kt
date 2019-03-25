package com.gcaguilar.android.speedrun.ui.game

import com.gcaguilar.android.speedrun.data.game.model.Game

sealed class GameListState(
        val data: List<Game>? = null,
        val error: String? = null
) {

    data class Success(private val dataSource: List<Game>) : GameListState(dataSource)

    data class Error(val errorMessage: String?) : GameListState(
            error = errorMessage?.let {
                it
            } ?: throw Exception("Cannot be possible recover error")
    )

    object Loading : GameListState()

}