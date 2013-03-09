package com.bogdanov.jcourse.guestbook;


import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@WebServlet(name = "SimpleServlet", urlPatterns = {"/simple"})
public class GuestBookServlet extends HttpServlet {
    @Resource(name = "jdbc/testDS")
    private DataSource ds;
    private String message = "";

    @Override
    public void init(){
        try (Connection c = ds.getConnection();
             Statement stmt = c.createStatement();) {
            String createTable = "CREATE TABLE posts (ID INT PRIMARY KEY AUTO_INCREMENT, postMessage VARCHAR(255), postDate TIMESTAMP)";//create new table
            stmt.execute(createTable);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection c = ds.getConnection();
             PrintWriter out = resp.getWriter();) {


            resp.setContentType("text/html");
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Guestbook</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>Type new message:</h3>");
            out.print("<form action=\"");
            out.print("simple\" ");
            out.println("method=POST>");
            out.println("<input type=text size=255 name=message>");
            out.println("<br>");
            out.println("<input type=submit value=Add name=Add>");
            out.println("</form>");

            String msg = req.getParameter("message");
            String add = req.getParameter("Add");
            if ((message == null) || (message.length() == 0)) {//first call
                message = msg;
            }

            GuestBookController gbc = new GuestBook(c);

            if ((msg != null) && (msg.length() > 0) && (add != null)) {
                gbc.addRecord(msg);
                out.println("added new message = " + msg);
            } else {
                out.println("Type not empty message text and press Add button");
            }


            List<Record> list = gbc.getRecords();
            out.println("<hr>");
            out.println("<h2 align=\"center\">Guestbook messages</h2>");
            out.println("<hr>");
            out.println("<table cellspacing=\"5\" width=\"100%\">");
            for (Record res : list) {
                out.println("<tr>");
                out.println("<td wigth=\"30%\" align=\"center\">" + res.getPostDate() + "</td>");
                out.println("<td wigth=\"70%\" align=\"left\">" + res.getMessage() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
