package com.jcourse.bogdanov.calc;

import java.util.HashMap;
import java.util.LinkedList;

public interface Cmd {
    void exec(String command, LinkedList<Double> stack, HashMap<String, Double> param); //execute command
}
