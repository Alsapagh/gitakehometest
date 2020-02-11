/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gitakehometest;

import bookmanager.BookManager;
import filemanager.FileManager;
import java.awt.Choice;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
public class GITakeHomeTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        int choice = 0;
        FileManager fm = new FileManager();
        BookManager bm = new BookManager();
        //on start inser ll data from the file too DB to manipulate 
        List<Book> books = fm.fileToDB();

        System.out.println("Choose the Cooresponding Operation..... \n"
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
                break;

        }

    }

}
