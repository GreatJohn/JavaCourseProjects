package com.jcourse.bogdanov.sort;

import java.io.*;
import java.util.*;

public class FileReceiver {
    private int totalCounter = 0;// counter all words in input
    public int getTotalCounter() {
        return totalCounter;
    }
    public Map<String,WordCounter> readInput(Reader r){
        Map<String,WordCounter> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        boolean nextWord = false;
        try {
            String sbString = "";
            int i;
            while ((i = r.read()) !=-1 ){// while not EOF
                char c = (char)i;
                if (Character.isLetterOrDigit(c)){
                    if (!nextWord) {
                        nextWord = true;//first symbol in word
                    }
                    sb.append(c);
                }
                else if (nextWord) { //if word ends
                    nextWord = false;
                    if ((sbString = sb.toString()).length()>0) {
                        sb.setLength(0);
                        WordCounter wc = map.get(sbString);
                        map.put(sbString, wc == null ? new WordCounter(sbString,1) : new WordCounter(sbString,wc.getCounter() + 1));
                        totalCounter++;
                    }
                }
            }
            if (sb.length()>0) {// add last word
                WordCounter wc = map.get(sbString);
                map.put(sbString, wc == null ? new WordCounter(sbString,1) : new WordCounter(sbString,wc.getCounter() + 1));
                totalCounter++;
            }
            return map;
        } catch (IOException e){
            throw new RuntimeException("Unable to execute method readInput()!" + e);
        }
    }
}
