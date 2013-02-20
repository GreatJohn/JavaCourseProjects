package com.jcourse.bogdanov.calc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReceiver implements InputCmd {

    private BufferedReader br;

    public FileReceiver(String fileName) throws FileNotFoundException {
        try {
            br = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Unable to open file(" + fileName + ")! (" + e.getMessage() + ")");
        }
    }

    public InputCmd getInputCmd() {
        return this;
    }

    public String getNextString() {
        String s, sTrimmed;
        try {
            s = br.readLine();
            if (s == null) {
                return null;
            }
            sTrimmed = s.trim();
            return sTrimmed;
        } catch (IOException e) {
            throw new RuntimeException("Unable to execute method readLine()! (" + e.getMessage() + ")");
        }
    }
}
