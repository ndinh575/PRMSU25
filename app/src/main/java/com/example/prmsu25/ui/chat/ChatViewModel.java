package com.example.prmsu25.ui.chat;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.prmsu25.data.model.ChatMessage;
import com.example.prmsu25.data.model.response.MessagesResponse;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.repository.ChatRepository;
import com.example.prmsu25.utils.UserSessionManager;

public class ChatViewModel extends AndroidViewModel {

    private final ChatRepository chatRepository;
    private final MutableLiveData<NetworkResult<MessagesResponse>> _messagesResult = new MutableLiveData<>();
    public final LiveData<NetworkResult<MessagesResponse>> messagesResult = _messagesResult;
    public final LiveData<ChatMessage> newMessage;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        UserSessionManager sessionManager = new UserSessionManager(application.getApplicationContext());
        this.chatRepository = new ChatRepository(sessionManager);
        this.newMessage = chatRepository.getNewMessageLiveData();
    }

    public void loadMessages(String receiverId, int page) {
        _messagesResult.setValue(NetworkResult.loading());
        chatRepository.getMessages(receiverId, page, 20, result -> _messagesResult.postValue(result));
    }

    public void sendMessage(String receiverId, String message) {
        chatRepository.sendMessage(receiverId, message);
    }

    public void setActiveConversation(String receiverId) {
        chatRepository.setActiveConversation(receiverId);
    }
}