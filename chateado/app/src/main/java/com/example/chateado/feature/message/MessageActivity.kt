package com.example.chateado.feature.message

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.myslack.feature.contacts.MessagesViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.chateado.R
import com.example.chateado.feature.base.BaseActivity
import com.example.chateado.feature.myprofile.MyProfileActivity
import com.example.chateado.feature.profile.ProfileActivity
import com.example.chateado.model.User
import com.example.chateado.util.ParserUtil
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.message_toolbar_layout.view.*

class MessageActivity : BaseActivity() {

    private val adapter = MessageAdapter()

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MessagesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        val channelId = intent.extras?.getString("channelId")

        initUI(channelId)
        initObservers(channelId)
    }

    private fun initUI(channelId: String?) {

        rvMessages.adapter = adapter
        rvMessages.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
            scrollToPosition(adapter.itemCount - 1);
        }

        btnSendMessage.setOnClickListener {
            val message = editMessage.text.toString()
            if (message.isNotBlank()) {
                editMessage.text.clear()
                viewModel.sendMessage(channelId, message)
            }

            editMessage.findFocus()

            (rvMessages.layoutManager as LinearLayoutManager)
                .scrollToPosition(adapter.getItemCount() - 1);
        }

    }

    private fun initActionBar(user: User?) {
        setSupportActionBar(customToolbar.toolbar)

        val intent = Intent(this, ProfileActivity::class.java).apply {
            putExtra("userId", user?.id)
        }

        customToolbar.imgUser.setOnClickListener { startActivity(intent) }
        customToolbar.txtName.setOnClickListener { startActivity(intent) }
        customToolbar.imgBack.setOnClickListener { finish() }

        customToolbar.txtName.text = user?.name

        Glide.with(this)
            .load(user?.photoUrl)
            .placeholder(R.drawable.ic_avatar_placeholder)
            .apply(RequestOptions.circleCropTransform())
            .into(customToolbar.imgUser)
    }

    private fun initObservers(channelId: String?) {

        viewModel.observeChannel(channelId).observe(this, Observer { channel ->

            val user = channel.users?.filterNot { it.id == viewModel.currentUser()?.uid }?.firstOrNull()
            initActionBar(user)

        })

        viewModel.observeMessages(channelId).observe(this, Observer {

            adapter.updateItems( ParserUtil.toUser(viewModel.currentUser()), it)

            (rvMessages.layoutManager as LinearLayoutManager)
                .scrollToPosition(adapter.getItemCount() - 1);

        })

    }
}
