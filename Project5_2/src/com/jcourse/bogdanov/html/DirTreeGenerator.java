package com.jcourse.bogdanov.html;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

class SubDirs implements FileFilter {
    @Override
    public boolean accept(File f) {
        return (f.isDirectory());
    }
}

class SubFiles implements FileFilter {
    @Override
    public boolean accept(File f) {
        return (f.isFile());
    }
}


public class DirTreeGenerator {

    public String generateHTML(File dirIn) throws MyException {//return String as HTML

        if ((!dirIn.exists()) || (!dirIn.isDirectory())) {
            throw new MyException("generateHTML : Unable to find such dir!");
        }

        StringBuilder sb = new StringBuilder("<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML//EN\">\n<HTML>\n<HEAD>\n<TITLE>Contents " + dirIn.getPath() + "</TITLE>\n</HEAD>\n<BODY>\n<H2>Contents " + dirIn.getPath() + "</H2>\n<HR>\n<PRE>");
        sb.append("<TABLE CELLSPACING=\"5\" WIDTH=\"30%\">\n");

        File[] dirs = dirIn.listFiles(new SubDirs());
        Arrays.sort(dirs, new SortByName());
        for (int i = 0; i < dirs.length; i++) {
            Date d = new Date(dirs[i].lastModified());
            SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            sb.append("<TR>\n<TD WIDTH = \"20%\">" + formatDate.format(d) + "</TD>\n<TD WIDTH = \"20%\" ALIGN=\"CENTER\">DIRECTORY</TD>\n<TD WIDTH = \"40%\"><A HREF=" + "\"" + dirs[i].getPath() + "\"" + "<B>" + dirs[i].getName() + "</B></A></TD>\n</TR>\n");
        }

        File[] files = dirIn.listFiles(new SubFiles());
        Arrays.sort(files, new SortByName());

        for (int i = 0; i < files.length; i++) {
            Date d = new Date(files[i].lastModified());
            SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            sb.append("<TR>\n<TD WIDTH = \"20%\">" + formatDate.format(d) + "</TD>\n<TD WIDTH = \"20%\">" + files[i].length() + "b</TD>\n<TD WIDTH = \"40%\"><A HREF=" + "\"" + files[i].getPath() + "\"" + "<B>" + files[i].getName() + "</B></A></TD>\n</TR>\n");
        }

        sb.append("</TABLE>\n</PRE>\n<HR>\n</BODY>\n</HTML>\n");

        return sb.toString();
    }
}
