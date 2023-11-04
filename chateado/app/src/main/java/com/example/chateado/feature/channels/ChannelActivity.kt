package com.example.chateado.feature.channels

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.myslack.feature.contacts.ChannelsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.chateado.R
import com.example.chateado.feature.base.BaseActivity
import com.example.chateado.feature.contacts.ContactsActivity
import com.example.chateado.feature.message.MessageActivity
import com.example.chateado.feature.myprofile.MyProfileActivity
import com.example.chateado.model.Channel
import com.example.chateado.util.ParserUtil
import kotlinx.android.synthetic.main.activity_channel.*
import kotlinx.android.synthetic.main.channel_toolbar_layout.view.*

class ChannelActivity : BaseActivity(), ChannelAdapter.ChannelsCallback {

    private val adapter = ChannelAdapter(this)

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(ChannelsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel)

        initActionBar()
        initUI()
        initObservables()
    }

    private fun initUI() {
        rvChannels.layoutManager = LinearLayoutManager(this)
        rvChannels.adapter = adapter
    }

    private fun initActionBar() {
        setSupportActionBar(customToolbar.toolbar)
        customToolbar.txtTitle.text = getString(R.string.channels)

        customToolbar.imgUser.setOnClickListener {
            startActivity(Intent(this, MyProfileActivity::class.java))
        }

        customToolbar.imgAdd.setOnClickListener {
            startActivity(Intent(this, ContactsActivity::class.java))
        }

        viewModel.currentUser()?.let { user ->
            Glide.with(this)
                .load(user.photoUrl)
                .placeholder(R.drawable.ic_avatar_placeholder)
                .apply(RequestOptions.circleCropTransform())
                .into(customToolbar.imgUser)
        }
    }

    override fun onItemClick(channel: Channel) {
        val intent = Intent(this, MessageActivity::class.java).apply {
            putExtra("channelId", channel.id)
        }

        startActivity(intent)
    }

    private fun initObservables() {
        viewModel.observeChannels().observe(this, Observer {
            val currentUser = ParserUtil.toUser(viewModel.currentUser())
            adapter.updateItems(currentUser, it)
        })
    }
}
