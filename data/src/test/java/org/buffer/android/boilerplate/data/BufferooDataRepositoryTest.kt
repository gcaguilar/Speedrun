package org.buffer.android.boilerplate.data

import com.gcaguilar.android.speedrun.data.BufferooDataRepository
import com.gcaguilar.android.speedrun.data.browse.Bufferoo
import com.gcaguilar.android.speedrun.data.source.BufferooDataStore
import com.gcaguilar.android.speedrun.data.source.BufferooDataStoreFactory
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Single
import org.buffer.android.boilerplate.data.test.factory.BufferooFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
open class BufferooDataRepositoryTest {

    private val bufferooDataStoreFactory = mock<BufferooDataStoreFactory>()
    private val bufferooCacheDataStore = mock<BufferooDataStore>()
    private val bufferooRemoteDataStore = mock<BufferooDataStore>()

    private val bufferooDataRepository = BufferooDataRepository(bufferooDataStoreFactory)

    @Before
    fun setUp() {
        stubBufferooDataStoreFactoryRetrieveCacheDataStore()
        stubBufferooDataStoreFactoryRetrieveRemoteDataStore()
    }

    //<editor-fold desc="Clear Bufferoos">
    @Test
    fun clearBufferoosCompletes() {
        stubBufferooCacheClearBufferoos(Completable.complete())
        val testObserver = bufferooDataRepository.clearBufferoos().test()
        testObserver.assertComplete()
    }

    @Test
    fun clearBufferoosCallsCacheDataStore() {
        stubBufferooCacheClearBufferoos(Completable.complete())
        bufferooDataRepository.clearBufferoos().test()
        verify(bufferooCacheDataStore).clearBufferoos()
    }

    @Test
    fun clearBufferoosNeverCallsRemoteDataStore() {
        stubBufferooCacheClearBufferoos(Completable.complete())
        bufferooDataRepository.clearBufferoos().test()
        verify(bufferooRemoteDataStore, never()).clearBufferoos()
    }
    //</editor-fold>

    //<editor-fold desc="Save Bufferoos">
    @Test
    fun saveBufferoosCompletes() {
        stubBufferooCacheSaveBufferoos(Completable.complete())
        val testObserver = bufferooDataRepository.saveBufferoos(
                BufferooFactory.makeBufferooList(2)
        ).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveBufferoosCallsCacheDataStore() {
        stubBufferooCacheSaveBufferoos(Completable.complete())
        bufferooDataRepository.saveBufferoos(BufferooFactory.makeBufferooList(2)).test()
        verify(bufferooCacheDataStore).saveBufferoos(any())
    }

    @Test
    fun saveBufferoosNeverCallsRemoteDataStore() {
        stubBufferooCacheSaveBufferoos(Completable.complete())
        bufferooDataRepository.saveBufferoos(BufferooFactory.makeBufferooList(2)).test()
        verify(bufferooRemoteDataStore, never()).saveBufferoos(any())
    }
    //</editor-fold>

    //<editor-fold desc="Get Bufferoos">
    @Test
    fun getBufferoosCompletes() {
        stubBufferooCacheDataStoreIsCached(Single.just(true))
        stubBufferooDataStoreFactoryRetrieveDataStore(bufferooCacheDataStore)
        stubBufferooCacheDataStoreGetBufferoos(
                Single.just(
                        BufferooFactory.makeBufferooList(2)
                )
        )
        stubBufferooCacheSaveBufferoos(Completable.complete())
        val testObserver = bufferooDataRepository.getBufferoos().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBufferoosReturnsData() {
        stubBufferooCacheDataStoreIsCached(Single.just(true))
        stubBufferooDataStoreFactoryRetrieveDataStore(bufferooCacheDataStore)
        stubBufferooCacheSaveBufferoos(Completable.complete())
        val bufferoos = BufferooFactory.makeBufferooList(2)
        stubBufferooCacheDataStoreGetBufferoos(Single.just(bufferoos))

        val testObserver = bufferooDataRepository.getBufferoos().test()
        testObserver.assertValue(bufferoos)
    }

    @Test
    fun getBufferoosSavesBufferoosWhenFromCacheDataStore() {
        stubBufferooDataStoreFactoryRetrieveDataStore(bufferooCacheDataStore)
        stubBufferooCacheSaveBufferoos(Completable.complete())
        bufferooDataRepository.saveBufferoos(BufferooFactory.makeBufferooList(2)).test()
        verify(bufferooCacheDataStore).saveBufferoos(any())
    }

    @Test
    fun getBufferoosNeverSavesBufferoosWhenFromRemoteDataStore() {
        stubBufferooDataStoreFactoryRetrieveDataStore(bufferooRemoteDataStore)
        stubBufferooCacheSaveBufferoos(Completable.complete())
        bufferooDataRepository.saveBufferoos(BufferooFactory.makeBufferooList(2)).test()
        verify(bufferooRemoteDataStore, never()).saveBufferoos(any())
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubBufferooCacheSaveBufferoos(completable: Completable) {
        whenever(bufferooCacheDataStore.saveBufferoos(any()))
                .thenReturn(completable)
    }

    private fun stubBufferooCacheDataStoreIsCached(single: Single<Boolean>) {
        whenever(bufferooCacheDataStore.isValidCache())
                .thenReturn(single)
    }

    private fun stubBufferooCacheDataStoreGetBufferoos(single: Single<List<Bufferoo>>) {
        whenever(bufferooCacheDataStore.getBufferoos())
                .thenReturn(single)
    }

    private fun stubBufferooCacheClearBufferoos(completable: Completable) {
        whenever(bufferooCacheDataStore.clearBufferoos())
                .thenReturn(completable)
    }

    private fun stubBufferooDataStoreFactoryRetrieveCacheDataStore() {
        whenever(bufferooDataStoreFactory.retrieveCacheDataStore())
                .thenReturn(bufferooCacheDataStore)
    }

    private fun stubBufferooDataStoreFactoryRetrieveRemoteDataStore() {
        whenever(bufferooDataStoreFactory.retrieveRemoteDataStore())
                .thenReturn(bufferooCacheDataStore)
    }

    private fun stubBufferooDataStoreFactoryRetrieveDataStore(dataStore: BufferooDataStore) {
        whenever(bufferooDataStoreFactory.retrieveDataStore(any()))
                .thenReturn(dataStore)
    }
    //</editor-fold>

}