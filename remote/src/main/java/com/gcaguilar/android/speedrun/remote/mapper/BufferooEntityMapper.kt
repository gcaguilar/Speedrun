package com.gcaguilar.android.speedrun.remote.mapper

import com.gcaguilar.android.speedrun.remote.model.BufferooModel
import org.buffer.android.boilerplate.data.browse.Bufferoo

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