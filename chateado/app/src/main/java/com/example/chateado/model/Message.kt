package com.example.chateado.model

data class Message( val personId: String? = "",
                    val personPhotoUrl: String? = "",
                    val text: String? = null,
                    val timestamp: Int? = null)