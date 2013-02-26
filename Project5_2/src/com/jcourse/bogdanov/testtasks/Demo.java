package com.jcourse.bogdanov.testtasks;


import com.jcourse.bogdanov.html.DirTreeGenerator;
import com.jcourse.bogdanov.html.MyException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Demo {

    public static void main(String[] args) throws MyException {
        DirTreeGenerator dtg = new DirTreeGenerator();
        File f = new File("c:/examples");

        String s = dtg.generateHTML(f);
        File f2 = new File(f, "index.html");
        try (FileWriter fr = new FileWriter(f2);) {
            fr.write(s);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
