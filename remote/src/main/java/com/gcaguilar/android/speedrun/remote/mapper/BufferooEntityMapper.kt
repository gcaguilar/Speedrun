package com.gcaguilar.android.speedrun.remote.mapper

import com.gcaguilar.android.speedrun.data.browse.Bufferoo
import com.gcaguilar.android.speedrun.remote.model.BufferooModel

/**
 * Map a [BufferooModel] to and from a [Bufferoo] instance when data is moving between
 * this later and the Data layer
 */
open class BufferooEntityMapper : EntityMapper<BufferooModel, Bufferoo> {

    /**
     * Map an instance of a [BufferooModel] to a [Bufferoo] model
     */
    override fun mapFromRemote(type: BufferooModel): Bufferoo {
        return Bufferoo(type.id, type.name, type.title, type.avatar)
    }
}