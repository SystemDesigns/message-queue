package org.example.handler;

import lombok.Getter;
import lombok.NonNull;
import org.example.model.Topic;
import org.example.model.TopicSubscriber;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TopicHandler {

    private final Topic topic;

    /**
     * This map contains the mapping from subscriber id to subscriber worker object
     */
    private final Map<String, SubscriberWorker> subscriberWorkerMap;

    public TopicHandler(@NonNull final Topic topic) {
        this.topic = topic;
        this.subscriberWorkerMap = new HashMap<>();
    }

    public void publish() {
        // publish the message to all subscribers
        for (TopicSubscriber topicSubscriber: topic.getTopicSubscribers()) {
            startSubscriberWorker(topicSubscriber);
        }
    }

    public void startSubscriberWorker(@NonNull final TopicSubscriber topicSubscriber) {
        final String subscriberID = topicSubscriber.getSubscriber().getID();
        if (!subscriberWorkerMap.containsKey(subscriberID)) {
            final SubscriberWorker subscriberWorker = new SubscriberWorker(this.topic, topicSubscriber);
            this.subscriberWorkerMap.put(subscriberID, subscriberWorker);
            new Thread(subscriberWorker).start();
        }

        final SubscriberWorker subscriberWorker = subscriberWorkerMap.get(subscriberID);
        subscriberWorker.wakeUpIfNeeded();
    }
}
