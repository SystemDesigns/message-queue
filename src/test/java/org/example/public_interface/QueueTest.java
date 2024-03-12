package org.example.public_interface;

import org.example.model.Topic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueueTest {

    @Test
    public void QueueTestTopicName() {
        Queue queue = new Queue();
        String topicName = "topic1";
        Topic topic = queue.createTopic(topicName);
        assertEquals(topicName, topic.getTopicName());
    }
}