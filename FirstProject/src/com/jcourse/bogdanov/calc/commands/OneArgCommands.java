package com.jcourse.bogdanov.calc.commands;

import java.util.*;

interface OneArgCommands { // list of One Arg Commands
    final static String PUSH = "PUSH";
    final static String COMMENT = "#";
    final static ArrayList<String> list = new ArrayList<>(Arrays.asList(PUSH,COMMENT));
}
