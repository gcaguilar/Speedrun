package com.gcaguilar.android.speedrun.data.game.interactor

import com.gcaguilar.android.speedrun.data.common.executor.PostExecutionThread
import com.gcaguilar.android.speedrun.data.common.executor.ThreadExecutor
import com.gcaguilar.android.speedrun.data.common.interactor.SingleUseCase
import com.gcaguilar.android.speedrun.data.game.model.Game
import com.gcaguilar.android.speedrun.data.game.repository.GameRepository
import io.reactivex.Single

class GetGameListUseCase(
        private val gameRepository: GameRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Game>, Void?>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: Void?): Single<List<Game>> {
        return gameRepository.getGameList()
    }

}