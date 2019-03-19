package com.gcaguilar.android.speedrun.data

import com.gcaguilar.android.speedrun.data.browse.Bufferoo
import com.gcaguilar.android.speedrun.data.repository.BufferooRepository
import com.gcaguilar.android.speedrun.data.source.BufferooDataStoreFactory
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Provides an implementation of the [BufferooRepository] interface for communicating to and from
 * data sources
 */
class BufferooDataRepository(private val factory: BufferooDataStoreFactory) : BufferooRepository {

    override fun clearBufferoos(): Completable {
        return factory.retrieveCacheDataStore().clearBufferoos()
    }

    override fun saveBufferoos(bufferoos: List<Bufferoo>): Completable {
        return factory.retrieveCacheDataStore().saveBufferoos(bufferoos)
    }

    override fun getBufferoos(): Single<List<Bufferoo>> {
        return factory.retrieveCacheDataStore().isValidCache()
                .flatMap { cached ->
                    // Get data store based on whether cached data is valid
                    val bufferooDataStore = factory.retrieveDataStore(cached)

                    val bufferooListSource = if (cached) {
                        // Getting data from cache
                        bufferooDataStore.getBufferoos()
                    } else {
                        // Getting data from remote, so result is cached
                        bufferooDataStore.getBufferoos()
                                .flatMap { bufferooList ->
                                    // Once the result have been retrieved, save it to cache and return it
                                    saveBufferoos(bufferooList).toSingle { bufferooList }
                                }
                    }

                    bufferooListSource
                }
    }
}