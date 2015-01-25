package org.quickat.telnet.commands;

import org.quickat.da.Quickie;
import org.quickat.da.User;
import org.quickat.repository.QuickiesRepository;
import org.quickat.repository.UsersRepository;
import org.quickat.service.QuickiesSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Christophe Pollet
 */
@Component
public class ListCommand extends BaseCommand {
    private static final Logger logger = LoggerFactory.getLogger(ListCommand.class);

    @Autowired
    private QuickiesRepository quickiesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private QuickiesSupplier quickiesSupplier;

    @Override
    public void execute(String[] args, PrintWriter out) {
        if (args.length != 1) {
            out.append("Error: expected 1 argument, got 0. See help.\n").flush();
            return;
        }

        try {
            for (Quickie quickie : quickiesSupplier.getQuickies(args[0])) {
                User speaker = usersRepository.findOne(quickie.getSpeakerId());
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("id", quickie.getId());
                parameters.put("name", quickie.getTitle());
                parameters.put("speaker", speaker.getFirstname() + " " + speaker.getLastname());
                out.append(evaluateLocalTemplate("quickie.vm", parameters));
            }
            out.flush();
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
            out.append("Error: illegal argument [").append(args[0]).append("]. See help.\n").flush();
        }
    }

    @Override
    public boolean respondsTo(String command) {
        return "list".equals(command) || "l".equals(command);
    }
}
