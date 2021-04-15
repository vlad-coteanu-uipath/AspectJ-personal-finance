package main.client.springaopaspects;

import com.diogonunes.jcolor.Attribute;
import main.client.ClientCache;
import main.common.entities.Category;
import main.common.entities.Expense;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.swing.*;

import static com.diogonunes.jcolor.Ansi.colorize;

@Aspect
@Component
public class ConnectionAspect {

    private final int MAX_CONNECTION_RETRIES = 3;

    @Pointcut("execution(* *..*.Client.startConnection(String, int) throws *..*.IOException) && args(ip, port)")
    public void startConnectionPointcut(String ip, int port) {}


    @Before(value = "startConnectionPointcut(ip, port)", argNames = "ip,port")
    public void startConnectionTrace(String ip, int port) {
        writeInfoMessage("Trying to establish connection on server ip " + ip + ", port " + port);
    }

    @Around(value = "startConnectionPointcut(ip, port)", argNames = "pjp,ip,port")
    public void startConnectionRetryMechanism(ProceedingJoinPoint pjp, String ip, int port) {
        int retry = 0;
        while (true) {
            try {
                pjp.proceed(new Object[] { ip, port });
                writeInfoMessage("Connection established on " + ip + ":" + port);
                ClientCache.getInstance().setServerConnected(true);
                return;
            } catch (Exception e) {
                if (++retry > MAX_CONNECTION_RETRIES) {
                    writeErrorMessage("Failed to connect to server");
                    return;
                }
                writeInfoMessage("Reconnect try nr. " + retry);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    @Pointcut("execution(* *..*.Client.stopConnection() throws *..*.IOException)")
    public void stopConnectionPointcut() {}

    @Before("stopConnectionPointcut()")
    public void closingConnectionTrace() {
        writeInfoMessage("Closing connection...");
    }

    @AfterReturning("stopConnectionPointcut()")
    public void connectionClosedTrace() {
        writeInfoMessage("Connection closed");
    }

    private void writeInfoMessage(String message) {
        System.out.println(colorize(message, Attribute.BLUE_TEXT()));
    }

    private void writeErrorMessage(String message) {
        System.out.println(colorize(message, Attribute.BLACK_TEXT()));
    }

}
