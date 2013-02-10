package com.jcourse.bogdanov.calc.commands;

import java.util.*;

interface NoArgCommands { // list of No Arg Commands
    final static String POP = "POP";
    final static String PRINT = "PRINT";
    final static String PLUS = "+";
    final static String MINUS = "-";
    final static String MULT = "*";
    final static String DIV = "/";
    final static String SQRT = "SQRT";
    final static ArrayList<String> list = new ArrayList<>(Arrays.asList(POP, PRINT, PLUS, MINUS, MULT, DIV, SQRT));
}
