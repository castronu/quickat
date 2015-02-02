package org.quickat.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Christophe Pollet
 */
@Controller
public class ForwardController {
    @RequestMapping({
            "/quickies/{id}",
            "/quickies/{id}/edit",
            "/about",
            "/help",
            "/profile/{id}"
    })
    public String redirectQuickie() {
        return "forward:/index.html";
    }
}
