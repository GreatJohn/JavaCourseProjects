package com.jcourse.bogdanov.testtasks;

import com.jcourse.bogdanov.calc.*;
import com.jcourse.bogdanov.calc.commands.*;

import java.util.*;


public class CalcDemo {
    public static void main(String[] args) {
        LinkedList<Double> stack = new LinkedList<>();
        HashMap<String,Double> mapParam = new HashMap<>();
        InputCmd abc,rec;
        Double a = 0.0D;
        Double b = 0.0D;
        Double c = 0.0D;

        boolean negativeD = true;
        boolean twoSQRT = true;
        List<String> abcST;

        CommandFactory cf = new CommandFactory();
        Cmd cmd = cf.getCmdClass("DEFINE");

        while(negativeD){
            System.out.println("Enter a,b,c values ((b*b-4*a*c)>=0, a!=0) with linefeed :");
            abc = new Receiver().getInputCmd();
            abcST = abc.getStringList();
            try{
                a  = Double.parseDouble(abcST.get(0));
                b  = Double.parseDouble(abcST.get(1));
                c  = Double.parseDouble(abcST.get(2));
                if (((b*b-4*a*c)>=0.0D) && (a!=0.0D)) {
                    cmd.exec("DEFINE a " + a,stack,mapParam);
                    cmd.exec("DEFINE b " + b,stack,mapParam);
                    cmd.exec("DEFINE c " + c,stack,mapParam);
                    negativeD = false;
                    if ((b*b-4*a*c)==0.0D){
                        twoSQRT = false;
                    }
                }
                else {
                    if (a==0.0D){
                        System.out.println("a = 0 , try again!!!");
                    }
                    else
                    {
                        System.out.println("(b*b-4*a*c) < 0 , try again!!!");
                    }
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
                cmd = cf.getCmdClass(str);
                cmd.exec(str, stack, mapParam);
            }
            if (!twoSQRT) {
                System.out.println("X = " + stack.getLast());
            }
            else
            {
                System.out.println("X1 = " + stack.getLast());
                System.out.print("X2 = " + (((-1.0D)*(stack.getLast()))-(b/a)) );
            }
        }
    }
}
