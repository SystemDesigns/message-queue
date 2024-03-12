package org.example.handler;

import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.example.model.Message;
import org.example.model.Topic;
import org.example.model.TopicSubscriber;

@Getter
public class SubscriberWorker extends Thread {

    private final Topic topic;
    private final TopicSubscriber topicSubscriber;

    public SubscriberWorker(@NonNull final Topic topic, @NonNull final TopicSubscriber topicSubscriber) {
        this.topic = topic;
        this.topicSubscriber = topicSubscriber;
    }

    @SneakyThrows
    @Override
    public void run() {
        synchronized (topicSubscriber) {
            do {
                int currOffset = topicSubscriber.getOffset().get();
                while (currOffset >= topic.getMessages().size()) {
                    topicSubscriber.wait();
                }
                Message message = topic.getMessages().get(currOffset);
                topicSubscriber.getSubscriber().consume(message);

                // We cannot just increment here since subscriber can reset the offset while it is consuming the current
                // message. So, after consuming we need to increase only if it still has the previous value.
                topicSubscriber.getOffset().compareAndSet(currOffset, currOffset + 1);
            } while (true);
        }
    }

    synchronized public void wakeUpIfNeeded() {
        synchronized (topicSubscriber) {
            topicSubscriber.notify();
        }
    }
}
