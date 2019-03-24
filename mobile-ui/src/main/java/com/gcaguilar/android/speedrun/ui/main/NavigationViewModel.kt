package com.gcaguilar.android.speedrun.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NavigationViewModel : ViewModel() {

    val navigateToItem = MutableLiveData<Event<String>>()

    fun onItemClicked(itemId: String) {
        navigateToItem.value = Event(itemId)
    }
}