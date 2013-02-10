package com.jcourse.bogdanov.calc2;

import java.util.*;

public interface Cmd {
    void exec(String command, LinkedList<Double> stack, HashMap<String, Double> param); //execute command
}
