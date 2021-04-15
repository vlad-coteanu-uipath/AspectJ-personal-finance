package main.client.springaopaspects;

import com.diogonunes.jcolor.Attribute;
import main.client.ClientCache;
import main.common.messaging.Message;
import main.common.messaging.MessageType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static com.diogonunes.jcolor.Ansi.colorize;

@Aspect
@Component
public class MessageAspect {

    private final int MAX_SEND_MESSAGE_RETRIES = 3;

    @Pointcut("execution(* *..*.RequestSender.sendMessage(..))" +
            " && args(oos, ois, request)")
    public void sendMessagePointcut(ObjectOutputStream oos, ObjectInputStream ois, Message request) {}


    @Before(value = "sendMessagePointcut(oos, ois, request)")
    public void beforeSendMessage(ObjectOutputStream oos, ObjectInputStream ois, Message request) {
        writeInfoMessage("Message  " + request + " was sent.");
    }

    @Around(value = "sendMessagePointcut(oos, ois, request)", argNames = "pjp,oos,ois,request")
    public Object sendMessage(ProceedingJoinPoint pjp, ObjectOutputStream oos, ObjectInputStream ois, Message request) throws Throwable {
        int retry = 0;
        while (true) {
            try {
                Object response = pjp.proceed(new Object[] {oos, ois, request});
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

    @AfterReturning(value = "sendMessagePointcut(oos, ois, request)", returning = "response", argNames = "oos,ois,request,response")
    public void afterSendMessage(ObjectOutputStream oos, ObjectInputStream ois, Message request, Message response) {
        if (request.messageType.equals(MessageType.ERROR)) {
            writeErrorMessage("Error returned from server");
        } else {
            writeInfoMessage("Message  " + response + " was received.");
        }
    }

    private void writeInfoMessage(String message) {
        System.out.println(colorize(message, Attribute.BLUE_TEXT()));
    }

    private void writeErrorMessage(String message) {
        System.out.println(colorize(message, Attribute.BLACK_TEXT()));
    }

}
