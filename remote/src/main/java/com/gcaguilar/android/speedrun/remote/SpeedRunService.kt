package com.gcaguilar.android.speedrun.remote

import com.gcaguilar.android.speedrun.remote.model.GamesResponseModel
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Defines the abstract methods used for interacting with the SpeedRun API
 */
interface SpeedRunService {

    companion object {
        const val baseUrl = "http://www.speedrun.com/api/v1/"
        const val games = "games/"
    }

    @GET(games)
    fun getGameList(): Single<GamesResponseModel>

}