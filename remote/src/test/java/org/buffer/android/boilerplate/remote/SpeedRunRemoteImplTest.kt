package org.buffer.android.boilerplate.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gcaguilar.android.speedrun.remote.SpeedRunRemoteImpl
import com.gcaguilar.android.speedrun.remote.SpeedRunService
import com.gcaguilar.android.speedrun.remote.mapper.BufferooEntityMapper
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.buffer.android.boilerplate.remote.test.factory.BufferooFactory
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SpeedRunRemoteImplTest {

    private val entityMapper = mock<BufferooEntityMapper>()
    private val bufferooService = mock<SpeedRunService>()

    private val bufferooRemoteImpl = SpeedRunRemoteImpl(bufferooService, entityMapper)

    //<editor-fold desc="Get Bufferoos">
    @Test
    fun getBufferoosCompletes() {
        stubBufferooServiceGetBufferoos(Single.just(BufferooFactory.makeBufferooResponse()))
        val testObserver = bufferooRemoteImpl.getBufferoos().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBufferoosReturnsData() {
        val bufferooResponse = BufferooFactory.makeBufferooResponse()
        stubBufferooServiceGetBufferoos(Single.just(bufferooResponse))
        val bufferooEntities = mutableListOf<Bufferoo>()
        bufferooResponse.team.forEach {
            bufferooEntities.add(entityMapper.mapFromRemote(it))
        }

        val testObserver = bufferooRemoteImpl.getBufferoos().test()
        testObserver.assertValue(bufferooEntities)
    }
    //</editor-fold>

    private fun stubBufferooServiceGetBufferoos(
            observable:
            Single<SpeedRunService.BufferooResponse>
    ) {
        whenever(bufferooService.getBufferoos())
                .thenReturn(observable)
    }
}