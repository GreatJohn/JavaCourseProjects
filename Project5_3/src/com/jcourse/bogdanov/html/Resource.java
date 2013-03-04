package com.jcourse.bogdanov.html;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Resource {
    private String status;
    private String contents;
    private boolean statusOK = true;
    private File resource;
    private String localPath;
    private String serverAndPort;
    private String cmd;
    SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public Resource(String cmd, String base, String file, String serverAndPort) {
        this.serverAndPort = serverAndPort;
        this.cmd = cmd;
        this.resource = new File((base + file).replace('\\', '/'));
        this.localPath = file;
        if (!resource.exists()) {
            status = "HTTP/1.1 404 File Not Found";
            statusOK = false;
            this.contents = "";
        } else if ((cmd.compareTo("GET") != 0) && ((cmd.compareTo("HEAD") != 0))) {
            status = "HTTP/1.1 501 Not Implemented";
            statusOK = false;
            this.contents = "";
        }
    }

    public byte[] get() {
        byte[] gb = getBody();
        byte[] gc = getContents();
        byte[] gs = getStatus();
        byte[] result = null;
        switch (cmd) {
            case "HEAD":
                result = new byte[gs.length + gc.length];
                System.arraycopy(gs, 0, result, 0, gs.length);
                if (statusOK) {
                    System.arraycopy(gc, 0, result, gs.length, gc.length);
                }
                break;
            case "GET":
                result = new byte[gs.length + gc.length + gb.length];
                System.arraycopy(gs, 0, result, 0, gs.length);
                if (statusOK) {
                    System.arraycopy(gc, 0, result, gs.length, gc.length);
                    System.arraycopy(gb, 0, result, gs.length + gc.length, gb.length);
                }
                break;
        }
        return result;
    }

    private byte[] getBody() {
        byte[] result;

        try {

            if (resource.isDirectory()) {//generate new index.html or get existing index.html
                File index = new File(resource, "index.html");
                if (index.exists()) {
                    result = getResource(index);
                } else {
                    result = getResource();
                }
                return result;
            }
            if (resource.isFile()) {
                result = getResource(resource);
                return result;
            }

            throw new MyException("Unable to determine type of resource! " + resource.getPath());

        } catch (MyException e) {
            statusOK = false;
            status = "HTTP/1.1 500 " + e;
            return null;
        }

    }

    private byte[] getContents() {
        if (statusOK) return this.contents.getBytes();
        return null;
    }

    private byte[] getStatus() {
        if (statusOK) return ("HTTP/1.1 200 OK\r\n".getBytes());
        return (this.status + "\r\n").getBytes();
    }

    private byte[] getResource(File file) throws MyException { //return file or index.html as page
        long fileLen = file.length();
        if (fileLen > Integer.MAX_VALUE) {
            throw new MyException("File size too big! " + resource.length());
        }
        int arrayLen = (int) fileLen;
        byte[] result = new byte[arrayLen];
        try
                (FileInputStream fis = new FileInputStream(file);) {
            fis.read(result);
            Date d = new Date(file.lastModified());
            if (resource.getName().compareTo("index.html") == 0) {
                this.contents = "Content-Type: text/html\r\nContent-Length: " + file.length() + "\r\nLast-Modified: " + formatDate.format(d) + "\r\n\r\n";
            } else {
                this.contents = "Content-Type: application/octet-stream\r\nContent-Length: " + file.length() + "\r\nLast-Modified: " + formatDate.format(d) + "\r\n\r\n";
            }
            return result;
        } catch (IOException e) {
            throw new MyException("IOException! " + resource.getPath());
        }
    }

    private byte[] getResource() throws MyException {//return new HTML


        StringBuilder sb = new StringBuilder("<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML//EN\">\n<HTML>\n<HEAD>\n<TITLE>Contents " + localPath + "</TITLE>\n</HEAD>\n<BODY>\n<H2>Contents " + localPath + "</H2>\n<HR>\n<PRE>");
        sb.append("<TABLE CELLSPACING=\"5\" WIDTH=\"50%\">\n");

        File[] dirs = resource.listFiles(new SubDirs());
        Arrays.sort(dirs, new SortByName());
        for (int i = 0; i < dirs.length; i++) {
            Date d = new Date(dirs[i].lastModified());
            sb.append("<TR>\n<TD WIDTH = \"20%\">" + formatDate.format(d) + "</TD>\n<TD WIDTH = \"20%\" ALIGN=\"CENTER\">DIRECTORY</TD>\n<TD WIDTH = \"40%\"><A HREF=" + "\"http:/" + serverAndPort + (localPath + dirs[i].getName()).replace('\\', '/').replaceAll(" ", "%20") + "/" + "\"" + "<B>" + dirs[i].getName() + "</B></A></TD>\n</TR>\n");
        }

        File[] files = resource.listFiles(new SubFiles());
        Arrays.sort(files, new SortByName());

        for (int i = 0; i < files.length; i++) {
            Date d = new Date(files[i].lastModified());
            sb.append("<TR>\n<TD WIDTH = \"20%\">" + formatDate.format(d) + "</TD>\n<TD WIDTH = \"20%\">" + files[i].length() + "b</TD>\n<TD WIDTH = \"40%\"><A HREF=" + "\"http:/" + serverAndPort + (localPath + files[i].getName()).replace('\\', '/').replaceAll(" ", "%20") + "\"" + "<B>" + files[i].getName() + "</B></A></TD>\n</TR>\n");
        }

        sb.append("</TABLE>\n</PRE>\n<HR>\n</BODY>\n</HTML>\n");

        byte[] result = sb.toString().getBytes();

        Date d = new Date();
        this.contents = "Content-type: text/html\r\nContent-length: " + result.length + "\r\nLast-Modified: " + formatDate.format(d) + "\r\n\r\n";

        return result;
    }

}

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
