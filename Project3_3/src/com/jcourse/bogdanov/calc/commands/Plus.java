package com.jcourse.bogdanov.calc.commands;

import java.util.LinkedList;

public class Plus extends CommandExec {
    @In(arg = "stack")
    LinkedList<Double> stack;
    public void exec(String inCommand) throws CalcCommandsException{
        Double d1,d2;
        if (stack.size() >1){
            d1 = stack.removeLast();
            d2 = stack.removeLast();
            stack.addLast(d2+d1);
        }else if((stack.size() == 0)) {
            throw new CalcCommandsException("List is empty! " + this.getClass().getSimpleName() + ".exec(" + inCommand + ") fail!");
        }
        else {
            throw new CalcCommandsException("List contains only one number! " + this.getClass().getSimpleName() + ".exec(" + inCommand + ") fail!");
        }
    }
}