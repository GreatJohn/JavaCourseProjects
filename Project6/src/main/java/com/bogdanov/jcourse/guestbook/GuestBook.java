package com.bogdanov.jcourse.guestbook;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class GuestBook implements GuestBookController {
    private PreparedStatement pstmt;
    private String selectQuery;
    private Statement stmt;
    private final SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    GuestBook(Connection con) throws SQLException {
        this.pstmt = con.prepareStatement("INSERT INTO posts (postMessage,postDate) VALUES (?, CURRENT_TIMESTAMP())");
        this.selectQuery = "SELECT * FROM posts";
        this.stmt = con.createStatement();
    }

    @Override
    public void addRecord(String message) throws SQLException {
        pstmt.setString(1, message);
        pstmt.execute();
    }

    @Override
    public List<Record> getRecords() throws SQLException {
        List<Record> result = new LinkedList<>();
        ResultSet rs = stmt.executeQuery(selectQuery);
        while (rs.next()) {
            Record rec = new Record(rs.getInt("ID"), rs.getString("postMessage"), formatDate.format(rs.getTimestamp("postDate")));
            result.add(rec);
        }
        return result;
    }
}
