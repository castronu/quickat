package org.quickat.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by aposcia on 14.01.15.
 */
@RestController
public class UserController {


    @RequestMapping("user")
    public String getUsers() {
        return "Hello world";
    }

}
