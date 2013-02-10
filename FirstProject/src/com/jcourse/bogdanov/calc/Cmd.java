package com.jcourse.bogdanov.calc;

import java.util.*;

public interface Cmd {
    void exec(String command,  LinkedList<Double> stack, Map<String,Double> param); //execute command
    public Cmd getCmd();

}
