package com.gcaguilar.android.speedrun.data.repository

import com.gcaguilar.android.speedrun.data.browse.Bufferoo
import io.reactivex.Completable
import io.reactivex.Single

interface BufferooRepository {

    fun clearBufferoos(): Completable

    fun saveBufferoos(bufferoos: List<Bufferoo>): Completable

    fun getBufferoos(): Single<List<Bufferoo>>
}