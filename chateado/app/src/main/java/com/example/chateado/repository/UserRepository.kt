package com.example.chateado.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chateado.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {

    fun users(): LiveData<List<User>> {
        val items = MutableLiveData<List<User>>()

        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        FirebaseFirestore.getInstance().collection("users")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                querySnapshot?.let {
                    val users = it.toObjects(User::class.java)
                    users.removeIf { user -> user.id == currentUserId }
                    items.value = users
                }
            }

        return items
    }

    fun findUser(userId: String?): LiveData<User> {

        val user = MutableLiveData<User>()

        FirebaseFirestore.getInstance()
            .collection("users")
            .whereEqualTo("id", userId)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                querySnapshot?.let {
                    val users = it.toObjects(User::class.java)
                    user.value = users.get(0)
                }
            }

        return user
    }


}