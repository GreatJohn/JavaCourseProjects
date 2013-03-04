package com.jcourse.bogdanov.html;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) throw new RuntimeException("Wrong args(must be two)!");
        int port;
        try {
            port = Integer.parseInt(args[0]);
            if (port < 0) throw new RuntimeException("Wrong args[0](port is negative)!");
        } catch (NumberFormatException e) {
            throw new RuntimeException("Wrong args[0](port must be int)!");
        }


        String dir = args[1].trim();
        File file = new File(dir);
        if (!file.exists()) {
            throw new RuntimeException("Wrong args[1](directory not exist)!");
        }

        if (!file.isDirectory()) {
            throw new RuntimeException("Wrong args[1](args[1] is not directory)!");
        }

        ServerSocket s = new ServerSocket(port);
        System.out.println("Start listen port " + s.getLocalPort() + ", on ip " + s.getLocalSocketAddress());
        while (true) {
            Socket clientSocket = s.accept();
            System.out.println("Recieved connect from:" + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
            Thread t = new Thread(new RequestProcessor(clientSocket, file));
            System.out.println("Starting processor...");
            t.start();
        }
    }
}

class RequestProcessor implements Runnable {
    Socket s;
    File file; //parent dir

    RequestProcessor(Socket s, File file) {
        this.s = s;
        this.file = file;
    }

    public void run() {
        try (
                InputStream input = s.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                OutputStream output = s.getOutputStream();
        ) {
            StringBuilder sb = new StringBuilder();
            int c;
            while (((c = reader.read()) != -1) && (c != 10) && (c != 13)) {
                sb.append((char) c);
            }
            String data = sb.toString();
            if ((data == null) || (data.length() == 0)) {
                data = "GET /";
            }
            String[] args = data.split(" ");
            String cmd = "GET";
            String resourcePath = "/";
            try {
                cmd = args[0].trim().toUpperCase();
                resourcePath = args[1].trim();
            } catch (NullPointerException e) {
                // do nothing use defaults
            }

            Resource gr = new Resource(cmd, file.getPath(), resourcePath.replaceAll("%20", " "), s.getLocalAddress() + ":" + s.getLocalPort());
            byte[] b = gr.get();
            output.write(b);
            output.flush();
            System.out.println("Processor finished.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
