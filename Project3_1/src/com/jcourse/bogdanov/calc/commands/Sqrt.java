package com.jcourse.bogdanov.calc.commands;

import java.util.HashMap;
import java.util.LinkedList;

public class Sqrt extends CommandExec {
    public void exec(String inCommand, LinkedList<Double> inStack, HashMap<String, Double> inMap){
        Double d1;
        if ((inStack.size() >0) && (inStack.getLast() >= 0.0D)){
            d1 = inStack.removeLast();
            inStack.addLast(Math.sqrt(d1));
        }
    }
}