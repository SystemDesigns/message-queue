package org.example.public_interface;

import lombok.NonNull;
import org.example.model.Message;

public interface ISubscriber {

    String getID();
    void consume(@NonNull final Message message) throws InterruptedException;
}
