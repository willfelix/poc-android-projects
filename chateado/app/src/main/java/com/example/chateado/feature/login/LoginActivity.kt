package com.example.chateado.feature.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.chateado.R
import com.example.chateado.feature.base.BaseActivity
import com.example.chateado.feature.channels.ChannelActivity
import com.example.chateado.feature.contacts.ContactsActivity
import com.example.chateado.model.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    companion object {
        const val GOOGLE_AUTH_REQUEST = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnSignIn.setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent,
                GOOGLE_AUTH_REQUEST
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_AUTH_REQUEST) {

            try {
                showProgressBar()
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.e("ninja", e.message)
                updateUI(null)
            }

        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                var user: FirebaseUser? = null
                if (task.isSuccessful) {
                    user = auth.currentUser
                }

                updateUI(user)
            }
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun updateUI(firebaseUser: FirebaseUser?) {
        hideProgressBar()
        firebaseUser?.let {

            val user = User(firebaseUser.uid,
                            firebaseUser.displayName,
                            firebaseUser.email,
                            firebaseUser.photoUrl.toString(),
                            firebaseUser.phoneNumber)

            db.collection("users").document(user.id!!).set(user).addOnSuccessListener {
                startActivity(Intent(this, ChannelActivity::class.java))
                finish()
            }

        }
    }

}
