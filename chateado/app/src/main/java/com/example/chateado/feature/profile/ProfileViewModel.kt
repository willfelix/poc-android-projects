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

class ProfileViewModel : ViewModel() {

    private val authRepository = AuthRepository()
    private val userRepository = UserRepository()

    fun currentUser() = authRepository.currentUser()

    fun observeUser(userId: String?) = userRepository.findUser(userId)

}