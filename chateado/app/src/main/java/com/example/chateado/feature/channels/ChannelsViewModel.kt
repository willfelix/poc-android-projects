package br.com.myslack.feature.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.chateado.model.Channel
import com.example.chateado.model.User
import com.example.chateado.repository.AuthRepository
import com.example.chateado.repository.ChannelRepository
import com.example.chateado.repository.UserRepository

class ChannelsViewModel : ViewModel() {

    private val channelsData : LiveData<List<Channel>>

    private val authRepository = AuthRepository()
    private val channelRepository = ChannelRepository()

    init {
        val uid = currentUser()?.uid
        channelsData = channelRepository.channels(uid)
    }

    fun observeChannels() = channelsData

    fun currentUser() = authRepository.currentUser()

}