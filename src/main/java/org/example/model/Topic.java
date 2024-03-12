package org.example.model;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Topic {

    private final String topicName;
    private final String topicID;
    private final List<Message> messages;
    private final List<TopicSubscriber> topicSubscribers;

    public Topic(@NonNull final String topicName, @NonNull final String topicID) {
        this.topicName = topicName;
        this.topicID = topicID;
        this.topicSubscribers = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public void addSubscriber(@NonNull final TopicSubscriber subscriber) {
        this.topicSubscribers.add(subscriber);
    }

    public synchronized void addMessage(@NonNull final Message message) {
        this.messages.add(message);
    }
}
