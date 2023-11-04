package com.example.chateado.repository

import com.google.firebase.auth.FirebaseAuth

class AuthRepository {
    fun currentUser() = FirebaseAuth.getInstance().currentUser
}