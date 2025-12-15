package com.example.whatsapp.presentation.viewmodels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.whatsapp.models.Message
import com.example.whatsapp.presentation.chat_box.ChatListModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.ByteArrayInputStream
import java.io.InputStream
import kotlin.io.encoding.ExperimentalEncodingApi

class BaseViewModel : ViewModel() {

    fun searchUserByPhoneNumber(phoneNumber: String, callback: (ChatListModel?) -> Unit) {

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser == null) {
            Log.e("BaseViewModel", "User is Not Authenticated")
            callback(null)
            return
        }

        val databaseReference = FirebaseDatabase.getInstance().getReference("users")
        databaseReference.orderByChild("phoneNumber").equalTo(phoneNumber)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.children.first().getValue(ChatListModel::class.java)
                        callback(user)

                    } else {
                        callback(null)

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(
                        "BaseViewModel",
                        "Error Fetching User: ${error.message}, Details: ${error.details}"
                    )
                    callback(null)
                }
            }
            )


    }


    fun getChatForUser(userId: String, callback: (List<ChatListModel>) -> Unit) {

        val chatref = FirebaseDatabase.getInstance().getReference("users/$userId/chats")
        chatref.orderByChild("userId").equalTo(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    val chatList = mutableListOf<ChatListModel>()
                    for (childSnapshot in snapshot.children) {
                        val chat = childSnapshot.getValue(ChatListModel::class.java)

                        if (chat != null) {
                            chatList.add(chat)
                        }

                    }
                    callback(chatList)
                }

                override fun onCancelled(error: DatabaseError) {

                    Log.e(
                        "BaseViewModel",
                        "Error Fetching User Chats: ${error.message}, Details: ${error.details}"
                    )
                    callback(emptyList())

                }
            }
            )
    }


    private val _chatList = MutableStateFlow<List<ChatListModel>>(emptyList())
    val chatList = _chatList.asStateFlow()


    init {
        loadChatData()
    }


    private fun loadChatData() {

        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        if (currentUserId != null) {

            val chatRef = FirebaseDatabase.getInstance().getReference("chats")
            chatRef.orderByChild("userId").equalTo(currentUserId)
                .addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val chatList = mutableListOf<ChatListModel>()
                        for (childSnapshot in snapshot.children) {
                            val chat = childSnapshot.getValue(ChatListModel::class.java)

                            if (chat != null) {
                                chatList.add(chat)
                            }
                        }
                        _chatList.value = chatList
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("BaseViewModel", "Error fetching chat data: ${error.message}")

                    }
                })


        }
    }


    fun addChat(newChat: ChatListModel) {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        if (currentUserId != null) {

            val newChatRef = FirebaseDatabase.getInstance().getReference("chats").push()
            val chatWithUser = newChat.copy(currentUserId)
            newChatRef.setValue(chatWithUser).addOnSuccessListener {

                Log.d("BaseViewModel", "Chat added successfully in database")

            }.addOnFailureListener { exception ->
                Log.e("BaseViewModel", "Error adding chat to database: ${exception.message}")
            }
        } else {
            Log.e("BaseViewModel", "User is not authenticated")
        }


    }


    private val dataBaseReference = FirebaseDatabase.getInstance().reference

    fun sendMessage(senderPhoneNumber: String, receiverPhoneNumber: String, messageText: String) {

        val messageId = dataBaseReference.push().key ?: return
        val message = Message(
            senderPhoneNumber = senderPhoneNumber,
            message = messageText,
            timestamp = System.currentTimeMillis()
        )

        dataBaseReference.child("messages")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)
            .child(messageId)
            .setValue(message)

        dataBaseReference.child("messages")
            .child(receiverPhoneNumber)
            .child(senderPhoneNumber)
            .child(messageId)
            .setValue(message)

    }

    fun getMessage(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        onNewMessage: (Message) -> Unit
    ) {
        val messageRef = dataBaseReference.child("messages")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)

        messageRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val message = snapshot.getValue(Message::class.java)
                if (message != null) {
                    onNewMessage(message)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("BaseViewModel", "Error fetching messages: ${error.message}")
            }
        })

    }


    fun fetchLastMessageForChat(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        onLastMessageFetched: (String, String) -> Unit
    ) {
        val chatRef = FirebaseDatabase.getInstance().reference
            .child("messages")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)

        chatRef.orderByChild("timestamp").limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val lastMessage =
                            snapshot.children.firstOrNull()?.child("message")?.value as? String
                        val timestamp =
                            snapshot.children.firstOrNull()?.child("timestamp")?.value as? String
                        onLastMessageFetched(lastMessage ?: "No message", timestamp ?: "--:--")
                    } else {
                        onLastMessageFetched("No message", "--:--")
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    onLastMessageFetched("No message", "--:--")
                }
            })
    }


    fun loadChatList(
        currentUserPhoneNumber: String,
        onChatListLoaded: (List<ChatListModel>) -> Unit
    ) {

        val chatList = mutableListOf<ChatListModel>()
        val chatRef = FirebaseDatabase.getInstance().reference
            .child("chats")
            .child(currentUserPhoneNumber)

        chatRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    snapshot.children.forEach { child ->

                        val phoneNumber = child.key ?: return@forEach
                        val name = child.child("name").value as? String ?: "Unknown"
                        val image = child.child("image").value as? String

                        val profileImageBitmap = image?.let { decodeBase64ToBitmap(it) }

                        fetchLastMessageForChat(
                            currentUserPhoneNumber,
                            phoneNumber
                        ) { lastMessage, time ->

                            chatList.add(
                                ChatListModel(
                                    name = name,
                                    image = profileImageBitmap,
                                    message = lastMessage,
                                    time = time
                                )
                            )

                            if (chatList.size == snapshot.childrenCount.toInt()) {
                                onChatListLoaded(chatList)
                            }
                        }
                    }
                } else {
                    onChatListLoaded(emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onChatListLoaded(emptyList())
            }
        })

    }


    @OptIn(ExperimentalEncodingApi::class)
    private fun decodeBase64ToBitmap(base64Image: String): Bitmap? {
        return try {
            val decodedByte =
                android.util.Base64.decode(base64Image, android.util.Base64.DEFAULT)

            BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
        } catch (e: Exception) {
            null
        }
    }


    @OptIn(ExperimentalEncodingApi::class)
    fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedByte =
                android.util.Base64.decode(base64String, android.util.Base64.DEFAULT)

            val inputStream: InputStream = ByteArrayInputStream(decodedByte)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            null
        }
    }

}