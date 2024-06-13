package com.example.pdmchat.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.pdmchat.databinding.ReadMessageActivityBinding

class MessageAdapter(private val context: Context, private val messages: List<Message>) : BaseAdapter() {

    override fun getCount(): Int {
        return messages.size
    }

    override fun getItem(position: Int): Any {
        return messages[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ReadMessageActivityBinding
        if (convertView == null) {
            binding = ReadMessageActivityBinding.inflate(LayoutInflater.from(context), parent, false)
            convertView?.tag = binding
        } else {
            binding = convertView?.tag as ReadMessageActivityBinding
        }

        val message = messages[position]
        binding.messageSenderTextView.text = message.senderId
        binding.messageDateTextView.text = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(message.timestamp)
        binding.messageTextView.text = message.messageText

        return binding.root
    }
}
