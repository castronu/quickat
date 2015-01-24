package org.quickat.telnet.commands;

import java.io.PrintWriter;

/**
 * @author Christophe Pollet
 */
public interface Command {
    void execute(String[] args, PrintWriter out);

    String getHelpMessage();

    boolean respondsTo(String command);
}
