import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pdmchat.databinding.ActivitySendMessageBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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
            val message = Message("sender_id", messageText, System.currentTimeMillis())

            messageId?.let {
                database.child(recipient).child(it).setValue(message)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            finish()
                        } else {
                            // Handle failure
                        }
                    }
            }
        }
    }
}
