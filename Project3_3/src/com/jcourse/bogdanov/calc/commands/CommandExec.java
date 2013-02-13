package com.jcourse.bogdanov.calc.commands;

import com.jcourse.bogdanov.calc.Cmd;

import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

public class CommandExec implements Cmd {
    @In(arg = "stack")
    LinkedList<Double> stack;
    @In(arg = "map")
    Map<String,Double> map;
    public void exec(String inCommand) throws CalcCommandsException {
        //do nothing - class not find in command map
    }
}

class Parser {
    public static String getFirst(String inCommand){
        StringTokenizer st = new StringTokenizer(inCommand, " ");
        int count = st.countTokens();
        if (count > 0 ){
            return st.nextToken();
        }
        else {
            return null;
        }
    }
    static StringTokenizer getTokenizer(String inCommand){
        return (new StringTokenizer(inCommand, " "));
    }
}


