package com.jcourse.bogdanov.calc.commands;

import java.util.HashMap;
import java.util.LinkedList;

public class Print extends CommandExec {
    public void exec(String inCommand, LinkedList<Double> inStack, HashMap<String, Double> inMap){
        if (inStack.size()>0){
            System.out.println(inStack.getLast().toString());
        }
    }
}