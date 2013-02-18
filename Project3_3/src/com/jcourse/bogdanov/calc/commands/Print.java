package com.jcourse.bogdanov.calc.commands;

import java.util.Deque;

public class Print extends CommandExec {
    @In(arg = "stack")
    private Deque<Double> stack;
    public void exec(String inCommand) throws CalcCommandsException{
        if (stack.size()>0){
            System.out.println(stack.getLast().toString());
        }
        else {
            throw new CalcCommandsException("List is empty! " + this.getClass().getSimpleName() + ".exec(" + inCommand + ") fail!");
        }
    }
}