package com.example.pdmchat.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pdmchat.databinding.ActivitySendMessageBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth

class SendMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySendMessageBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarIn.toolbar)

        database = FirebaseDatabase.getInstance().getReference("messages")

        binding.sendButton.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        val recipient = binding.recipientEditText.text.toString().trim()
        val messageText = binding.messageEditText.text.toString().trim()

        if (recipient.isNotEmpty() && messageText.isNotEmpty()) {
            val messageId = database.push().key
            val senderId = getCurrentUserId()
            val message = Message(senderId, recipient, messageText, System.currentTimeMillis())

            messageId?.let {
                database.child(recipient).child(it).setValue(message)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            finish()
                        }
                    }
            }
        }
    }

    private fun getCurrentUserId(): String {
        val user = FirebaseAuth.getInstance().currentUser
        return user?.uid ?: "anonymous"
    }
}