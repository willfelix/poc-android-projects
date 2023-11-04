package com.example.chateado.model

data class Channel( val id: String? = null,
                    val lastMessage: String? = null,
                    val userIds: List<String?>? = null,
                    val users: List<User>? = null)