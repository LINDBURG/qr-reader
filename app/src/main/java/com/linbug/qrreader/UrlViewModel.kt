package com.linbug.qrreader

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class UrlViewModel(private val repository: UrlRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allUrls: LiveData<List<Url>> = repository.allUrls.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(url: Url) = viewModelScope.launch {
        repository.insert(url)
    }
}

class UrlViewModelFactory(private val repository: UrlRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UrlViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UrlViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}