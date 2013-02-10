package com.jcourse.bogdanov.task1;

import java.io.*;

public class Echo {

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while((s = br.readLine()) != null && s.length() != 0)
            System.out.println(s);
    }
}
