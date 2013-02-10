package com.jcourse.bogdanov.testtasks;

import com.jcourse.bogdanov.calc.*;
import com.jcourse.bogdanov.calc.commands.*;
import java.io.*;
import java.util.*;

public class CalcDemo {
    public static void main(String[] args) throws IOException {
        InputCmd rec;
        if (args.length == 0) {
            rec = new Receiver().getInputCmd();
            System.out.println("Enter commands:");
        }
        else {rec = new Receiver(args[0]).getInputCmd();}
        StringTokenizer st =  rec.getStringTokenizer();
        if (st!=null){
            CommandExec ce = new CommandExec();
            LinkedList<Double> stack = new LinkedList<>();
            Map<String,Double> map = new HashMap<>();
            while (st.hasMoreTokens()){
                ce.exec(st.nextToken(),stack,map);
            }
            System.out.print("Result = ");
            ce.exec("PRINT",stack,map);
        }
    }
}
