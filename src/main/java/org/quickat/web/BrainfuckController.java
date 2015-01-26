package org.quickat.web;

import org.quickat.da.BrainfuckAnswer;
import org.quickat.service.BrainfuckEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by aposcia on 26.01.15.
 */
@RestController
@RequestMapping("brainfuck")
public class BrainfuckController {

    final static Logger logger = LoggerFactory.getLogger(BrainfuckController.class);

    private static final String BRAINFUCK_CODE = "-[--->+<]>----.---[-->+++<]>.------------.------.++++++++.----------.--[--->+<]>-.";

    @RequestMapping(method = RequestMethod.GET)
    public BrainfuckAnswer sayHello() {
        //would be better if reused... anyway
        BrainfuckEngine brainfuckEngine = new BrainfuckEngine(32, System.out);

        try {
            brainfuckEngine.interpret(BRAINFUCK_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String result = brainfuckEngine.getResult();
        return new BrainfuckAnswer(result);
    }


}
