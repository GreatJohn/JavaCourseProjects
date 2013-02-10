package com.jcourse.bogdanov.calc2;

import java.io.*;
import java.util.*;

public class Receiver implements InputCmd {

    private List<String> st;

    private BufferedReader br;

    public Receiver(){
        br = new BufferedReader(new InputStreamReader(System.in));
        st = this.inputRead();
    }

    public Receiver(String fileName) {
        try{
            br = new BufferedReader(new FileReader(fileName));
            st = this.inputRead();
            br.close();
        } catch (FileNotFoundException e){
            System.err.println("FileNotFoundException in Receiver(String fileName)" + e.getMessage());
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("IOExeption in Receiver(String fileName) - unable o close BufferedReader " + e.getMessage());
        }

    }

    public List<String> getStringList(){
        return this.st;
    }

    public InputCmd getInputCmd(){
        return this;
    }

    private List<String> inputRead() {
        String s,sTrimmed;
        List<String> commands = new ArrayList<>();


        try {
            while((s = br.readLine()) != null && s.length() != 0){
                sTrimmed = s.trim();
                if (sTrimmed.length()!= 0) {
                    commands.add(sTrimmed);
                }
            }
            return  commands;
        } catch (IOException e){
            System.err.println("IOExeption in Receiver.inputRead() - unable to execute readLine() " + e.getMessage());
            System.exit(-1);
        }
        return null;
    }
}
