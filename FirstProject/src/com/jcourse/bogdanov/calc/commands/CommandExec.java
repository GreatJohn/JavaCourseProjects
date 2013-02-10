package com.jcourse.bogdanov.calc.commands;

import com.jcourse.bogdanov.calc.Cmd;
import java.util.*;

public class CommandExec implements Cmd {

    public void exec(String command,  LinkedList<Double> inStack, Map<String,Double> inParam){
        CmdData cd = parseCmd(command);
        if (cd!= null){
           if (cd instanceof CmdDataWithParam){// CmdDataWithParam
               CmdDataWithParam cdwp = (CmdDataWithParam)cd;
               cdwp.define(inParam,cdwp.getParam(),cdwp.getNumber());             //define new parameter
           }
           else if (cd instanceof CmdDataWithDouble){ //CmdDataWithDouble
               CmdDataWithDouble cdwd = (CmdDataWithDouble)cd;
               if (cdwd.getNumber()!=null){cdwd.push(inStack, cdwd.getNumber());}//push number
               else {cdwd.push(inStack, inParam, cdwd.getParam());}              //push parameter if exist
           }
           else if (cd instanceof CmdDataWithComments){  // CmdDataWithComments
               CmdDataWithComments cdwc = (CmdDataWithComments)cd;
               cdwc.comments(cdwc.getComments());                                 //output comments
           }
           else if (cd instanceof CmdDataWithNoArgs){  // CmdDataWithNoArgs
               CmdDataWithNoArgs cdwna = (CmdDataWithNoArgs)cd;
               switch (cdwna.getName()){                                          //exec stack commands...
                   case NoArgCommands.POP :     cdwna.pop(inStack);
                        break;
                   case NoArgCommands.PRINT:    cdwna.print(inStack);
                        break;
                   case NoArgCommands.PLUS:     cdwna.plus(inStack) ;
                        break;
                   case NoArgCommands.MINUS:    cdwna.minus(inStack);
                        break;
                   case NoArgCommands.MULT:     cdwna.mult(inStack) ;
                        break;
                   case NoArgCommands.DIV:      cdwna.div(inStack) ;
                        break;
                   case NoArgCommands.SQRT:     cdwna.sqrt(inStack) ;
                        break;
                   default:                     System.out.println("Wrong command " + cdwna.getName() + " in CommandExec.exec()!!!");

               }

           }
        }
    }

    public Cmd getCmd(){
        return this;
    }

    private static CmdData parseCmd(String inCommand){ //parses cmd string and return appropriate CmdData or null
        StringTokenizer st = new StringTokenizer(inCommand, " ");
        int count = st.countTokens();
        String cmdString;

        if (count > 0 )
            {cmdString = st.nextToken();}
        else {
            return null;
        }

        if (count == 1 && NoArgCommands.list.contains(cmdString)){return new CmdDataWithNoArgs(cmdString);}
        if (count == 2 && OneArgCommands.list.contains(cmdString)){
            String cmdString2 = st.nextToken();
            if (cmdString.equals(OneArgCommands.COMMENT)) {return new CmdDataWithComments(cmdString,cmdString2);}
            try{
                Double doubParam = Double.parseDouble(cmdString2);
                return (new CmdDataWithDouble(cmdString,doubParam));
            }catch (NumberFormatException e){
                return (new CmdDataWithDouble(cmdString,cmdString2));
            }catch (NullPointerException e){
                return null;
            }
        }
        if (count == 3 && TwoArgCommands.list.contains(cmdString)){
            String cmdString2 = st.nextToken();
            String cmdString3 = st.nextToken();
            try{
                Double doubParam = Double.parseDouble(cmdString3);
                return (new CmdDataWithParam(cmdString,doubParam,cmdString2));
            }catch (NumberFormatException e){
                return null;
            }catch (NullPointerException e){
                return null;
            }
        }
        return null;
    }
}

class CmdData{
    private final String cmd;
    CmdData(String inCmd){
        this.cmd = inCmd;
    }
    String getName(){
        return this.cmd;
    }
}

class CmdDataWithNoArgs extends CmdData{
    CmdDataWithNoArgs(String inCmd){
        super(inCmd);
    }

    Double pop(LinkedList<Double> inStack){
        if (inStack.size()>0){return inStack.removeLast();}
        else {return null;}
    }
    void print(LinkedList<Double> inStack){
        System.out.println(inStack.getLast().toString());

    }
    void plus(LinkedList<Double> inStack){
        Double d1,d2;
        if (inStack.size() >1){
            d1 = pop(inStack);
            d2 = pop(inStack);
            inStack.addLast(d2+d1);
        }
        else {
            System.err.println("Unable execute command='+' when only one element in stack!");
        }
    }
    void minus(LinkedList<Double> inStack){
        Double d1,d2;
        if (inStack.size() >1){
            d1 = pop(inStack);
            d2 = pop(inStack);
            inStack.addLast(d2-d1);
        }
        else {
            System.err.println("Unable execute command='-' when only one element in stack!");
        }

    }
    void mult(LinkedList<Double> inStack){
        Double d1,d2;
        if (inStack.size() >1){
            d1 = pop(inStack);
            d2 = pop(inStack);
            inStack.addLast(d2*d1);
        }
        else {
            System.err.println("Unable execute command='*' when only one element in stack!");
        }
    }
    void div(LinkedList<Double> inStack){
        Double d1,d2;
        if (inStack.size() >1){
            d1 = pop(inStack);
            d2 = pop(inStack);
            inStack.addLast(d2/d1);
        }
        else {
            System.err.println("Unable execute command='/' when only one element in stack!");
        }
    }
    void sqrt(LinkedList<Double> inStack){
        Double d1;
        if ((inStack.size() >0) && (inStack.getLast() >= 0.0D)){
            d1 = pop(inStack);
            inStack.addLast(Math.sqrt(d1));
        }
        else {
            System.err.println("Unable execute command='SQRT' to negative number!");
        }
    }
}
class CmdDataWithDouble extends CmdData{
    private final Double number;
    private final String param;
    CmdDataWithDouble(String inCmd,Double inDouble){
        super(inCmd);
        this.number = inDouble;
        this.param = "";
    }
    CmdDataWithDouble(String inCmd,String inParam){
        super(inCmd);
        this.number = null;
        this.param = inParam;
    }
    void push(LinkedList<Double> inStack, Double inDouble){
        inStack.addLast(inDouble);
    }
    void push(LinkedList<Double> inStack,Map<String,Double> inMap, String inParam){
        if (inMap.containsKey(inParam)){push(inStack,inMap.get(inParam));}
        else System.err.println("Parameter " + inParam + " not defined! Define it before using.");
    }
    Double getNumber(){
        return this.number;
    }
    String getParam(){
        return this.param;
    }
}
class CmdDataWithParam extends CmdData{
    private final String param;
    private final Double number;
    CmdDataWithParam(String inCmd, Double inDouble, String inParam){
        super(inCmd);
        this.param = inParam;
        this.number = inDouble;
    }
    void define(Map<String,Double> inMap,String inKey,Double inParam){
        inMap.put(inKey, inParam);
    }
    Double getNumber(){
        return this.number;
    }
    String getParam(){
        return this.param;
    }
}
class CmdDataWithComments extends CmdData{
    private final String comments;
    CmdDataWithComments(String inCmd,String inComments){
        super(inCmd);
        this.comments = inComments;
    }
    void comments(String inComments){
        System.out.println("Comments : " + inComments);
    }
    String getComments(){
        return this.comments;
    }

}


