package com.example.chateado.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chateado.model.Channel
import com.example.chateado.model.Message
import com.example.chateado.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MessageRepository {

    fun messages(channelId: String?): LiveData<List<Message>> {
        val items = MutableLiveData<List<Message>>()

        FirebaseFirestore.getInstance()
            .collection("channels")
            .document(channelId!!)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                querySnapshot?.let {
                    items.value = it.toObjects(Message::class.java)
                }
            }

        return items
    }

    fun create(channelId: String?, message: Message) {

        FirebaseFirestore.getInstance()
            .collection("channels")
            .document(channelId!!)
            .collection("messages")
            .add(message)

    }


}