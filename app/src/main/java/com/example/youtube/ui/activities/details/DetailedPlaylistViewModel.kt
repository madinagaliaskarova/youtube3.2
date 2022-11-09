package com.example.youtube.ui.activities.details

import com.example.youtube.App.Companion.repository
import com.example.youtube.core.ui.BaseViewModel

class DetailedPlaylistViewModel : BaseViewModel() {
    fun getPlaylist(id: String) = repository.playlist(id)
}