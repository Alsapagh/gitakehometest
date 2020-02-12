package gitakehometest;

import bookmanager.BookManager;
import filemanager.FileManager;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Alsapagh
 */
public class GITakeHomeTest {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    static List<Book> books;

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        int choice;
        FileManager fm = new FileManager();
        BookManager bm = new BookManager();
        //on start load Data from Disk 
        books = fm.readFromFile();
        cycle();
    }

    static void cycle() throws IOException {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        int choice;
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
                fm.writeToFile(false, books);
                break;
            case 2:
                Book b = bm.addBook(GITakeHomeTest.books.size() + 1);
                GITakeHomeTest.books.add(b);
                cycle();
                break;
            case 3:
                books = bm.editBook(books);
                cycle();
                break;
            case 4:
                bm.searchForBook(books);
                cycle();
                break;
            case 5:
                fm.writeToFile(true, books);
                break;
            default:
                System.out.println("invalid choice :( ");
                break;

        }
    }
}
