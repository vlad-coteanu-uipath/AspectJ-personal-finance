package main.server.springaopaspects;

import com.diogonunes.jcolor.Attribute;
import main.common.messaging.Message;
import main.server.RequestHandler;
import main.server.ServerClientHandler;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import static com.diogonunes.jcolor.Ansi.colorize;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* main.server.Server.start(..)) && args(port)")
    private void serverStartingPointcut(int port) {}

    @Before(value = "serverStartingPointcut(port)", argNames = "port")
    public void serverStartingTrace(int port) {
        writeInfoMessage("Server is starting on port: " + port);
    }

    @Pointcut("execution(* main.server.ServerClientHandler.start(..)) && target(clientHandler)")
    public void clientConnectingPointcut(ServerClientHandler clientHandler) {}

    @Before(value = "clientConnectingPointcut(clientHandler)", argNames = "clientHandler")
    public void clientConnectedTrace(ServerClientHandler clientHandler) {
        writeInfoMessage("Client " + clientHandler + " connected.");
    }

//    NEED AN ALTERNATIVE TO THIS
//    @After(value = "clientConnectingPointcut(clientHandler)", argNames = "clientHandler")
//    public void clientDisconnectedTrace(ServerClientHandler clientHandler) {
//        writeInfoMessage("Client " + clientHandler + " disconnected.");
//    }

    @Pointcut(value = "execution(* main.server.RequestHandler.handleRequest(..)) && args(clientHandler, request)", argNames = "clientHandler, request")
    public void receivedRequestFromClientPointcut(ServerClientHandler clientHandler, Message request) {}

    @Before(value = "receivedRequestFromClientPointcut(clientHandler, request)", argNames = "clientHandler, request")
    public void receivedRequestFromClientTrace(ServerClientHandler clientHandler, Message request) {
        writeInfoMessage("Received request from client " + (clientHandler != null ? clientHandler : " null ") + ": " + (request != null ? request : " null "));
    }

    @AfterReturning(pointcut ="receivedRequestFromClientPointcut(clientHandler, request)", returning = "response", argNames = "clientHandler,request,response")
    public void sendingResponseToClientTrace(ServerClientHandler clientHandler, Message request, Message response) {
        writeInfoMessage("Sending response to client " + (clientHandler != null ? clientHandler : " null ") + ": " + (response != null ? response : " null "));
    }

    private void writeInfoMessage(String message) {
        System.out.println(colorize(message, Attribute.GREEN_TEXT()));
    }

}
