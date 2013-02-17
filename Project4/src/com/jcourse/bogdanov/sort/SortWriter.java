package com.jcourse.bogdanov.sort;

import java.io.IOException;
import java.io.Writer;

public interface SortWriter {
    void writeOutput(Writer out, WordCounter[] outArray); //execute command
}
