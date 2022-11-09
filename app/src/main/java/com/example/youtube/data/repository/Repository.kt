package com.example.youtube.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtube.BuildConfig
import com.example.youtube.core.common.Constant
import com.example.youtube.core.remote.RetrofitClient
import com.example.youtube.data.remote.ApiService
import com.example.youtube.data.remote.model.Playlist
import com.example.youtube.data.remote.model.Playlists
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    private val apiService: ApiService by lazy {
        RetrofitClient.create();
    }

    fun playlists(): LiveData<List<Playlist>> {
        return getPlayLists()
    }

    fun playlist(id: String): LiveData<Playlists> {
        return getPlaylist(id)
    }

    private fun getPlayLists(): LiveData<List<Playlist>> {
        val data = MutableLiveData<List<Playlist>>()

        apiService.getPlaylists(Constant.channelId, Constant.part, BuildConfig.API_KEY, 50)
            .enqueue(object : Callback<Playlists> {
                override fun onResponse(
                    call: Call<Playlists>,
                    response: Response<Playlists>
                ) {
                    if (response.isSuccessful) {
                        data.value = response.body()?.items
                    }
                }

                override fun onFailure(call: Call<Playlists>, t: Throwable) {
                }
            })
        return data
    }

    private fun getPlaylist(id: String): LiveData<Playlists> {
        val data = MutableLiveData<Playlists>()
        apiService.getPlaylistItem(Constant.part, BuildConfig.API_KEY, id, 50)
            .enqueue(object : Callback<Playlists> {
                override fun onResponse(call: Call<Playlists>, response: Response<Playlists>) {
                    if (response.isSuccessful) {
                        data.value = response.body()
                    }
                }

                override fun onFailure(call: Call<Playlists>, t: Throwable) {
                }
            })
        return data
    }
}