package com.gcaguilar.android.speedrun.data.interactor

import com.gcaguilar.android.speedrun.data.executor.PostExecutionThread
import com.gcaguilar.android.speedrun.data.executor.ThreadExecutor
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Abstract class for a UseCase that returns an instance of a [Observable].
 */
abstract class ObservableUseCase<T, in Params> constructor(
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) {

    /**
     * Builds a [Observable] which will be used when the current [ObservableUseCase] is executed.
     */
    protected abstract fun buildUseCaseObservable(params: Params? = null): Observable<T>

    /**
     * Executes the current use case.
     */
    fun execute(params: Params? = null): Observable<T> {
        return this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
    }
}