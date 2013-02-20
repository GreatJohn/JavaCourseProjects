package com.jcourse.bogdanov.testtasks;

import com.jcourse.bogdanov.sort.*;
import com.jcourse.bogdanov.sort.FileWriter;

import java.io.*;
import java.util.*;


public class Demo {
    public static void main(String[] args) {
        Map<String, WordCounter> map;
        try (Reader r = new InputStreamReader(new BufferedInputStream(new FileInputStream(args[0])));
             Writer w = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(args[1])))) {
            FileReceiver fr = new FileReceiver();
            map = fr.readInput(r);
            Collection<WordCounter> var = map.values();
            WordCounter[] setArray = var.toArray(new WordCounter[var.size()]);
            Arrays.sort(setArray, new SortByNumber());
            FileWriter fw = new FileWriter(fr.getTotalCounter());
            fw.writeOutput(w, setArray);
        } catch (FileNotFoundException e) {
            System.err.println("File not found !!! " + args[0] + " or " + args[1] + ", " + e);
        } catch (IOException e) {
            System.err.println("IOException in main !!! " + e);
        }
    }
}
