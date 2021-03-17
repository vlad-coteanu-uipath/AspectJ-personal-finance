package main.common.messaging;

import java.io.Serializable;

public class Message implements Serializable {
    public Object payload;
    public MessageType messageType;
    public int userId;

    public Message(MessageType messageType, int userId) {
        this.messageType = messageType;
        this.userId = userId;
    }

    public Message(Object payload, MessageType messageType) {
        this.payload = payload;
        this.messageType = messageType;
    }

    public Message(Object payload, MessageType messageType, int userId) {
        this.payload = payload;
        this.messageType = messageType;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "payload=" + payload +
                ", messageType=" + messageType +
                ", userId=" + userId +
                '}';
    }
}
