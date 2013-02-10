package com.jcourse.bogdanov.testtasks;

import com.jcourse.bogdanov.calc2.*;
import com.jcourse.bogdanov.calc2.commands.*;
import java.io.*;
import java.util.*;

public class Calc2Demo {
    public static void main(String[] args) {

        InputCmd rec;
        HashMap<String,Cmd> mapCmd = new HashMap<>();
        LinkedList<Double> stack = new LinkedList<>();
        HashMap<String,Double> mapParam = new HashMap<>();
        mapCmd.put("POP", new Pop());
        mapCmd.put("PUSH", new Push());
        mapCmd.put("/", new Div());
        mapCmd.put("*", new Mult());
        mapCmd.put("+", new Plus());
        mapCmd.put("-", new Minus());
        mapCmd.put("#", new Comments());
        mapCmd.put("DEFINE", new Define());
        mapCmd.put("SQRT", new Sqrt());
        mapCmd.put("PRINT", new Print());

        if (args.length == 0) {
            System.out.println("Enter commands:");
            rec = new Receiver().getInputCmd();
        }
        else {
            rec = new Receiver(args[0]).getInputCmd();
        }
        List<String> st = rec.getStringList();
        if (st!=null){
            for(String str : st) {
                CommandExec.getCmd(str, mapCmd).exec(str,stack,mapParam);
            }
            System.out.print("Result = ");
            CommandExec.getCmd("PRINT", mapCmd).exec("PRINT",stack,mapParam);
        }


    }
}
