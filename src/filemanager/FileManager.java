/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import bookmanager.BookManager;
import dbSource.DBSource;
import gitakehometest.Book;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
public class FileManager {

    static final String path = "Books.txt";
    int id;
    String title;
    String author;
    String descr;

    public void dBToFile(boolean flag) throws ClassNotFoundException, SQLException {

        Connection conn = DBSource.getConnection();
        List<Book> data = new ArrayList<Book>();
        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT ID,TITLE,AUTHOR,DESCRIPTION FROM BOOK");
            Book b = null;
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
                data.add(b);
                if (!flag) {
                    System.out.println(id + "\t" + title + "\t" + author + "\t" + descr);
                }
            }
            if (flag) {
                writeToFile(data);
            }

            rs.close();
            conn.close();
            st.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        if (!flag) {
            agin();
        }
    }

    private void writeToFile(java.util.List<Book> list) throws IOException {
        BufferedWriter out = null;

        File file = new File(path);
        /**
         * check if file exists delete and recreate to add data from beginning
         */
        if (file.delete()) {
//            System.out.println("file.txt File deleted from Project root directory and recreated again");
        } else {
            System.out.println("File file.txt doesn't exist in the project root directory and have been created");
        }

        out = new BufferedWriter(new FileWriter(file, true));
        for (Book b : list) {
            out.write(b.getId() + "," + b.getTitle() + "," + b.getAuthor() + "," + b.getDescription());
            out.newLine();
        }
        out.close();
        System.out.println("Data have been written to file " + path + " successfully");

    }

    public List<Book> fileToDB() throws ClassNotFoundException, SQLException {

        PreparedStatement ps = null;
        Connection conn = DBSource.getConnection();
        ResultSet rs = null;
        List<Book> list = new ArrayList<Book>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            String deleteSql = "DELETE FROM BOOK";
            ps = conn.prepareStatement(deleteSql);
            ps.executeUpdate();
            String line = null;
            while ((line = br.readLine()) != null) {
                String tmp[] = line.split(",");
                id = Integer.parseInt(tmp[0]);
                title = tmp[1];
                author = tmp[2];
                descr = tmp[3];
//                System.out.println(id + "\t" + title + "\t" + author + "\t" + descr);
                String sql
                        = "INSERT INTO BOOK (ID,TITLE,AUTHOR,DESCRIPTION)values ("
                        + id + ",'" + title + "','" + author + "','" + descr + "')";

                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                list.add(new Book(id, title, author, descr));

            }

            br.close();
            conn.close();
            ps.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
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
