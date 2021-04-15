package main.client.aspects;

import com.diogonunes.jcolor.Attribute;
import main.common.messaging.Message;
import main.client.Client;
import main.common.messaging.MessageType;

import java.io.IOException;

import static com.diogonunes.jcolor.Ansi.colorize;

public aspect MessageAspect {

  final int MAX_SEND_MESSAGE_RETRIES = 3;
  pointcut sendMessage(Message request) :
    call(* Client.sendMessage(Message) throws IOException, ClassNotFoundException) && args(request);

  before(Message request) : sendMessage(request) {
    writeInfoMessage("Message  " + request + " was sent.");
  }

  Object around(Message request) : sendMessage(request) {
    int retry = 0;
    while (true) {
      try {
        Object response = proceed(request);
        if(response == null) {
          throw new RuntimeException("Server response is null");
        } else {
          return response;
        }
      } catch (Exception e) {
        if (++retry > MAX_SEND_MESSAGE_RETRIES) {
          return new Message(null, MessageType.ERROR);
        }
        writeInfoMessage("Send message failed, retry nr. " + retry);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException interruptedException) {
          interruptedException.printStackTrace();
        }
      }
    }
  }

  after(Message request) returning(Message response): sendMessage(request) {
    if (request.messageType.equals(MessageType.ERROR)) {
      writeErrorMessage("Error returned from server");
    } else {
      writeInfoMessage("Message  " + response + " was received.");
    }
  }

  private void writeInfoMessage(String message) {
    System.out.println(colorize(message, Attribute.BRIGHT_CYAN_TEXT()));
  }

  private void writeErrorMessage(String message) {
    System.out.println(colorize(message, Attribute.RED_TEXT()));
  }
}
