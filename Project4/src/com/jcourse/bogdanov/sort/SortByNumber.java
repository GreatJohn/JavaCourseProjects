package com.jcourse.bogdanov.sort;

import java.util.Comparator;

public class SortByNumber implements Comparator<WordCounter> {
        public int compare(WordCounter dt1,WordCounter dt2) {
            return dt2.getCounter() - dt1.getCounter();
        }
}
