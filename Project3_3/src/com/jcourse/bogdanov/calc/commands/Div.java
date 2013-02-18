package com.jcourse.bogdanov.calc.commands;

import java.util.Deque;

public class Div extends CommandExec {
    @In(arg = "stack")
    private Deque<Double> stack;
    public void exec(String inCommand) throws CalcCommandsException{
        Double d1,d2;
        if (stack.size() >1){
            d1 = stack.removeLast();
            d2 = stack.removeLast();
            stack.addLast(d2/d1);
        }else if((stack.size() == 0)) {
            throw new CalcCommandsException("List is empty! " + this.getClass().getSimpleName() + ".exec(" + inCommand + ") fail!");
        }
        else {
            throw new CalcCommandsException("List contains only one number! " + this.getClass().getSimpleName() + ".exec(" + inCommand + ") fail!");
        }
    }
}