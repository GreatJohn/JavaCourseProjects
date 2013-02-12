package com.jcourse.bogdanov.testtasks;

import com.jcourse.bogdanov.calc.Cmd;
import com.jcourse.bogdanov.calc.ConsoleReceiver;
import com.jcourse.bogdanov.calc.FileReceiver;
import com.jcourse.bogdanov.calc.InputCmd;
import com.jcourse.bogdanov.calc.commands.CalcCommandsException;
import com.jcourse.bogdanov.calc.commands.CommandFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CalcDemo {
    public static void consoleExec(){
        System.out.println("Enter commands(type ';' to abort):");
        InputCmd rec = new ConsoleReceiver().getInputCmd();
        String strCmd;
        Cmd cmd = null;
        CommandFactory cf = new CommandFactory();
        while(!(strCmd = rec.getNextString()).contains(";")){
            if (!strCmd.isEmpty()){
                cmd = cf.getCmdClass(strCmd);
                try {
                    cmd.exec(strCmd);
                    System.out.println("Executed command = " + strCmd);
                } catch (CalcCommandsException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }
    public static void main(String[] args) {

        Double a;
        Double b;
        Double c;
        boolean negativeD = true;
        boolean twoSQRT = true;
        CommandFactory cf = new CommandFactory();
        String strCmd;
        Cmd cmd;
        InputCmd rec = null;

        if (args.length == 0) {
            consoleExec();
            System.exit(0);
        }
        else {
            try {
                rec = new FileReceiver(args[0]).getInputCmd();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            InputCmd abcRec = new ConsoleReceiver().getInputCmd();
            while(negativeD){
                System.out.println("Enter a,b,c values ((b*b-4*a*c)>=0, a!=0) with linefeed :");
                try{
                    List<String> abcList = new ArrayList<>(3);
                    for(int i=0;i<3;i++){
                        abcList.add(abcRec.getNextString());
                    }
                    a  = Double.parseDouble(abcList.get(0));
                    b  = Double.parseDouble(abcList.get(1));
                    c  = Double.parseDouble(abcList.get(2));
                    if (((b*b-4*a*c)>=0.0D) && (a!=0.0D)) {
                        cmd = cf.getCmdClass("DEFINE");
                        try {
                            cmd.exec("DEFINE a " + a);
                            cmd.exec("DEFINE b " + b);
                            cmd.exec("DEFINE c " + c);
                        }catch (CalcCommandsException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
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
            try {
                while((strCmd = rec.getNextString())!=null){
                    cmd = cf.getCmdClass(strCmd);
                    cmd.exec(strCmd);
                    System.out.println("Executed command = " + strCmd);
                }
                if (!twoSQRT) {
                    System.out.print("X = ");
                    cmd = cf.getCmdClass("PRINT");
                    cmd.exec("PRINT");
                }
                else {
                    System.out.print("X1 = ");
                    cmd = cf.getCmdClass("PRINT");
                    cmd.exec("PRINT");
                    try {
                        rec = new FileReceiver(args[1]).getInputCmd();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    while((strCmd = rec.getNextString())!=null){
                        cmd = cf.getCmdClass(strCmd);
                        cmd.exec(strCmd);
                        System.out.println("Executed command = " + strCmd);
                    }
                    System.out.print("X2 = ");
                    cmd = cf.getCmdClass("PRINT");
                    cmd.exec("PRINT");
                }
            }catch (CalcCommandsException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

    }
}
