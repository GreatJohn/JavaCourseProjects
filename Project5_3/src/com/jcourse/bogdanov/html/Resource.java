package com.jcourse.bogdanov.html;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
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
    private SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public Resource(String cmd, String base, String file, String serverAndPort) {
        this.serverAndPort = serverAndPort;
        this.cmd = cmd;
        this.resource = new File((base + file).replace('\\', '/'));
        this.localPath = file;
        if (!this.resource.exists()) {
            this.status = "HTTP/1.1 404 File Not Found";
            this.statusOK = false;
            this.contents = "";
        } else if ((cmd.compareTo("GET") != 0) && ((cmd.compareTo("HEAD") != 0))) {
            this.status = "HTTP/1.1 501 Not Implemented";
            this.statusOK = false;
            this.contents = "";
        }
    }

    public InputStream getIS() {
        InputStream gb = getBody();
        InputStream gc = getContents();
        InputStream gs = getStatus();
        InputStream result = gs;
        if (!statusOK) return result;

        switch (cmd) {
            case "HEAD":
                if (statusOK) {
                    result = new SequenceInputStream(gs, gc);
                }
                break;
            case "GET":
                if (statusOK) {
                    InputStream tmp = new SequenceInputStream(gs, gc);
                    result = new SequenceInputStream(tmp, gb);
                }
                break;
        }
        return result;
    }

    private InputStream getBody() {
        InputStream result;

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

    private InputStream getContents() {
        if (statusOK) return new DataInputStream(new ByteArrayInputStream(this.contents.getBytes()));
        return null;
    }

    private InputStream getStatus() {
        if (statusOK) return new DataInputStream(new ByteArrayInputStream("HTTP/1.1 200 OK\r\n".getBytes()));
        return new DataInputStream(new ByteArrayInputStream((this.status + "\r\n").getBytes()));
    }

    private InputStream getResource(File file) throws MyException { //return file or index.html as page
        long fileLen = file.length();
        if (fileLen > Integer.MAX_VALUE) {
            throw new MyException("File size too big! " + fileLen);
        }
        try {
            Date d = new Date(file.lastModified());
            this.contents = "Content-Type: " + new MimetypesFileTypeMap().getContentType(file) + "\r\nContent-Length: " + fileLen + "\r\nLast-Modified: " + formatDate.format(d) + "\r\n\r\n";
            return new FileInputStream(file);
        } catch (IOException e) {
            throw new MyException("IOException! " + resource.getPath());
        }
    }

    private InputStream getResource() throws MyException {//return new HTML


        StringBuilder sb = new StringBuilder("<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML//EN\">\n<HTML>\n<HEAD>\n<TITLE>Contents " + localPath + "</TITLE>\n</HEAD>\n<BODY>\n<H2>Contents " + localPath + "</H2>\n<HR>\n<PRE>");
        sb.append("<TABLE CELLSPACING=\"5\" WIDTH=\"50%\">\n");

        File[] dirs = resource.listFiles(new SubDirs());
        Arrays.sort(dirs, new SortByName());
        for (File dir : dirs) {
            Date d = new Date(dir.lastModified());
            sb.append("<TR>\n<TD WIDTH = \"20%\">" + formatDate.format(d) + "</TD>\n<TD WIDTH = \"20%\" ALIGN=\"CENTER\">DIRECTORY</TD>\n<TD WIDTH = \"40%\"><A HREF=" + "\"http:/" + serverAndPort + (localPath + dir.getName()).replace('\\', '/').replaceAll(" ", "%20") + "/" + "\"" + "<B>" + dir.getName() + "</B></A></TD>\n</TR>\n");
        }

        File[] files = resource.listFiles(new SubFiles());
        Arrays.sort(files, new SortByName());

        for (File file : files) {
            Date d = new Date(file.lastModified());
            sb.append("<TR>\n<TD WIDTH = \"20%\">" + formatDate.format(d) + "</TD>\n<TD WIDTH = \"20%\">" + file.length() + "b</TD>\n<TD WIDTH = \"40%\"><A HREF=" + "\"http:/" + serverAndPort + (localPath + file.getName()).replace('\\', '/').replaceAll(" ", "%20") + "\"" + "<B>" + file.getName() + "</B></A></TD>\n</TR>\n");
        }

        sb.append("</TABLE>\n</PRE>\n<HR>\n</BODY>\n</HTML>\n");

        byte[] result = sb.toString().getBytes();

        Date d = new Date();
        this.contents = "Content-Type: text/html\r\nContent-Length: " + result.length + "\r\nLast-Modified: " + formatDate.format(d) + "\r\n\r\n";

        return new DataInputStream(new ByteArrayInputStream(result));
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
