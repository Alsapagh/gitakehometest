/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmanager;

import dbSource.DBSource;
import filemanager.FileManager;
import gitakehometest.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Alsapagh
 */
public class BookManager {

    int id;
    String title;
    String author;
    String descr;
    Connection conn;

    public void addBook() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object

        conn = DBSource.getConnection();
        Book book = null;
        System.out.println("please inter the Book Title ");
        String title = scanner.nextLine();
        System.out.println("please inter the Book Author ");
        String author = scanner.nextLine();
        System.out.println("please inter the Book Description ");
        String descr = scanner.nextLine();
        int maxId = 0;
        Statement st = null;
        ResultSet rs = null;
        st = conn.createStatement();
        rs = st.executeQuery("SELECT NVL(MAX(ID),0)+1 ID FROM BOOK");
        Book b = null;
        while (rs.next()) {
            maxId = rs.getInt("ID");
        }
        String sql
                = "INSERT INTO BOOK (ID,TITLE,AUTHOR,DESCRIPTION)values ("
                + maxId + ",'" + title + "','" + author + "','" + descr + "')";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.executeUpdate();

        st.close();
        rs.close();
        conn.close();
        ps.close();
        System.out.println("a book was Added successfully to the Book List with Id " + maxId);
        agin();

    }

    public void editBook() throws ClassNotFoundException, SQLException {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        Connection conn = DBSource.getConnection();
        ResultSet rs = null;
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery("SELECT ID,TITLE,AUTHOR,DESCRIPTION FROM BOOK");
            Book b = null;
            while (rs.next()) {
                id = rs.getInt("ID");
                title = rs.getString("TITLE");
                author = rs.getString("AUTHOR");
                descr = rs.getString("DESCRIPTION");
                System.out.println(id + "\t" + title + "\t" + author + "\t" + descr);
            }
            System.out.println("Enter the book ID of the book you want to edit");
            System.out.print("Book ID: ");
            int editId = scanner.nextInt();

            rs = st.executeQuery("SELECT ID,TITLE,AUTHOR,DESCRIPTION FROM BOOK WHERE ID =" + editId);
            while (rs.next()) {
                title = scanner.nextLine();
                id = rs.getInt("ID");
                System.out.println("Updating with this ID " + rs.getInt("ID") + "");
                System.out.print("Title [" + rs.getString("TITLE") + "]: \t");
                title = scanner.nextLine();
                title = title == null ? rs.getString("TITLE") : title;
                System.out.print("Author [" + rs.getString("AUTHOR") + "]: \t");
                author = scanner.nextLine();
                author = author == null ? rs.getString("AUTHOR") : author;
                System.out.print("Description: [" + rs.getString("DESCRIPTION") + "]: \t");
                descr = scanner.nextLine();
                descr = descr == null ? rs.getString("DESCRIPTION") : descr;

            }
            String sql
                    = "UPDATE BOOK SET TITLE='" + title + "',AUTHOR='" + author + "',DESCRIPTION='" + descr + "' WHERE ID =" + id;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();

            st.close();
            rs.close();
            conn.close();
            ps.close();
            System.out.println("one Record Updated Successfully.....");
        } catch (Exception e) {
            System.out.println(e);
        }
        agin();
    }

    public void searchForBook() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        Connection conn = DBSource.getConnection();
        ResultSet rs = null;
        try {
            Statement st = conn.createStatement();
            System.out.println("Type in one or more keywords to search for");
            String qstr = scanner.nextLine();

            rs = st.executeQuery("SELECT ID,TITLE,AUTHOR,DESCRIPTION FROM BOOK WHERE  LOWER(TITLE) like('" + qstr + "') or LOWER(AUTHOR) like('" + qstr + "') or LOWER(DESCRIPTION) like('" + qstr + "')");
            if (rs.isBeforeFirst()) {
                List<Book> books = new ArrayList<Book>();
                Book b = null;
                System.out.println("The following books matched your query. Enter the book ID to see more details, or <Enter> to return.");
                while (rs.next()) {
                    b = new Book();
                    id = rs.getInt("ID");
                    b.setId(id);
                    title = rs.getString("TITLE");
                    b.setTitle(title);
                    author = rs.getString("AUTHOR");
                    b.setAuthor(author);
                    descr = rs.getString("DESCRIPTION");
                    b.setDescription(descr);
                    System.out.println("[" + id + "] " + title);
                    books.add(b);
                }
                System.out.print("Book ID: ");
                int bookId = scanner.nextInt();
                for (Book book : books) {
                    if (bookId == book.getId()) {
                        System.out.println("ID: " + book.getId() + "\n"
                                + "	Title: " + book.getTitle() + "\n"
                                + "	Author: " + book.getAuthor() + "\n"
                                + "	Description: " + book.getDescription());
                        break;
                    }
                }
            }
            System.out.println("there are no data matches your search criteria... ");
            st.close();
            rs.close();
            conn.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        agin();
    }

    void agin() throws ClassNotFoundException, SQLException {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        int choice = 0;
        FileManager fm = new FileManager();
        BookManager bm = new BookManager();

        System.out.println("Choose another the Cooresponding Operation ? \n"
                + "      1) View all books\n"
                + "	2) Add a book\n"
                + "	3) Edit a book\n"
                + "	4) Search for a book\n"
                + "	5) Save and exit");
        choice = scanner.nextInt();
        switch (choice) {
            case 1:
                fm.dBToFile(false);
                break;
            case 2:
                bm.addBook();
                break;
            case 3:
                bm.editBook();
                break;
            case 4:
                bm.searchForBook();
                break;
            case 5:
                fm.dBToFile(true);
                break;
            default:
                System.out.println("invalid choice :( ");
                agin();
                break;

        }
    }

}
