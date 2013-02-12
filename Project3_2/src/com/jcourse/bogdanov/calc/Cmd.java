package com.jcourse.bogdanov.calc;

import com.jcourse.bogdanov.calc.commands.CalcCommandsException;

public interface Cmd {
    void exec(String command) throws CalcCommandsException; //execute command
}
