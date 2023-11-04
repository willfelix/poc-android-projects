package com.example.chateado.util

import android.icu.text.SimpleDateFormat
import com.example.chateado.model.User
import com.google.firebase.auth.FirebaseUser
import java.util.*

class DateUtil {

    companion object {

        fun formatTimestamp(timestamp: Int, pattern: String = "HH:mm"): String {
            val datetime = Date( timestamp.toLong() )
            return formatDate(datetime, pattern)
        }

        fun formatDate(date: Date, pattern: String = "HH:mm"): String {
            val sdf = SimpleDateFormat(pattern)
            return sdf.format(date)
        }

    }

}