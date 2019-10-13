package com.star_zero.navigation_keep_fragment_sample.util

import androidx.lifecycle.ViewModel

class NavigationViewModel : ViewModel() {
    val navigateDetail = SingleLiveEvent<Void>()
    var isShowDetail = false

    fun navigateToDetail() {
        isShowDetail = true
        navigateDetail.call()
    }
}
