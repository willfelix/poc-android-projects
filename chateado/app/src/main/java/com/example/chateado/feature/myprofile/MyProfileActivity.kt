package com.example.chateado.feature.myprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.chateado.R
import com.example.chateado.feature.base.BaseActivity
import com.example.chateado.feature.login.LoginActivity
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.channel_toolbar_layout.view.*

class MyProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        initUI()
        initActionbar()
    }

    private fun initUI() {
        auth.currentUser?.let { user ->
            tvName.text = user.displayName
            tvEmail.text = user.email

            Glide.with(this)
                .load(user.photoUrl)
                .placeholder(R.drawable.ic_avatar_placeholder)
                .apply(RequestOptions.circleCropTransform())
                .into(imgProfile)
        }

        btnLogout.setOnClickListener {
            auth.signOut()
            googleSignInClient.signOut().addOnSuccessListener {
                val intent = Intent(this, LoginActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }

                startActivity(intent)
                finish()
            }
        }
    }

    private fun initActionbar() {
        setSupportActionBar(customToolbar.toolbar)
        customToolbar.txtTitle.text = getString(R.string.my_profile)
        customToolbar.imgAdd.visibility = View.INVISIBLE
    }
}
