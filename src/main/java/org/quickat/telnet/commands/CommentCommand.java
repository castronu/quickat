package org.quickat.telnet.commands;

import org.quickat.da.Comment;
import org.quickat.da.Quickie;
import org.quickat.da.User;
import org.quickat.repository.CommentsRepository;
import org.quickat.repository.QuickiesRepository;
import org.quickat.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Christophe Pollet
 */
@Component
public class CommentCommand extends BaseCommand {
    @Autowired
    private QuickiesRepository quickiesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CommentsRepository commentsRepository;

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


        Iterable<Comment> comments = commentsRepository.findByQuickieId(quickieId);

        for (Comment comment : comments) {
            User user = usersRepository.findOne(comment.getUserId());
            Map<String, Object> parameters = new HashMap<>();

            ZonedDateTime instant = comment.getDate().toInstant().atZone(ZoneId.of("UTC"));
            String commentDate = LocalDateTime.from(instant).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            parameters.put("id", quickie.getId());
            parameters.put("description", comment.getComment());
            parameters.put("user", user.getFirstname() + " " + user.getLastname());
            parameters.put("date", commentDate);

            out.append(evaluateLocalTemplate("comment.vm", parameters));
        }


        /*Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", quickie.getId());
        parameters.put("title", quickie.getTitle());
        parameters.put("speaker", speaker.getFirstname() + " " + speaker.getLastname());
        parameters.put("date", quickieDate);
        parameters.put("description", quickie.getDescription());

        out.append(evaluateLocalTemplate("info.vm", parameters)).flush();*/
    }

    @Override
    public boolean respondsTo(String command) {
        return "comment".equals(command) || "c".equals(command);
    }
}
