package com.jcourse.bogdanov.calc2.commands;

import com.jcourse.bogdanov.calc2.*;
import java.util.*;

public class CommandExec implements Cmd  {

    public static Cmd getCmd(String inCmd, HashMap<String,Cmd> inMap){
        String cmdName = Parser.getFirst(inCmd);
        if (inMap.containsKey(cmdName)){
            return inMap.get(cmdName);
        }
        else return new CommandExec();
    }
    public void exec(String inCommand, LinkedList<Double> inStack, HashMap<String, Double> inMap){
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


