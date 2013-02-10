package com.jcourse.bogdanov.calc2.commands;

import com.jcourse.bogdanov.calc2.*;
import java.util.*;

public class Sqrt extends CommandExec {
    public void exec(String inCommand, LinkedList<Double> inStack, HashMap<String, Double> inMap){
        Double d1;
        if ((inStack.size() >0) && (inStack.getLast() >= 0.0D)){
            d1 = inStack.removeLast();
            inStack.addLast(Math.sqrt(d1));
        }
    }
}