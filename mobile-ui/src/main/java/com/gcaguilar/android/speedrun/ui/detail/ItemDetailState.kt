package com.gcaguilar.android.speedrun.ui.detail

import com.gcaguilar.android.speedrun.data.game.model.Runs

sealed class ItemDetailState(
        val data: Runs? = null,
        val errorMessage: String? = null
) {
    data class Success(private val source: Runs?) : ItemDetailState(
            source
    )

    data class Error(private val e: String?) : ItemDetailState(
            errorMessage = e?.let { it } ?: throw Exception("Cannot recover error")
    )

    object Loading : ItemDetailState()
}