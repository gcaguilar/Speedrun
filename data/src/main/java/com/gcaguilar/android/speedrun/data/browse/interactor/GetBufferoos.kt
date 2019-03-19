package com.gcaguilar.android.speedrun.data.browse.interactor

import com.gcaguilar.android.speedrun.data.browse.Bufferoo
import com.gcaguilar.android.speedrun.data.executor.PostExecutionThread
import com.gcaguilar.android.speedrun.data.executor.ThreadExecutor
import com.gcaguilar.android.speedrun.data.interactor.SingleUseCase
import com.gcaguilar.android.speedrun.data.repository.BufferooRepository
import io.reactivex.Single

class GetBufferoos(
        private val bufferooRepository: BufferooRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Bufferoo>, Void?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Void?): Single<List<Bufferoo>> {
        return bufferooRepository.getBufferoos()
    }
}