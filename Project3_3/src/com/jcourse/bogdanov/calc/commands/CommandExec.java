package com.jcourse.bogdanov.calc.commands;

import com.jcourse.bogdanov.calc.Cmd;

import java.util.StringTokenizer;

public abstract class CommandExec implements Cmd {
    abstract public void exec(String inCommand) throws CalcCommandsException;
}

class Parser {
    public static String getFirst(String inCommand) {
        StringTokenizer st = new StringTokenizer(inCommand, " ");
        int count = st.countTokens();
        if (count > 0) {
            return st.nextToken();
        } else {
            return null;
        }
    }

    static StringTokenizer getTokenizer(String inCommand) {
        return (new StringTokenizer(inCommand, " "));
    }
}


