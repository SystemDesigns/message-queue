package org.example.model;

import lombok.NonNull;

public class Message {

    private final String msg;

    public Message(@NonNull final String message) {
        this.msg = message;
    }

    public String getMsg() {
        return msg;
    }
}
