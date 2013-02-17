package com.jcourse.bogdanov.sort;

import java.io.Reader;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SortReader {
    Map<String,WordCounter> readInput(Reader in); //execute command
}
