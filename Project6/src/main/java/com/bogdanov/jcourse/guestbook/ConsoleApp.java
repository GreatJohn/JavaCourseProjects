package com.bogdanov.jcourse.guestbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ConsoleApp {
    public static void main(String[] args) {

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try
                (Connection con = DriverManager.getConnection("jdbc:h2:mem:mydatabase", "user1", "password1");
                 Statement stmt = con.createStatement();) {

            String createTable = "CREATE TABLE posts (ID INT PRIMARY KEY AUTO_INCREMENT, postMessage VARCHAR(255), postDate TIMESTAMP)";//create new table
            stmt.execute(createTable);

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String s;

            GuestBookController gbc = new GuestBook(con);
            System.out.println("Enter command (type 'exit' to quit) : ");
            while ((s = br.readLine().trim()).compareTo("exit") != 0) {
                if (s.toLowerCase().startsWith("add")) {
                    if (s.length() > 4) {
                        String message = s.substring(4);
                        gbc.addRecord(message);
                        System.out.println("message added");
                    } else {
                        System.out.println("You try to add empty message!");
                        continue;
                    }
                } else if (s.toLowerCase().startsWith("list")) {
                    List<Record> list = gbc.getRecords();
                    if (list.isEmpty()) {
                        System.out.println("no records!");
                        continue;
                    }
                    for (Record rec : list) {
                        System.out.println("id = " + rec.id + ", message = " + rec.message + ", date = " + rec.postDate);
                    }
                } else {
                    System.out.println("Wrong command!");
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
