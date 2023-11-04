package com.example.chateado.util

import com.example.chateado.model.User
import com.google.firebase.auth.FirebaseUser

class ParserUtil {

    companion object {

        fun toUser(firebaseUser: FirebaseUser?): User {
            return User(
                firebaseUser?.uid,
                firebaseUser?.displayName,
                firebaseUser?.email,
                firebaseUser?.photoUrl.toString(),
                firebaseUser?.phoneNumber
            )
        }

    }

}