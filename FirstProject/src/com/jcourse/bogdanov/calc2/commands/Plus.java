package com.jcourse.bogdanov.calc2.commands;

import com.jcourse.bogdanov.calc2.*;
import java.util.*;

public class Plus extends CommandExec {
    public void exec(String inCommand, LinkedList<Double> inStack, HashMap<String, Double> inMap){
        Double d1,d2;
        if (inStack.size() >1){
            d1 = inStack.removeLast();
            d2 = inStack.removeLast();
            inStack.addLast(d2+d1);
        }
    }
}