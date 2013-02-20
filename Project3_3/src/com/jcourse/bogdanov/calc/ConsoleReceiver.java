package com.jcourse.bogdanov.calc;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReceiver implements InputCmd {
    private BufferedReader br;

    public ConsoleReceiver() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public InputCmd getInputCmd() {
        return this;
    }

    public String getNextString() {
        String s, sTrimmed;
        try {
            s = br.readLine();
            sTrimmed = s.trim();
            return sTrimmed;
        } catch (IOException e) {
            throw new RuntimeException("Unable to execute method readLine()! (" + e.getMessage() + ")");
        }
    }
}
