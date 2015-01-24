package org.quickat.telnet;

import org.apache.velocity.app.VelocityEngine;
import org.quickat.telnet.commands.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Christophe Pollet
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Lazy
public class ClientConnection extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(ClientConnection.class);

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private List<Command> commands;

    private Socket socket;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        logger.info("New client connected from " + socket.getRemoteSocketAddress().toString());

        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream())) {

            welcomeMessage(out);
            commandsLoop(in, out);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeSocket();
        }
    }

    private void welcomeMessage(PrintWriter out) {
        String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "telnet/welcome.vm", "UTF-8", Collections.<String, Object>emptyMap());
        out.append(content).flush();
    }

    private void commandsLoop(BufferedReader in, PrintWriter out) throws IOException {
        while (true) {
            out.append("> ").flush();
            String line = in.readLine();

            logger.info(socket.getRemoteSocketAddress().toString() + " -- " + line);

            if ("quit".equals(line)) {
                out.append("Bye.\n").flush();
                return;
            }

            String[] parts = line.split(" ");
            String[] args = Arrays.copyOfRange(parts, 1, parts.length);

            executeCommand(parts[0], args, out);
        }
    }

    private void executeCommand(String cmd, String[] args, PrintWriter out) {
        for (Command command : commands) {
            if (command.respondsTo(cmd)) {
                command.execute(args, out);
                return;
            }
        }

        out.append("Command [").append(cmd).append("] not implemented\n").flush();
    }

    private void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
