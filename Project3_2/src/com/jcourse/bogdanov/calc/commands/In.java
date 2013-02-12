package com.jcourse.bogdanov.calc.commands;

import java.lang.annotation.*;

@Target(value=ElementType.FIELD)
@Retention(value= RetentionPolicy.RUNTIME)
@Inherited
public @interface In {
    String arg();
}
