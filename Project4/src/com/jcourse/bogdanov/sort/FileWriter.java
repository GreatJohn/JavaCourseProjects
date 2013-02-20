package com.jcourse.bogdanov.sort;


import java.io.IOException;
import java.io.Writer;

public class FileWriter {
    private int totalCounter;

    public FileWriter(int totalCounter) {
        this.totalCounter = totalCounter;
    }

    public void writeOutput(Writer out, WordCounter[] outArray) {
        for (WordCounter dt : outArray) {
            try {
                out.write(dt.getWord() + "," + dt.getCounter() + "," + (100 / totalCounter * dt.getCounter()) + "\n");
            } catch (IOException e) {
                throw new RuntimeException("Unable to execute method writeOutput()!" + e);
            }
        }
    }
}
