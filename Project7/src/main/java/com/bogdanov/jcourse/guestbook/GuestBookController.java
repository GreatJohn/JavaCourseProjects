package com.bogdanov.jcourse.guestbook;

import java.sql.SQLException;
import java.util.List;

public interface GuestBookController {
    void addRecord(String message) throws SQLException;

    List<Record> getRecords() throws SQLException;
}
