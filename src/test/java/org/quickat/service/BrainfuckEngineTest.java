package org.quickat.service;

import org.junit.Test;

import java.io.File;

public class BrainfuckEngineTest {

    @Test
    public void testInterpret() throws Exception {

        getClass().getClassLoader().getResource("hello.bf").getContent();

        BrainfuckEngine brainfuckEngine = new BrainfuckEngine(32, System.out);

        brainfuckEngine.interpret(new File(getClass().getClassLoader().getResource("hello.bf").getFile()));

    }
}