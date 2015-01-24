package org.quickat.telnet.commands;

import org.quickat.da.Quickie;
import org.quickat.da.User;
import org.quickat.repository.QuickiesRepository;
import org.quickat.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Christophe Pollet
 */
@Component
public class ListCommand extends BaseCommand {
    @Autowired
    private QuickiesRepository quickiesRepository;

    @Autowired
    private UsersRepository usersRepository;

    private Map<String, Supplier<Iterable<Quickie>>> methods;

    public ListCommand() {
        methods = new HashMap<>();
        methods.put("future", () -> quickiesRepository.findByQuickieDateAfter(new Date()));
        methods.put("past", () -> quickiesRepository.findByQuickieDateBefore(new Date()));
        methods.put("top3future", () -> quickiesRepository.getFutureOrderedByVoteCount(3));
        methods.put("top3past", () -> quickiesRepository.getPastOrderedByLikeCount(3));
    }

    @Override
    public void execute(String[] args, PrintWriter out) {
        if (args.length != 1) {
            out.append("Error: expected 1 argument, got 0. See help.\n").flush();
            return;
        }

        if (!methods.containsKey(args[0])) {
            out.append("Error: illegal argument [").append(args[0]).append("]. See help.\n").flush();
            return;
        }

        for (Quickie quickie : methods.get(args[0]).get()) {
            User speaker = usersRepository.findOne(quickie.getSpeakerId());
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("id", quickie.getId());
            parameters.put("name", quickie.getTitle());
            parameters.put("speaker", speaker.getFirstname() + " " + speaker.getLastname());
            out.append(evaluateLocalTemplate("quickie.vm", parameters));
        }
        out.flush();
    }

    @Override
    public boolean respondsTo(String command) {
        return "list".equals(command);
    }
}
