package com.example.chateado.feature.base

import androidx.appcompat.app.AppCompatActivity
import com.example.chateado.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

open class BaseActivity : AppCompatActivity() {

    protected val auth: FirebaseAuth = FirebaseAuth.getInstance()
    protected val db: FirebaseFirestore = FirebaseFirestore.getInstance();

    protected val googleSignInClient by lazy {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        GoogleSignIn.getClient(this, gso)
    }
}