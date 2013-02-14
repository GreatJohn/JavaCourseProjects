package com.jcourse.bogdanov.calc.commands;

import com.jcourse.bogdanov.calc.Cmd;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

public class CommandFactory {
    private static Properties prop = new Properties();
    @In(arg = "stack")
    LinkedList<Double> stack;
    @In(arg = "map")
    Map<String,Double> map;

    static {
        try {
            InputStream in = CommandFactory.class.getResourceAsStream("cmd.properties");
            prop.loadFromXML(in);
            in.close();
        } catch (IOException e) {
            throw new RuntimeException("static init block of CommandFactory fail! (" + e.getMessage() + ")");
        }
    }
    public CommandFactory(){
        stack = new LinkedList<>();
        map = new HashMap<>();
    }
    public Cmd getCmdClass(String cmdName){
        String className = prop.getProperty(Parser.getFirst(cmdName));
        Cmd cmd;
        Cmd proxy;
        class MethodSelector implements InvocationHandler{
            private Object proxied;
            MethodSelector(Object inProxied){
                this.proxied = inProxied;
            }
            public Object invoke (Object proxy, Method method, Object[] args) throws Throwable{
                Object obj = null;
                if(method.getName().equals("exec")){
                    System.out.println("DEBUG : Stack before " + stack.toString());
                    obj =  method.invoke(proxied,args);
                    System.out.println("DEBUG : Stack after " + stack.toString());
                }
                else
                    obj =  method.invoke(proxied,args);
                return obj;
            }
        }
        try {
            Class c = Class.forName(className);
            cmd = (Cmd) c.newInstance();
            for(Field f : cmd.getClass().getDeclaredFields()){
                if (f.getAnnotation(In.class).arg().equals("stack")) {
                    f.set(cmd,stack);
                    f.setAccessible(true);
                }
                if (f.getAnnotation(In.class).arg().equals("map")) {
                    f.set(cmd,map);
                    f.setAccessible(true);
                }
            }

            proxy = (Cmd) Proxy.newProxyInstance(Cmd.class.getClassLoader(),new Class[]{Cmd.class}, new MethodSelector(cmd));

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to find class! (" + className + "), "  + e.getMessage() + ")");
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to instantiate class! (" + className + "), "  + e.getMessage() + ")");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to access fields of class! (" + className + "), "  + e.getMessage() + ")");
        }
        return proxy;
    }
}
