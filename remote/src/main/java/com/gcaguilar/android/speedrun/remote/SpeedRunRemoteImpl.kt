package com.gcaguilar.android.speedrun.remote

import com.gcaguilar.android.speedrun.data.game.model.Game
import com.gcaguilar.android.speedrun.data.game.model.Player
import com.gcaguilar.android.speedrun.data.game.model.Runs
import com.gcaguilar.android.speedrun.data.game.source.GameDataStore
import com.gcaguilar.android.speedrun.data.game.source.PlayerDataStore
import com.gcaguilar.android.speedrun.remote.mapper.GameEntityMapper
import com.gcaguilar.android.speedrun.remote.mapper.PlayerEntityMapper
import com.gcaguilar.android.speedrun.remote.mapper.RunsEntityMapper
import io.reactivex.Single

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class SpeedRunRemoteImpl(
        private val speedRunService: SpeedRunService,
        private val gameEntityMapper: GameEntityMapper,
        private val playerEntityMapper: PlayerEntityMapper,
        private val runsEntityMapper: RunsEntityMapper
) : GameDataStore, PlayerDataStore {

    override fun getRuns(id: String): Single<Runs> {
        return speedRunService.getRunsById(id)
                .map { it.runResponseList }
                .map { runsEntityMapper.mapFromRemote(it.first()) }
    }

    override fun getUserById(userId: String): Single<Player> {
        return speedRunService.getUserById(userId)
                .map { playerSource -> playerEntityMapper.mapFromRemote(playerSource.player) }
    }


    override fun getGameList(): Single<List<Game>> {
        return speedRunService.getGameList()
                .map { it.gameResponseList }
                .map {
                    val gameList = mutableListOf<Game>()
                    it.forEach { gameSource ->
                        gameList.add(gameEntityMapper.mapFromRemote(gameSource))
                    }
                    gameList
                }
    }
}