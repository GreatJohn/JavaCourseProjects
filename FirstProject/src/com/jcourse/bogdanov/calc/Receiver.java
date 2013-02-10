package com.jcourse.bogdanov.calc;

import java.io.*;
import java.util.*;

public class Receiver implements InputCmd {

    private StringTokenizer st;

    private BufferedReader br;

    public Receiver() throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        st = this.inputRead();
        br.close();
    }

    public Receiver(String fileName) throws IOException {
        try {
            br = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e){
            System.err.println("FileNotFoundException in Receiver(String fileName)" + e.getMessage());
            System.exit(-1);
        }
        st = this.inputRead();
        br.close();
    }

    public StringTokenizer getStringTokenizer(){
        return this.st;
    }

    public InputCmd getInputCmd(){
        return this;
    }

    private StringTokenizer inputRead() throws IOException,NullPointerException {
        String s,sTrimmed;
        StringBuilder sb = new StringBuilder();


        try {
            while((s = br.readLine()) != null && s.length() != 0){
                try{
                    sTrimmed = s.trim();
                }catch (NullPointerException e){
                    sTrimmed = "";
                }
                if (sTrimmed.length()!= 0) {
                    sb.append(sTrimmed).append("\n");}
            }
            if (sb.toString().length()>0) {return new StringTokenizer(sb.toString(), "\n");}
        } catch (IOException e){
            System.err.println("IOExeption in Receiver.inputRead() " + e.getMessage());
            br.close();
            System.exit(-1);
        }
        return null;
    }



}
