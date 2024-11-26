package ru.raperan.poopoo.elements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.session.MediaController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
class PlayBarViewModel : ViewModel() {

    var controller: MediaController? = null
    var currentProgress = 0f

    fun start(updateProgress: (Float) -> Unit) {
        viewModelScope.launch {
            loadProgress(updateProgress)
        }
    }

    /** Iterate the progress value */
    suspend fun loadProgress(updateProgress: (Float) -> Unit) {
        //suspend fun loadProgress(updateProgress: (Float) -> Unit, progressFetcher: () -> Float, controller: MediaController) {
        while (true) {
            currentProgress = if (this.controller != null) this.controller!!.currentPosition.toFloat() / this.controller!!.contentDuration.toFloat() else 0F
            updateProgress(currentProgress)
            delay(100)
        }
    }
}

