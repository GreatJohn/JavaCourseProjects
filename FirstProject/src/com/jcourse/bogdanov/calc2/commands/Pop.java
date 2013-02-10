package com.jcourse.bogdanov.calc2.commands;

import com.jcourse.bogdanov.calc2.*;
import java.util.*;

public class Pop extends CommandExec {
    public void exec(String inCommand, LinkedList<Double> inStack, HashMap<String, Double> inMap){
        if (inStack.size()>0){
            inStack.removeLast();
        }
    }
}