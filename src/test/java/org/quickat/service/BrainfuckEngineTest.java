package org.quickat.service;

import org.junit.Test;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.*;

public class BrainfuckEngineTest {

    @Test
    public void testInterpret() throws Exception {

       getClass().getClassLoader().getResource("hello.bf").getContent();

        BrainfuckEngine brainfuckEngine = new BrainfuckEngine(32, System.out);

        brainfuckEngine.interpret(new File(getClass().getClassLoader().getResource("hello.bf").getFile()));

    }
}