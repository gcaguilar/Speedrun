package org.buffer.android.boilerplate.ui.browse

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gcaguilar.android.speedrun.ui.browse.BrowseBufferoosViewModel
import com.gcaguilar.android.speedrun.ui.browse.BrowseState
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.buffer.android.boilerplate.data.browse.Bufferoo
import org.buffer.android.boilerplate.data.browse.interactor.GetBufferoos
import org.buffer.android.boilerplate.ui.test.util.BufferooFactory
import org.buffer.android.boilerplate.ui.test.util.DataFactory
import org.junit.Rule
import org.junit.Test

class BrowseBufferoosViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    val mockGetBufferoos = mock<GetBufferoos>()

    private val bufferoosViewModel = BrowseBufferoosViewModel(mockGetBufferoos)

    //<editor-fold desc="Success">
    @Test
    fun getBufferoosReturnsSuccess() {
        val list = BufferooFactory.makeBufferooList(2)
        stubGetBufferoos(Single.just(list))
        bufferoosViewModel.getBufferoos()

        assert(bufferoosViewModel.getBufferoos().value is BrowseState.Success)
    }

    @Test
    fun getBufferoosReturnsDataOnSuccess() {
        val list = BufferooFactory.makeBufferooList(2)
        stubGetBufferoos(Single.just(list))
        bufferoosViewModel.getBufferoos()

        assert(bufferoosViewModel.getBufferoos().value?.data == list)
    }

    @Test
    fun getBufferoosReturnsNoMessageOnSuccess() {
        val list = BufferooFactory.makeBufferooList(2)
        stubGetBufferoos(Single.just(list))

        bufferoosViewModel.getBufferoos()

        assert(bufferoosViewModel.getBufferoos().value?.errorMessage == null)
    }
    //</editor-fold>

    //<editor-fold desc="Error">
    @Test
    fun getBufferoosReturnsError() {
        bufferoosViewModel.getBufferoos()
        stubGetBufferoos(Single.error(RuntimeException()))

        assert(bufferoosViewModel.getBufferoos().value is BrowseState.Error)
    }

    @Test
    fun getBufferoosFailsAndContainsNoData() {
        bufferoosViewModel.getBufferoos()
        stubGetBufferoos(Single.error(RuntimeException()))

        assert(bufferoosViewModel.getBufferoos().value?.data == null)
    }

    @Test
    fun getBufferoosFailsAndContainsMessage() {
        val errorMessage = DataFactory.randomUuid()
        bufferoosViewModel.getBufferoos()
        stubGetBufferoos(Single.error(RuntimeException(errorMessage)))

        assert(bufferoosViewModel.getBufferoos().value?.errorMessage == errorMessage)
    }
    //</editor-fold>

    //<editor-fold desc="Loading">
    @Test
    fun getBufferoosReturnsLoading() {
        bufferoosViewModel.getBufferoos()

        assert(bufferoosViewModel.getBufferoos().value is BrowseState.Loading)
    }

    @Test
    fun getBufferoosContainsNoDataWhenLoading() {
        bufferoosViewModel.getBufferoos()

        assert(bufferoosViewModel.getBufferoos().value?.data == null)
    }

    @Test
    fun getBufferoosContainsNoMessageWhenLoading() {
        bufferoosViewModel.getBufferoos()

        assert(bufferoosViewModel.getBufferoos().value?.data == null)
    }
    //</editor-fold>

    private fun stubGetBufferoos(single: Single<List<Bufferoo>>) {
        whenever(mockGetBufferoos.execute(any()))
                .thenReturn(single)
    }
}