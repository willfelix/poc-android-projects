package br.com.myslack.feature.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.chateado.model.Message
import com.example.chateado.model.User
import com.example.chateado.repository.AuthRepository
import com.example.chateado.repository.ChannelRepository
import com.example.chateado.repository.MessageRepository
import com.example.chateado.repository.UserRepository
import java.util.*

class MessagesViewModel : ViewModel() {

    private val authRepository = AuthRepository()
    private val channelRepository = ChannelRepository()
    private val messageRepository = MessageRepository()

    fun currentUser() = authRepository.currentUser()

    fun observeMessages(channelId: String?) = messageRepository.messages(channelId)

    fun observeChannel(channelId: String?) = channelRepository.find(channelId)

    fun sendMessage(channelId: String?, text: String) {
        val currentUser = currentUser()
        val message = Message(currentUser?.uid, currentUser?.photoUrl.toString(), text, Date().time.toInt())

        messageRepository.create(channelId, message)
        channelRepository.updateLastMessage(channelId, text)
    }

}