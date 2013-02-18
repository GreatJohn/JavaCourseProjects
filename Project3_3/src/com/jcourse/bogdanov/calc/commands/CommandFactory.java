package com.jcourse.bogdanov.calc.commands;

import com.jcourse.bogdanov.calc.Cmd;
import com.jcourse.bogdanov.calc.CmdInitiator;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CommandFactory {
    private static final Properties prop = new Properties();
    private final Map<String, Cmd> commands = new HashMap<>();
    private final CmdInitiator initCmd;
    static {
        try {
            InputStream in = CommandFactory.class.getResourceAsStream("cmd.properties.xml");
            prop.loadFromXML(in);
            in.close();
        } catch (IOException e) {
            throw new RuntimeException("static init block of CommandFactory fail! (unable to load 'cmd.properties.xml' for command classes initiation) (" + e.getMessage() + ")");
        }
    }
    public CommandFactory(CmdInitiator initCmd){
        this.initCmd = initCmd;
        initMapCmd();
    }
    private void initMapCmd(){
        for(String cmdName : prop.stringPropertyNames()) {
            String cmdClassName = prop.getProperty(cmdName);
            Cmd cmd;
            try {
                Class c = Class.forName(cmdClassName);
                cmd = (Cmd) c.newInstance();
                initCmd.initCmd(cmd);
                commands.put(cmdName,cmd);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("initMapCmd(): Unable to find class to init! (" + cmdClassName + "), "  + e.getMessage() + ")");
            } catch (InstantiationException e) {
                throw new RuntimeException("Unable to instantiate class! (" + cmdClassName + "), "  + e.getMessage() + ")");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Unable to access fields of class! (" + cmdClassName + "), "  + e.getMessage() + ")");
            }
        }
    }
    public Cmd getCmdClass(String cmdName){
        return this.commands.get(cmdName);
    }
    public Cmd getProxy(InvocationHandler ih){
        return (Cmd) Proxy.newProxyInstance(Cmd.class.getClassLoader(),new Class[]{Cmd.class}, ih);
    }
}
