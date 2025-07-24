package com.example.prmsu25.data.network.api;

import com.example.prmsu25.data.model.response.ConversationResponse;
import com.example.prmsu25.data.model.response.MessagesResponse;
import com.example.prmsu25.data.model.response.TokenResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChatApiService {
    @GET("chat/token")
    Call<TokenResponse> getChatToken();

    @GET("chat/conversations")
    Call<ConversationResponse> getConversations(
            @Query("senderId") String senderId,
            @Query("query") String query
    );

    @GET("chat/messages")
    Call<MessagesResponse> getMessages(
            @Query("senderId") String senderId,
            @Query("receiverId") String receiverId,
            @Query("page") int page,
            @Query("limit") int limit
    );
}
