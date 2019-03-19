package com.gcaguilar.android.speedrun.data.source

import com.gcaguilar.android.speedrun.data.browse.Bufferoo
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Interface defining methods for the data operations related to Bufferroos.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface BufferooDataStore {

    fun clearBufferoos(): Completable

    fun saveBufferoos(bufferoos: List<Bufferoo>): Completable

    fun getBufferoos(): Single<List<Bufferoo>>

    fun isValidCache(): Single<Boolean>

    fun setLastCacheTime(lastCache: Long)
}