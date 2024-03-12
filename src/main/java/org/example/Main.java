package org.example;

import org.example.model.Message;
import org.example.model.Topic;
import org.example.public_interface.Queue;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        final Queue queue = new Queue();
        final Topic topic1 = queue.createTopic("t1");
        final Topic topic2 = queue.createTopic("t2");

        final SleepingSubscriber sub1 = new SleepingSubscriber("sub1", 10000);
        final SleepingSubscriber sub2 = new SleepingSubscriber("sub2", 10000);
        queue.subscribeTopic(sub1, topic1);
        queue.subscribeTopic(sub2, topic1);

        final SleepingSubscriber sub3 = new SleepingSubscriber("sub3", 5000);
        queue.subscribeTopic(sub3, topic2);

        queue.publishMessage(new Message("m1"), topic1); // sub1, sub2
        queue.publishMessage(new Message("m2"), topic1); // sub1, sub2

        queue.publishMessage(new Message("m3"), topic2); // sub3

        Thread.sleep(15000);
        queue.publishMessage(new Message("m4"), topic2); // sub3
        queue.publishMessage(new Message("m5"), topic1); // sub1, sub2

        // reset offset of sub1 to 0 for topic1
        queue.resetOffset(topic1, sub1, 0);

    }
}