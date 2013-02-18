package com.jcourse.bogdanov.calc.commands;

import java.util.Deque;

public class Exp extends CommandExec {
    @In(arg = "stack")
    private Deque<Double> stack;
    public void exec(String inCommand) throws CalcCommandsException{
        Double d1;
        if ((stack.size() >0)){
            d1 = stack.removeLast();
            stack.addLast(Math.exp(d1));
        }
        else {
            throw new CalcCommandsException("List is empty! " + this.getClass().getSimpleName() + ".exec(" + inCommand + ") fail!");
        }
    }
}