package com.example.chateado.feature.message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.chateado.R
import com.example.chateado.model.Message
import com.example.chateado.model.User
import com.example.chateado.util.DateUtil
import java.util.*

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private var currentUser: User? = null
    private var items: List<Message>? = null

    private val ME = 1
    private val OTHERS = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layout = R.layout.row_message
        if (ME == viewType) {
            layout = R.layout.row_my_message
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        items?.let {
            if (it[position].personId == currentUser?.id) {
                return ME;
            }
        }

        return OTHERS;
    }

    override fun getItemCount() = items?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items?.let { holder.bind(currentUser, it[position]) }
    }

    fun updateItems(user: User?, items: List<Message>?) {
        this.currentUser = user
        this.items = items

        notifyDataSetChanged()

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(user: User?, message: Message) {

            if ( user?.id != message.personId ) {
                val imgView = itemView.findViewById<ImageView>(R.id.imgMessageUser)
                if (imgView != null) {
                    Glide.with(itemView.context)
                        .load(message.personPhotoUrl)
                        .placeholder(R.drawable.ic_avatar_placeholder)
                        .apply(RequestOptions.circleCropTransform())
                        .into(imgView)
                }
            }

            var txtMessage = itemView.findViewById<TextView>(R.id.txtMessage)
            var txtTimestamp = itemView.findViewById<TextView>(R.id.txtTimestamp)

            txtMessage.text = message.text
            txtTimestamp.text = DateUtil.formatTimestamp(message.timestamp!!)
        }
    }
}