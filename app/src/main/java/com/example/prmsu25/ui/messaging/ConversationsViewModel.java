package com.example.prmsu25.ui.messaging;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.prmsu25.data.model.response.ConversationResponse;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.repository.ChatRepository;
import com.example.prmsu25.utils.UserSessionManager;

public class ConversationsViewModel extends AndroidViewModel {

    private final ChatRepository chatRepository;
    private final MutableLiveData<NetworkResult<ConversationResponse>> _conversationsResult = new MutableLiveData<>();
    public final LiveData<NetworkResult<ConversationResponse>> conversationsResult = _conversationsResult;

    public ConversationsViewModel(@NonNull Application application) {
        super(application);
        UserSessionManager sessionManager = new UserSessionManager(application.getApplicationContext());
        this.chatRepository = new ChatRepository(sessionManager);
        this.chatRepository.authenticateAndConnect();
    }

    public void loadConversations(String query) {
        _conversationsResult.setValue(NetworkResult.loading());
        chatRepository.getConversations(query, result -> _conversationsResult.postValue(result));
    }
}