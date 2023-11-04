package br.com.myslack.feature.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.chateado.model.Channel
import com.example.chateado.model.User
import com.example.chateado.repository.AuthRepository
import com.example.chateado.repository.ChannelRepository
import com.example.chateado.repository.UserRepository
import com.example.chateado.util.Md5Util
import com.example.chateado.util.ParserUtil

class ContactsViewModel : ViewModel() {

    private val usersData : LiveData<List<User>>

    private val userRepository = UserRepository()
    private val authRepository = AuthRepository()
    private val channelRepository = ChannelRepository()

    init {
        usersData = userRepository.users()
    }

    fun observeUsers() = usersData

    fun currentUser() = authRepository.currentUser()

    fun getChannel(user: User): LiveData<Channel> {
        val currentUser = ParserUtil.toUser(currentUser())
        val id = Md5Util.encrypt(currentUser.id + user.id)

        val channel = Channel(
            id,
            "",
            listOf(currentUser.id, user.id),
            listOf(currentUser, user)
        )

        return channelRepository.findOrCreate(channel)
    }

}