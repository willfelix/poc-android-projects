package com.example.chateado.model

data class User(val id: String? = "",
                val name: String? = null,
                val email: String? = null,
                val photoUrl: String = "",
                val phone: String? = "")