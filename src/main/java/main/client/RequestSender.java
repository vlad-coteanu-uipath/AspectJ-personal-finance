package main.client;

import main.common.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@Component
public class RequestSender {

    public Message sendMessage(ObjectOutputStream out, ObjectInputStream in, Message message) throws IOException, ClassNotFoundException {
        out.writeObject(message);
        return (Message) in.readObject();
    }

}
