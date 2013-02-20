package com.jcourse.bogdanov.calc.commands;

import java.util.Deque;
import java.util.Map;
import java.util.StringTokenizer;


public class Define extends CommandExec {
    @In(arg = "stack")
    private Deque<Double> stack;
    @In(arg = "map")
    private Map<String, Double> map;

    public void exec(String inCommand) throws CalcCommandsException {
        StringTokenizer st = Parser.getTokenizer(inCommand);
        int count = st.countTokens();
        if (count == 3) {
            try {
                st.nextToken();
                String param = st.nextToken();
                Double number = Double.parseDouble(st.nextToken());
                map.put(param, number);
            } catch (NumberFormatException e) {
                throw new CalcCommandsException("You tried to DEFINE not a number! " + this.getClass().getSimpleName() + ".exec(" + inCommand + ") fail!");
            }
        }
    }
}