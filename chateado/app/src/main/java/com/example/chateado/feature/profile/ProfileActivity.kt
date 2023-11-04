package com.example.chateado.feature.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.myslack.feature.contacts.ContactsViewModel
import br.com.myslack.feature.contacts.ProfileViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.chateado.R
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.channel_toolbar_layout.view.*

class ProfileActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(ProfileViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val userId = intent.extras?.getString("userId")

        viewModel.observeUser(userId).observe(this, Observer { user ->

            initActionbar(user.name!!)

            tvName.text = user.name
            tvEmail.text = user.email

            Glide.with(this)
                .load(user.photoUrl)
                .placeholder(R.drawable.ic_avatar_placeholder)
                .apply(RequestOptions.circleCropTransform())
                .into(imgProfile)

        })
    }

    private fun initActionbar(name: String) {
        setSupportActionBar(customToolbar.toolbar)
        customToolbar.txtTitle.text = name
        customToolbar.imgAdd.visibility = View.INVISIBLE
    }


}
