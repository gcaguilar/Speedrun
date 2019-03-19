package com.gcaguilar.android.speedrun.ui.browse

import com.gcaguilar.android.speedrun.data.browse.Bufferoo
import com.gcaguilar.android.speedrun.ui.model.ResourceState

sealed class BrowseState(
        val resourceState: ResourceState,
        val data: List<Bufferoo>? = null,
        val errorMessage: String? = null
) {

    data class Success(private val bufferoos: List<Bufferoo>) : BrowseState(
            ResourceState.SUCCESS,
            bufferoos
    )

    data class Error(private val message: String? = null) : BrowseState(
            ResourceState.SUCCESS,
            errorMessage = message
    )

    object Loading : BrowseState(ResourceState.LOADING)
}