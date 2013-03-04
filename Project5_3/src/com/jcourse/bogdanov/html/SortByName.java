package com.jcourse.bogdanov.html;


import java.io.File;
import java.util.Comparator;

public class SortByName implements Comparator<File> {
    public int compare(File dt1, File dt2) {
        return dt1.getName().toLowerCase().compareTo(dt2.getName().toLowerCase());
    }
}


