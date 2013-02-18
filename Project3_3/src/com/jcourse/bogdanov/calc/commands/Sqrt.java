package com.jcourse.bogdanov.calc.commands;

import java.util.Deque;

public class Sqrt extends CommandExec {
    @In(arg = "stack")
    private Deque<Double> stack;
    public void exec(String inCommand) throws CalcCommandsException{
        Double d1;
        if ((stack.size() >0) && (stack.getLast() >= 0.0D)){
            d1 = stack.removeLast();
            stack.addLast(Math.sqrt(d1));
        }
        else if((stack.size() == 0)) {
            throw new CalcCommandsException("List is empty! " + this.getClass().getSimpleName() + ".exec(" + inCommand + ") fail!");
        }
        else {
            throw new CalcCommandsException("List contains negative number! " + this.getClass().getSimpleName() + ".exec(" + inCommand + ") fail!");
        }
    }
}