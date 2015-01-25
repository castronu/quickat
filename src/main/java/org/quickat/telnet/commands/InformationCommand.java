package org.quickat.telnet.commands;

import org.quickat.da.Quickie;
import org.quickat.da.User;
import org.quickat.repository.QuickiesRepository;
import org.quickat.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Christophe Pollet
 */
@Component
public class InformationCommand extends BaseCommand {
    @Autowired
    private QuickiesRepository quickiesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public void execute(String[] args, PrintWriter out) {
        if (args.length != 1) {
            out.append("Error: expected 1 argument, got 0. See help.\n").flush();
            return;
        }

        long quickieId;
        try {
            quickieId = Long.valueOf(args[0]);
        } catch (NumberFormatException e) {
            out.append("Error: invalid id [").append(args[0]).append("].\n").flush();
            return;
        }

        Quickie quickie = quickiesRepository.findOne(quickieId);

        if (quickie == null) {
            out.append("Error: quickie with id [").append(args[0]).append("] not found.\n").flush();
            return;
        }

        User speaker = usersRepository.findOne(quickie.getSpeakerId());

        ZonedDateTime instant = quickie.getQuickieDate().toInstant().atZone(ZoneId.of("UTC"));
        String quickieDate = LocalDateTime.from(instant).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", quickie.getId());
        parameters.put("title", quickie.getTitle());
        parameters.put("speaker", speaker.getFirstname() + " " + speaker.getLastname());
        parameters.put("date", quickieDate);
        parameters.put("description", quickie.getDescription());

        out.append(evaluateLocalTemplate("info.vm", parameters)).flush();
    }

    @Override
    public boolean respondsTo(String command) {
        return "info".equals(command) || "i".equals(command);
    }
}
