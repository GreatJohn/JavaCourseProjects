package com.jcourse.bogdanov.calc.commands;

import java.util.HashMap;
import java.util.LinkedList;

public class Mult extends CommandExec {
    public void exec(String inCommand, LinkedList<Double> inStack, HashMap<String, Double> inMap){
        Double d1,d2;
        if (inStack.size() >1){
            d1 = inStack.removeLast();
            d2 = inStack.removeLast();
            inStack.addLast(d2*d1);
        }
    }
}