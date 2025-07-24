package com.example.prmsu25.data.repository;

import androidx.lifecycle.LiveData;

import com.example.prmsu25.data.model.ChatMessage;
import com.example.prmsu25.data.model.response.ConversationResponse;
import com.example.prmsu25.data.model.response.MessagesResponse;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.data.network.SocketManager;
import com.example.prmsu25.data.network.api.ChatApiService;
import com.example.prmsu25.utils.UserSessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRepository {

    private final ChatApiService chatApiService;
    private final SocketManager socketManager;
    private final UserSessionManager sessionManager;

    public ChatRepository(UserSessionManager sessionManager) {
        this.chatApiService = RetrofitClient.getRetrofit().create(ChatApiService.class);
        this.socketManager = SocketManager.getInstance();
        this.sessionManager = sessionManager;
    }

    public void authenticateAndConnect() {
        chatApiService.getChatToken().enqueue(new Callback<com.example.prmsu25.data.model.response.TokenResponse>() {
            @Override
            public void onResponse(Call<com.example.prmsu25.data.model.response.TokenResponse> call, Response<com.example.prmsu25.data.model.response.TokenResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    sessionManager.saveChatToken(response.body().getData());
                    socketManager.connect();
                }
            }

            @Override
            public void onFailure(Call<com.example.prmsu25.data.model.response.TokenResponse> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void getConversations(String query, JobRepository.JobCallback<ConversationResponse> callback) {
        String senderId = sessionManager.getUserId();
        if (senderId == null) {
            callback.onResult(NetworkResult.error("User not logged in."));
            return;
        }
        chatApiService.getConversations(senderId, query).enqueue(new Callback<ConversationResponse>() {
            @Override
            public void onResponse(Call<ConversationResponse> call, Response<ConversationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(NetworkResult.success(response.body()));
                } else {
                    callback.onResult(NetworkResult.error("Failed to load conversations."));
                }
            }
            @Override
            public void onFailure(Call<ConversationResponse> call, Throwable t) {
                callback.onResult(NetworkResult.error(t.getMessage()));
            }
        });
    }

    public void getMessages(String receiverId, int page, int limit, JobRepository.JobCallback<MessagesResponse> callback) {
        String senderId = sessionManager.getUserId();
        if (senderId == null) {
            callback.onResult(NetworkResult.error("User not logged in."));
            return;
        }
        chatApiService.getMessages(senderId, receiverId, page, limit).enqueue(new Callback<MessagesResponse>() {
            @Override
            public void onResponse(Call<MessagesResponse> call, Response<MessagesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(NetworkResult.success(response.body()));
                } else {
                    callback.onResult(NetworkResult.error("Failed to load messages."));
                }
            }
            @Override
            public void onFailure(Call<MessagesResponse> call, Throwable t) {
                callback.onResult(NetworkResult.error(t.getMessage()));
            }
        });
    }

    public LiveData<ChatMessage> getNewMessageLiveData() {
        return socketManager.getOnNewMessage();
    }

    public void sendMessage(String receiverId, String message) {
        String chatToken = sessionManager.getChatToken();
        if (chatToken != null) {
            socketManager.sendMessage(chatToken, receiverId, message);
        }
    }

    public void setActiveConversation(String receiverId) {
        String chatToken = sessionManager.getChatToken();
        if (chatToken != null) {
            socketManager.setActiveConversation(chatToken, receiverId);
        }
    }
}
