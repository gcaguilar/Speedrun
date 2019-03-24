package com.gcaguilar.android.speedrun.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gcaguilar.android.speedrun.data.game.interactor.GetGameListUseCase
import io.reactivex.disposables.Disposable
import timber.log.Timber

class GameListViewModel(
        private val gameListUseCase: GetGameListUseCase
) : ViewModel() {

    private val gameListLiveData: MutableLiveData<GameListState> = MutableLiveData()
    private var disposable: Disposable? = null

    fun getGameListLiveData(): LiveData<GameListState> {
        return gameListLiveData
    }

    fun fecthList() {
        gameListLiveData.value = GameListState.Loading

        disposable = gameListUseCase.execute()
                .subscribe({
                    gameListLiveData.value = GameListState.Success(it)
                }, {
                    Timber.e(it)
                    it.message?.let { messageSource ->
                        gameListLiveData.value = GameListState.Error(messageSource)
                    }
                })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}
