package com.example.prmsu25.data.network;

public class NetworkResult<T> {
    public enum Status { SUCCESS, ERROR, LOADING }

    public final Status status;
    public final T data;
    public final String message;

    private NetworkResult(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> NetworkResult<T> success(T data) {
        return new NetworkResult<>(Status.SUCCESS, data, null);
    }

    public static <T> NetworkResult<T> error(String msg) {
        return new NetworkResult<>(Status.ERROR, null, msg);
    }

    public static <T> NetworkResult<T> loading() {
        return new NetworkResult<>(Status.LOADING, null, null);
    }

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
