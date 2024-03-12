package org.example;

import lombok.NonNull;
import org.example.model.Message;
import org.example.public_interface.ISubscriber;

public class SleepingSubscriber implements ISubscriber {

    public final String id;
    public final int sleepTimeInMillis;

    public SleepingSubscriber(@NonNull final String id, int sleepTimeInMillis) {
        this.id = id;
        this.sleepTimeInMillis = sleepTimeInMillis;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void consume(@NonNull Message message) throws InterruptedException {
        System.out.println("Subscriber: " + id + " started consuming: " + message.getMsg());
        Thread.sleep(sleepTimeInMillis);
        System.out.println("Subscriber: " + id + " finished consuming: " + message.getMsg());
    }
}
