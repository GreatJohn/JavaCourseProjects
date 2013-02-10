package com.jcourse.bogdanov.testtasks;

import com.jcourse.bogdanov.calc2.Cmd;
import com.jcourse.bogdanov.calc2.InputCmd;
import com.jcourse.bogdanov.calc2.Receiver;
import com.jcourse.bogdanov.calc2.commands.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Calc2DemoSQRT {
    public static void main(String[] args) {

        InputCmd rec;
        InputCmd abc;
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

        Double a,b,c;
        boolean negativeD = true;
        List<String> abcST;

        while(negativeD){
            System.out.println("Enter a,b,c values ((b*b-4*a*c)>=0) with linefeed :");
            abc = new Receiver().getInputCmd();
            abcST = abc.getStringList();
            try{
                a  = Double.parseDouble(abcST.get(0));
                b  = Double.parseDouble(abcST.get(1));
                c  = Double.parseDouble(abcST.get(2));
                if ((b*b-4*a*c)>=0) {
                    CommandExec.getCmd("DEFINE", mapCmd).exec("DEFINE a " + a,stack,mapParam);
                    CommandExec.getCmd("DEFINE", mapCmd).exec("DEFINE b " + b,stack,mapParam);
                    CommandExec.getCmd("DEFINE", mapCmd).exec("DEFINE c " + c,stack,mapParam);
                    negativeD = false;
                }
                else {
                    System.out.println("(b*b-4*a*c) < 0 , try again!!!");
                }
            } catch (NumberFormatException e){
                System.out.println("Not correct input, try again!");
            }
        }


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
