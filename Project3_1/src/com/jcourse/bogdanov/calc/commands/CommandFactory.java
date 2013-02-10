package com.jcourse.bogdanov.calc.commands;

import com.jcourse.bogdanov.calc.*;

import java.io.*;
import java.util.*;

public class CommandFactory {
    private static Properties  prop = new Properties();
    static {
        try{
            InputStream in = CommandFactory.class.getResourceAsStream("cmd.properties");
            prop.loadFromXML(in);
            in.close();
        } catch (IOException e){
            System.err.println("IOException in static init block of CommandFactory " + e.getMessage());
        }
    }
    public Cmd getCmdClass(String cmdName){
        String className = prop.getProperty(Parser.getFirst(cmdName));
        Cmd cmd = null;
        try {
            Class c = Class.forName(className);
            cmd = (Cmd)c.newInstance();
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException in CommandFactory.getCmdClass " + e.getMessage());
        } catch (InstantiationException e) {
            System.err.println("InstantiationException in CommandFactory.getCmdClass " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.err.println("IllegalAccessException in CommandFactory.getCmdClass " + e.getMessage());
        }
        return cmd;
    }
}
