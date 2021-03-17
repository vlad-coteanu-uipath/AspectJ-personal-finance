package main.server.aspects;

import com.diogonunes.jcolor.Attribute;
import main.server.Server;
import java.net.ServerSocket;
import main.common.messaging.Message;

import static com.diogonunes.jcolor.Ansi.colorize;

public aspect LoggingAspect {

    pointcut serverStartingTrace(int port) :
            call(ServerSocket.new(..)) && args(port) && within(main.server.*);

    pointcut clientConnectionTrace(Server.ServerClientHandler target) :
            execution(* Server.ServerClientHandler.run(..)) && within(main.server.*) && target(target);

    pointcut receivedRequestFromClientTrace(Server.ServerClientHandler target, Message request) :
            execution(* Server.ServerClientHandler.handleRequest(Message)) && within(main.server.*) && target(target) && args(request);

    before(int port) : serverStartingTrace(port) {
        writeInfoMessage("Server starting on port: " + port);
    }

    after(int port) returning : serverStartingTrace(port) {
        writeInfoMessage("Server successfully started on port: " + port);
    }

    after(int port) throwing : serverStartingTrace(port) {
        writeErrorMessage("Server could not start on port: " + port);
    }

    before(Server.ServerClientHandler target) : clientConnectionTrace(target) {
        writeInfoMessage("Client " + target + " connected.");
    }

    after(Server.ServerClientHandler target) : clientConnectionTrace(target) {
        writeInfoMessage("Client " + target + " disconnected.");
    }

    before(Server.ServerClientHandler target, Message message) : receivedRequestFromClientTrace(target, message) {
        writeInfoMessage("Received request from client " + (target != null ? target : " null ") + ": " + (message != null ? message : " null "));
    }

    after(Server.ServerClientHandler target, Message request) returning (Message response) : receivedRequestFromClientTrace(target, request) {
        writeInfoMessage("Sending response to client " + (target != null ? target : " null ") + ": " + (response != null ? response : " null "));
    }

    private void writeInfoMessage(String message) {
        System.out.println(colorize(message, Attribute.GREEN_TEXT()));
    }

    private void writeErrorMessage(String message) {
        System.out.println(colorize(message, Attribute.RED_TEXT()));
    }

}
