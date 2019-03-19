package com.gcaguilar.android.speedrun.data.source

/**
 * Create an instance of a BufferooDataStore
 */
open class BufferooDataStoreFactory(
        private val bufferooCacheDataStore: BufferooDataStore,
        private val bufferooRemoteDataStore: BufferooDataStore
) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(isValidCache: Boolean): BufferooDataStore {
        if (isValidCache) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveCacheDataStore(): BufferooDataStore {
        return bufferooCacheDataStore
    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveRemoteDataStore(): BufferooDataStore {
        return bufferooRemoteDataStore
    }
}