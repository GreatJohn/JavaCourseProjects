package com.jcourse.bogdanov.testtasks;

import com.jcourse.bogdanov.calc.*;
import com.jcourse.bogdanov.calc.commands.*;
import java.io.*;
import java.util.*;


public class CalcDemoSQRTSolution {
        public static void main(String[] args) throws IOException {
            System.out.println("Enter a,b,c values with command : ");
            System.out.println("DEFINE param value (example DEFINE a 1.55):");
            System.out.println("example :");
            System.out.println("DEFINE a 1.55");
            System.out.println("DEFINE b 15.6");
            System.out.println("DEFINE c 0.88");
            System.out.println("Note: (b*b - 4*a*c) must be >= 0!!!");
            InputCmd abc = new Receiver().getInputCmd();
            StringTokenizer abcSt = abc.getStringTokenizer();
            InputCmd rec = new Receiver(args[0]).getInputCmd();
            StringTokenizer recSt =  rec.getStringTokenizer();

            if ((recSt!=null) && (abcSt!=null)){
                Cmd ce = new CommandExec().getCmd();
                LinkedList<Double> stack = new LinkedList<>();
                Map<String,Double> map = new HashMap<>();
                while (abcSt.hasMoreTokens()){
                    ce.exec(abcSt.nextToken(),stack,map);
                }
                while (recSt.hasMoreTokens()){
                    ce.exec(recSt.nextToken(),stack,map);
                }
                System.out.print("Result(+-) = ");
                ce.exec("PRINT",stack,map);
            }
        }
}
