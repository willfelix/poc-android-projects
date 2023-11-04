package com.example.chateado.feature.contacts

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.myslack.feature.contacts.ContactsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.chateado.R
import com.example.chateado.feature.base.BaseActivity
import com.example.chateado.feature.message.MessageActivity
import com.example.chateado.feature.myprofile.MyProfileActivity
import com.example.chateado.model.User
import kotlinx.android.synthetic.main.activity_contacts.*
import kotlinx.android.synthetic.main.channel_toolbar_layout.view.*

class ContactsActivity : BaseActivity(), ContactsAdapter.ContactsCallback {

    private val adapter = ContactsAdapter(this)

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(ContactsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        initActionBar()
        initUI()
        initObservables()
    }

    private fun initUI() {
        rvContacts.layoutManager = LinearLayoutManager(this)
        rvContacts.adapter = adapter
    }

    private fun initActionBar() {
        setSupportActionBar(customToolbar.toolbar)
        customToolbar.txtTitle.text = getString(R.string.contacts)

        customToolbar.imgUser.setOnClickListener {
            startActivity(Intent(this, MyProfileActivity::class.java))
        }

        viewModel.currentUser()?.let { user ->
            Glide.with(this)
                .load(user.photoUrl)
                .placeholder(R.drawable.ic_avatar_placeholder)
                .apply(RequestOptions.circleCropTransform())
                .into(customToolbar.imgUser)
        }
    }

    override fun onItemClick(user: User) {
        viewModel.getChannel(user).observe(this, Observer { channel ->

            val intent = Intent(this, MessageActivity::class.java).apply {
                putExtra("channelId", channel.id)
            }

            startActivity(intent)
            finish()
        })
    }

    private fun initObservables() {
        viewModel.observeUsers().observe(this, Observer {
            adapter.updateItems(it)
        })
    }
}
