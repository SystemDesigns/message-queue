package org.example.public_interface;

import lombok.NonNull;
import org.example.handler.TopicHandler;
import org.example.model.Message;
import org.example.model.Topic;
import org.example.model.TopicSubscriber;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Queue {

    /**
     * Mapping from topic id to a topic handler object.
     */
    private final Map<String, TopicHandler> topicHandlerMap;

    public Queue() {
        this.topicHandlerMap = new HashMap<>();
    }

    public Topic createTopic(@NonNull final String topicName) {
        Topic topic = new Topic(topicName, UUID.randomUUID().toString());
        TopicHandler topicHandler = new TopicHandler(topic);
        topicHandlerMap.put(topic.getTopicID(), topicHandler);
        System.out.println("Created topic: " + topic.getTopicName());
        return topic;
    }

    public void subscribeTopic(@NonNull final ISubscriber subscriber, @NonNull final Topic topic) {
        topic.addSubscriber(new TopicSubscriber(subscriber));
        System.out.println(subscriber.getID() + " subscribed to topic: " + topic.getTopicName());
    }

    public void publishMessage(@NonNull final Message message, @NonNull final Topic topic) {
        topic.addMessage(message);
        System.out.println(message.getMsg() + " published to topic: " + topic.getTopicName());
        new Thread(() -> topicHandlerMap.get(topic.getTopicID()).publish()).start();
    }

    public void resetOffset(@NonNull final Topic topic, @NonNull final ISubscriber subscriber, @NonNull final Integer newOffset) {
        for (TopicSubscriber topicSubscriber : topic.getTopicSubscribers()) {
            if (topicSubscriber.getSubscriber().equals(subscriber)) {
                topicSubscriber.getOffset().set(newOffset);
                System.out.println(topicSubscriber.getSubscriber().getID() + " offset reset to " + newOffset);
                new Thread(() -> topicHandlerMap.get(topic.getTopicID()).startSubscriberWorker(topicSubscriber)).start();
            }
        }
    }

}
