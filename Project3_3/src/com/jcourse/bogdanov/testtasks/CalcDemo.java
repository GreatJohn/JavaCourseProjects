package com.jcourse.bogdanov.testtasks;

import com.jcourse.bogdanov.calc.*;
import com.jcourse.bogdanov.calc.commands.CalcCommandsException;
import com.jcourse.bogdanov.calc.commands.CommandFactory;
import com.jcourse.bogdanov.calc.commands.In;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.*;

class Parser {
    public static String getFirst(String inCommand){
        StringTokenizer st = new StringTokenizer(inCommand, " ");
        int count = st.countTokens();
        if (count > 0 ){
            return st.nextToken();
        }
        else {
            return null;
        }
    }
    static StringTokenizer getTokenizer(String inCommand){
        return (new StringTokenizer(inCommand, " "));
    }
}
public class CalcDemo {
    public static void main(String[] args) throws IOException,SQLException { // no args - console input, or sqrt1.txt & sqrt2.txt for SQRT solution

        final Deque<Double> stackMain = new LinkedList<>();
        final Map<String, Double> mapMain = new HashMap<>();
        Double a;
        Double b;
        Double c;
        boolean negativeD = true;
        boolean twoSQRT = true;
        String className;

        try(
            InputStream in = CalcDemo.class.getResourceAsStream("log4j.properties")){
            Properties p = new Properties();
            p.load(in);
            PropertyConfigurator.configure(p);
        }catch(Exception e) {
            throw new RuntimeException("static init block of CommandFactory fail! (unable to load 'log4j.properties' for DEBUG) (" + e.getMessage() + ")");
        }
        class MethodSelector implements InvocationHandler {
            private Object proxied;
            Logger log = Logger.getLogger(Cmd.class.getName());
            MethodSelector(Object inProxied){
                this.proxied = inProxied;
                log = Logger.getLogger(proxied.getClass());
            }
            public Object invoke (Object proxy, Method method, Object[] args) throws Throwable{
                Object obj;
                if(method.getName().equals("exec")){
                    log.debug("DEBUG : Stack before = " + stackMain);
                    log.debug("DEBUG : args = " + args[0]);
                    log.debug("DEBUG : map before = " + mapMain.entrySet().toString());
                    obj =  method.invoke(proxied,args);
                    log.debug("DEBUG : map after = " + mapMain.entrySet().toString());
                    log.debug("DEBUG : Stack after" + stackMain);
                }
                else
                    throw new RuntimeException("MethodSelector : Try to execute unknown method! (" + method.getName() + ")");
                return obj;
            }
        }

        CommandFactory cf = new CommandFactory(new CmdInitiator(){
            final Deque<Double> stack = stackMain;
            Map<String, Double> map = mapMain;
            @Override
            public void initCmd(Cmd cmd) {
                try {
                    for(Field f : cmd.getClass().getDeclaredFields()){
                        if (f.getAnnotation(In.class).arg().equals("stack")) {
                            f.setAccessible(true);
                            f.set(cmd,stack);
                        }
                        if (f.getAnnotation(In.class).arg().equals("map")) {
                            f.setAccessible(true);
                            f.set(cmd,map);
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Unable to access fields of class! (" + cmd.getClass().getName()  + "), "  + e.getMessage() + ")");
                }
            }
        });
        String strCmd;
        Cmd cmd;
        InputCmd rec = null;

        if (args.length == 0) {
            System.out.println("Enter commands(type ';' to abort):");
            rec = new ConsoleReceiver().getInputCmd();
            while(!(strCmd = rec.getNextString()).contains(";")){
                if (!strCmd.isEmpty()){
                    className = Parser.getFirst(strCmd);
                    cmd = cf.getProxy(new MethodSelector(cf.getCmdClass(className)));
                    try {
                        cmd.exec(strCmd);
                        System.out.println("Executed command = " + strCmd);
                    } catch (CalcCommandsException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
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
                    className = Parser.getFirst(strCmd);
                    cmd = cf.getCmdClass(className);
                    cmd.exec(strCmd);
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
                        className = Parser.getFirst(strCmd);
                        cmd = cf.getCmdClass(className);
                        cmd.exec(strCmd);
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
