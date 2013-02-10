package com.jcourse.bogdanov.testtasks;

import com.jcourse.bogdanov.calc3.*;
import com.jcourse.bogdanov.calc3.commands.*;

import java.io.*;
import java.util.*;


public class Calc3Demo {
    public static void main(String[] args) {
        LinkedList<Double> stack = new LinkedList<>();
        HashMap<String,Double> mapParam = new HashMap<>();


        /*
        Properties prop = new Properties();
        prop.put("POP",Pop.class.getCanonicalName());
        prop.put("PUSH",Push.class.getCanonicalName());
        prop.put("/",Div.class.getCanonicalName());
        prop.put("*",Mult.class.getCanonicalName());
        prop.put("+",Plus.class.getCanonicalName());
        prop.put("-",Minus.class.getCanonicalName());
        prop.put("#",Comments.class.getCanonicalName());
        prop.put("DEFINE",Define.class.getCanonicalName());
        prop.put("SQRT",Sqrt.class.getCanonicalName());
        prop.put("PRINT",Print.class.getCanonicalName());

        try {
            prop.storeToXML(new Writer("cmd.properties").getOS(),"add put");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        */


        /*
        try {
            prop.loadFromXML(new FileInputStream("cmd.properties"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        String className = prop.getProperty("PUSH");

        Cmd cmd = null;

        try {
            Class c = Class.forName(className);
            cmd = (Cmd)c.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        cmd.exec("PUSH 1", stack, mapParam);
        */

        CommandFactory cf = new CommandFactory();
        Cmd cmd = cf.getCmdClass("PUSH");
        cmd.exec("PUSH 1", stack, mapParam);

        System.out.print("Result = ");
        cmd = cf.getCmdClass("PRINT");
        cmd.exec("PRINT", stack, mapParam);



    }
}
