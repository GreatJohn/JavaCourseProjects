package com.jcourse.bogdanov.testtasks;

import com.jcourse.bogdanov.sort.*;
import com.jcourse.bogdanov.sort.FileWriter;

import java.io.*;
import java.util.*;


public class Demo {
    public static void main(String[] args) {
        Map<String,WordCounter> map;
        SortReader fr = new FileReceiver();
        SortWriter fw = new FileWriter();
        try (Reader r = new InputStreamReader(new BufferedInputStream(new FileInputStream(args[0])));
             Writer w = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(args[1])))){
            map = fr.readInput(r);
            Collection<WordCounter> var = map.values();
            WordCounter[] setArray = var.toArray(new WordCounter[var.size()]);
            Arrays.sort(setArray, new SortByNumber());
            fw.writeOutput(w,setArray);
        } catch (FileNotFoundException e) {
            System.err.println("File not found !!! " + args[0] + " or " + args[1] + ", " + e.getMessage());
        }
        catch (IOException e) {
            System.err.println("IOException in main !!! " + e.getMessage());
        }
    }
}
