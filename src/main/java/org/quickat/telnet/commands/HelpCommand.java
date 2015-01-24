package org.quickat.telnet.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.util.List;

/**
 * @author Christophe Pollet
 */
@Component
public class HelpCommand extends BaseCommand {
    @Autowired
    private List<Command> commands;

    @Override
    public boolean respondsTo(String command) {
        return "help".equals(command);
    }

    @Override
    public void execute(String[] args, PrintWriter out) {
        out.append(evaluateLocalTemplate("header.vm"));
        for (Command command : commands) {
            out.append(command.getHelpMessage());
        }
        out.flush();
    }
}
