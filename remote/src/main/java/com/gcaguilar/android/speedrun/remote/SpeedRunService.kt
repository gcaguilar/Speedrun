package com.gcaguilar.android.speedrun.remote

import com.gcaguilar.android.speedrun.remote.model.GamesResponseModel
import com.gcaguilar.android.speedrun.remote.model.PlayerResponseEntity
import com.gcaguilar.android.speedrun.remote.model.RunsResponseEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Defines the abstract methods used for interacting with the SpeedRun API
 */
interface SpeedRunService {

    companion object {
        const val baseUrl = "https://www.speedrun.com/api/v1/"
        const val games = "games"
        const val game = "game"
        const val runs = "runs"
        const val user = "users/{userId}"
    }

    @GET(runs)
    fun getRunsById(@Query(game) gameId: String): Single<RunsResponseEntity>

    @GET(games)
    fun getGameList(): Single<GamesResponseModel>

    @GET(user)
    fun getUserById(@Path("userId") userId: String): Single<PlayerResponseEntity>

}