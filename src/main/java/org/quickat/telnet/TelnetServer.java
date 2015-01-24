package org.quickat.telnet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Christophe Pollet
 */
@Component
public class TelnetServer extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(TelnetServer.class);

    @Autowired
    private ApplicationContext applicationContext;

    public TelnetServer() {
        start();
    }

    @Override
    public void run() {
        try (ServerSocket socket = new ServerSocket(1337, 10)) {
            logger.info("Starting telnet server on port 1337");

            while (true) {
                Socket clientSocket = socket.accept();

                ClientConnection clientConnection = applicationContext.getBean(ClientConnection.class);
                clientConnection.setSocket(clientSocket);
                clientConnection.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
