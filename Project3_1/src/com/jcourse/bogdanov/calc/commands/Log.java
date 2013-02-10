package com.jcourse.bogdanov.calc.commands;

import java.util.HashMap;
import java.util.LinkedList;

public class Log extends CommandExec {
    public void exec(String inCommand, LinkedList<Double> inStack, HashMap<String, Double> inMap){
        Double d1;
        if ((inStack.size() >0)){
            d1 = inStack.removeLast();
            inStack.addLast(Math.log(d1));
        }
    }
}