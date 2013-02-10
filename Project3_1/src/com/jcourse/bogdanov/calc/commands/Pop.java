package com.jcourse.bogdanov.calc.commands;

import java.util.HashMap;
import java.util.LinkedList;

public class Pop extends CommandExec {
    public void exec(String inCommand, LinkedList<Double> inStack, HashMap<String, Double> inMap){
        if (inStack.size()>0){
            inStack.removeLast();
        }
    }
}