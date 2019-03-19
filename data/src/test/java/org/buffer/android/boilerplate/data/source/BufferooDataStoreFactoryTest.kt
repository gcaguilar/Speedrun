package org.buffer.android.boilerplate.data.source

import com.gcaguilar.android.speedrun.data.source.BufferooDataStore
import com.gcaguilar.android.speedrun.data.source.BufferooDataStoreFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BufferooDataStoreFactoryTest {

    private val bufferooCacheDataStore = mock<BufferooDataStore>()
    private val bufferooRemoteDataStore = mock<BufferooDataStore>()

    private val bufferooDataStoreFactory = BufferooDataStoreFactory(
            bufferooCacheDataStore, bufferooRemoteDataStore
    )

    //<editor-fold desc="Retrieve Data Store">
    @Test
    fun retrieveDataStoreWhenNotCachedReturnsRemoteDataStore() {
        stubBufferooCacheIsValidCache(Single.just(false))
        val bufferooDataStore = bufferooDataStoreFactory.retrieveDataStore(false)
        assertTrue(bufferooDataStore == bufferooRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreReturnsCacheDataStore() {
        stubBufferooCacheIsValidCache(Single.just(true))
        val bufferooDataStore = bufferooDataStoreFactory.retrieveDataStore(true)
        assertTrue(bufferooDataStore == bufferooCacheDataStore)
    }
    //</editor-fold>

    //<editor-fold desc="Retrieve Remote Data Store">
    @Test
    fun retrieveRemoteDataStoreReturnsRemoteDataStore() {
        val bufferooDataStore = bufferooDataStoreFactory.retrieveRemoteDataStore()
        assertTrue(bufferooDataStore == bufferooRemoteDataStore)
    }
    //</editor-fold>

    //<editor-fold desc="Retrieve Cache Data Store">
    @Test
    fun retrieveCacheDataStoreReturnsCacheDataStore() {
        val bufferooDataStore = bufferooDataStoreFactory.retrieveCacheDataStore()
        assertTrue(bufferooDataStore == bufferooCacheDataStore)
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubBufferooCacheIsValidCache(single: Single<Boolean>) {
        whenever(bufferooCacheDataStore.isValidCache())
                .thenReturn(single)
    }
    //</editor-fold>

}