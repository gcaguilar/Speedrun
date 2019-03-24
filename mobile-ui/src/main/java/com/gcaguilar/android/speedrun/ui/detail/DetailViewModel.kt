package com.gcaguilar.android.speedrun.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gcaguilar.android.speedrun.data.game.interactor.GetRunsUseCase
import io.reactivex.disposables.Disposable
import timber.log.Timber

class DetailViewModel(
        private val getRunsUseCase: GetRunsUseCase
) : ViewModel() {

    private val itemDetailLiveData = MutableLiveData<ItemDetailState>()
    private var disposable: Disposable? = null

    fun itemDetailLiveData(): LiveData<ItemDetailState> {
        return itemDetailLiveData
    }

    fun getDetails(gameId: String?) {
        gameId?.let {
            itemDetailLiveData.value = ItemDetailState.Loading
            disposable = getRunsUseCase.execute(gameId)
                    .subscribe({
                        itemDetailLiveData.value = ItemDetailState.Success(it)
                    }, {
                        Timber.e(it)
                    }
                    )
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}