package com.gcaguilar.android.speedrun.data.game.interactor

import com.gcaguilar.android.speedrun.data.common.executor.PostExecutionThread
import com.gcaguilar.android.speedrun.data.common.executor.ThreadExecutor
import com.gcaguilar.android.speedrun.data.common.interactor.SingleUseCase
import com.gcaguilar.android.speedrun.data.game.model.Runs
import com.gcaguilar.android.speedrun.data.game.repository.GameRepository
import com.gcaguilar.android.speedrun.data.game.repository.PlayerRepository
import io.reactivex.Single

class GetRunsUseCase(
        private val gameRepository: GameRepository,
        private val playerRepository: PlayerRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
) : SingleUseCase<Runs, String>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: String?): Single<Runs> {
        return params?.let {
            gameRepository.getRuns(it)
                    .flatMap { runsSource ->
                        playerRepository.getUserById(runsSource.id)
                                .flatMap { player ->
                                    Single.just(
                                            Runs(runsSource.id, player.names, runsSource.time, runsSource.videoUri))
                                }
                    }

        } ?: throw Exception("Cannot get runs without id")
    }
}