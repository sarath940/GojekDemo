package com.example.gojekdemo.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RepoResource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;


    public RepoResource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> RepoResource<T> success (@Nullable T data) {
        return new RepoResource<>(Status.SUCCESS, data, null);
    }

    public static <T> RepoResource<T> error(@NonNull String msg, @Nullable T data) {
        return new RepoResource<>(Status.ERROR, data, msg);
    }

    public static <T> RepoResource<T> loading(@Nullable T data) {
        return new RepoResource<>(Status.LOADING, data, null);
    }

    public enum Status { SUCCESS, ERROR, LOADING}
}