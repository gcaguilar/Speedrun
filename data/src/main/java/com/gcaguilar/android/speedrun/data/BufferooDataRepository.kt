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
        TODO("This should not implement")
    }

    override fun saveBufferoos(bufferoos: List<Bufferoo>): Completable {
        TODO("This should not implement")
    }

    override fun getBufferoos(): Single<List<Bufferoo>> {
        return factory.retrieveRemoteDataStore().getBufferoos()
    }
}