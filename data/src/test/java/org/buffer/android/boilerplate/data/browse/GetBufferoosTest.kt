package org.buffer.android.boilerplate.data.browse

import com.gcaguilar.android.speedrun.data.browse.Bufferoo
import com.gcaguilar.android.speedrun.data.browse.interactor.GetBufferoos
import com.gcaguilar.android.speedrun.data.executor.PostExecutionThread
import com.gcaguilar.android.speedrun.data.executor.ThreadExecutor
import com.gcaguilar.android.speedrun.data.repository.BufferooRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.buffer.android.boilerplate.data.test.factory.BufferooFactory
import org.junit.Test

class GetBufferoosTest {

    private val mockThreadExecutor = mock<ThreadExecutor>()
    private val mockPostExecutionThread = mock<PostExecutionThread>()
    private val mockBufferooRepository = mock<BufferooRepository>()

    private val getBufferoos = GetBufferoos(
            mockBufferooRepository, mockThreadExecutor,
            mockPostExecutionThread
    )

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getBufferoos.buildUseCaseObservable(null)
        verify(mockBufferooRepository).getBufferoos()
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubBufferooRepositoryGetBufferoos(Single.just(BufferooFactory.makeBufferooList(2)))
        val testObserver = getBufferoos.buildUseCaseObservable(null).test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val bufferoos = BufferooFactory.makeBufferooList(2)
        stubBufferooRepositoryGetBufferoos(Single.just(bufferoos))
        val testObserver = getBufferoos.buildUseCaseObservable(null).test()
        testObserver.assertValue(bufferoos)
    }

    private fun stubBufferooRepositoryGetBufferoos(single: Single<List<Bufferoo>>) {
        whenever(mockBufferooRepository.getBufferoos())
                .thenReturn(single)
    }
}