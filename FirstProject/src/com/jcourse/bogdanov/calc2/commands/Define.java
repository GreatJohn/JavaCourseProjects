package com.jcourse.bogdanov.calc2.commands;

import java.util.*;


public class Define extends CommandExec {
    public void exec(String inCommand, LinkedList<Double> inStack, HashMap<String, Double> inMap) throws NumberFormatException{
        StringTokenizer st = Parser.getTokenizer(inCommand);
        int count = st.countTokens();
        if (count == 3 ){
            st.nextToken();
            String param = st.nextToken();
            Double number = Double.parseDouble(st.nextToken()) ;
            inMap.put(param, number);
        }
    }
}