package com.example.prmsu25.data.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.prmsu25.BuildConfig;
import com.example.prmsu25.data.model.ChatMessage;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketManager {
    private static final String TAG = "SocketManager";
    private static SocketManager instance;
    private Socket mSocket;
    private final Gson gson = new Gson();

    private final MutableLiveData<ChatMessage> onNewMessage = new MutableLiveData<>();

    private SocketManager() {
        try {
            mSocket = IO.socket(BuildConfig.SOCKET_URL);
        } catch (URISyntaxException e) {
            Log.e(TAG, "Error initializing socket", e);
        }
    }

    public static synchronized SocketManager getInstance() {
        if (instance == null) {
            instance = new SocketManager();
        }
        return instance;
    }

    public void connect() {
        if (mSocket != null && !mSocket.connected()) {
            mSocket.connect();
            setupEventListeners();
        }
    }

    public void disconnect() {
        if (mSocket != null) {
            mSocket.disconnect();
            mSocket.off();
        }
    }

    private void setupEventListeners() {
        mSocket.on(Socket.EVENT_CONNECT, args -> Log.d(TAG, "Socket Connected!: " + Arrays.toString(Arrays.stream(args).map(String::valueOf).toArray())));
        mSocket.on(Socket.EVENT_DISCONNECT, args -> Log.d(TAG, "Socket Disconnected!: " + Arrays.toString(Arrays.stream(args).map(String::valueOf).toArray())));
        mSocket.on(Socket.EVENT_CONNECT_ERROR, args -> Log.e(TAG, "Socket Connection Error: " + Arrays.toString(Arrays.stream(args).map(String::valueOf).toArray())));

        mSocket.on("receivedChatMessage", args -> {
            Log.d(TAG, "Received chat message: " + Arrays.toString(Arrays.stream(args).map(String::valueOf).toArray()));
            if (args.length > 0) {
                ChatMessage message = gson.fromJson(args[0].toString(), ChatMessage.class);
                Log.d(TAG, "Received chat message: " + message);
                onNewMessage.postValue(message);
            }
        });
    }

    public LiveData<ChatMessage> getOnNewMessage() {
        return onNewMessage;
    }

    public void sendMessage(String chatToken, String receiverId, String message) {
        if (mSocket != null && mSocket.connected()) {
            JSONObject data = new JSONObject();
            try {
                data.put("chatToken", chatToken);
                data.put("receiverId", receiverId);
                data.put("message", message);
                mSocket.emit("sendChatMessage", data);
            } catch (JSONException e) {
                Log.e(TAG, "Error creating JSON for sendMessage", e);
            }
        }
    }

    public void setActiveConversation(String chatToken, String receiverId) {
        if (mSocket != null && mSocket.connected()) {
            JSONObject data = new JSONObject();
            try {
                data.put("chatToken", chatToken);
                data.put("receiverId", receiverId);
                mSocket.emit("setActiveConversation", data);
            } catch (JSONException e) {
                Log.e(TAG, "Error creating JSON for setActiveConversation", e);
            }
        }
    }
}
