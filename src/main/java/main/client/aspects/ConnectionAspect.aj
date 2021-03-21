package main.client.aspects;
import com.diogonunes.jcolor.Attribute;
import main.client.Client;
import main.client.ClientCache;
import main.common.messaging.Message;
import main.common.messaging.MessageType;

import java.io.IOException;

import static com.diogonunes.jcolor.Ansi.colorize;

public aspect ConnectionAspect {
  final int MAX_SEND_MESSAGE_RETRIES = 3;

  pointcut startConnection(String ip, int port) :
    call(* Client.startConnection(String, int) throws IOException) && args(ip, port);

  pointcut stopConnection() :
    call(* Client.stopConnection() throws IOException);

  before(String ip, int port) : startConnection(ip, port) {
    writeInfoMessage("Trying to establish connection on server ip " + ip + ", port " + port);
  }

  void around(String ip, int port) : startConnection(ip, port) {
    int retry = 0;
    while (true) {
      try {
        proceed(ip, port);
        writeInfoMessage("Connection established on " + ip + ":" + port);
        ClientCache.getInstance().setServerConnected(true);
        return;
      } catch (Exception e) {
        if (++retry > MAX_SEND_MESSAGE_RETRIES) {
          writeErrorMessage("Failed to connect to server");
          return;
        }
        writeInfoMessage("Reconnect try nr. " + retry);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException interruptedException) {
          interruptedException.printStackTrace();
        }
      }
    }
  }

  before() : stopConnection() {
    writeInfoMessage("Closing connection...");
  }

  after() returning: stopConnection() {
    writeInfoMessage("Connection closed");
  }

  private void writeInfoMessage(String message) {
    System.out.println(colorize(message, Attribute.BRIGHT_GREEN_TEXT()));
  }

  private void writeErrorMessage(String message) {
    System.out.println(colorize(message, Attribute.BRIGHT_RED_TEXT()));
  }
}
