package com.gcaguilar.android.speedrun.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gcaguilar.android.speedrun.data.game.interactor.GetRunsUseCase
import com.gcaguilar.android.speedrun.ui.main.Event
import io.reactivex.disposables.Disposable
import org.joda.time.Period.parse
import org.joda.time.format.PeriodFormatterBuilder
import timber.log.Timber

class DetailViewModel(
        private val getRunsUseCase: GetRunsUseCase
) : ViewModel() {

    private val itemDetailLiveData = MutableLiveData<ItemDetailState>()
    private val navigateToVideo = MutableLiveData<Event<String>>()
    private var videoUri: String? = null
    private var disposable: Disposable? = null

    fun itemDetailLiveData(): LiveData<ItemDetailState> {
        return itemDetailLiveData
    }

    fun navigetToVideo(): LiveData<Event<String>> {
        return navigateToVideo
    }

    fun getDetails(gameId: String?) {
        gameId?.let {
            itemDetailLiveData.value = ItemDetailState.Loading
            disposable = getRunsUseCase.execute(gameId)
                    .subscribe({
                        videoUri = it.videoUri
                        itemDetailLiveData.value = ItemDetailState.Success(it)
                    }, {
                        Timber.e(it)
                        itemDetailLiveData.value = ItemDetailState.Error(it.message)
                    }
                    )
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    fun onFloatingClicked() {
        videoUri?.let {
            navigateToVideo.value = Event(it)
        }
    }

    fun formatDate(time: String): String {
        val period = parse(time)
        val periodFormatter = PeriodFormatterBuilder()
                .printZeroAlways()
                .appendHours()
                .appendSeparator(":")
                .appendMinutes()
                .appendSeparator(":")
                .appendSeconds()
                .toFormatter()
        return periodFormatter.print(period)
    }

}