package com.jcourse.bogdanov.calc.commands;

import java.util.Deque;
import java.util.Map;
import java.util.StringTokenizer;

public class Push extends CommandExec {
    @In(arg = "stack")
    private Deque<Double> stack;
    @In(arg = "map")
    private Map<String,Double> map;
    public void exec(String inCommand) throws CalcCommandsException{
        StringTokenizer st = Parser.getTokenizer(inCommand);
        int count = st.countTokens();
        if (count == 2 ){
            st.nextToken();
            String paramOrNumber = st.nextToken();
            try {
                Double number = Double.parseDouble(paramOrNumber);
                stack.addLast(number);
            }catch (NumberFormatException e){
                if (map.containsKey(paramOrNumber)){
                    stack.addLast(map.get(paramOrNumber));
                }
                else {
                    throw new CalcCommandsException(this.getClass().getSimpleName() + ".exec(" + inCommand + ") fail! Parameter " + paramOrNumber + " not defined!");
                }
            }
        }
    }
}