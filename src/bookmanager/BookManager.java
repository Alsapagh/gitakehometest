package bookmanager;

import gitakehometest.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Alsapagh
 */
public class BookManager {

    public Book addBook(int maxId) {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        System.out.println("please inter the Book Title ");
        String title = scanner.nextLine();
        System.out.println("please inter the Book Author ");
        String author = scanner.nextLine();
        System.out.println("please inter the Book Description ");
        String descr = scanner.nextLine();
        return new Book(maxId, title, author, descr);
    }

    public List<Book> editBook(List<Book> data) {
        List<Book> books = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object        
        //first of all display data to choose 
        for (Book b : data) {
            System.out.println(b.getId() + "\t" + b.getTitle() + "\t" + b.getAuthor() + "\t" + b.getDescription());
        }
        System.out.println("Enter the book ID of the book you want to edit");
        System.out.print("Book ID: ");
        int editId = scanner.nextInt();
        int id;
        String title, author, descr;
        for (Book b : data) {
            if (editId == b.getId()) {
                Book updatedBook = new Book();
                updatedBook.setId(editId);
                title = scanner.nextLine();
                System.out.println("Updating with this ID " + b.getId() + "");
                System.out.print("Title [" + b.getTitle() + "]: \t");
                title = scanner.nextLine();
                title = title == "" ? b.getTitle() : title;
                updatedBook.setTitle(title);
                System.out.print("Author [" + b.getAuthor() + "]: \t");
                author = scanner.nextLine();
                author = author == "" ? b.getAuthor() : author;
                updatedBook.setAuthor(author);
                System.out.print("Description: [" + b.getDescription() + "]: \t");
                descr = scanner.nextLine();
                descr = descr == "" ? b.getDescription() : descr;
                updatedBook.setDescription(descr);
                books.add(updatedBook);
            } else {
                books.add(b);
            }
        }
        return books;
    }

    public void searchForBook(List<Book> data) {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        List<Book> cretria = null;

        System.out.println("Type in one or more keywords to search for");
        String qstr = scanner.nextLine().toLowerCase();
        for (Book b : data) {
            if (b.getTitle().toLowerCase().contains(qstr) || b.getAuthor().toLowerCase().contains(qstr) || b.getDescription().toLowerCase().contains(qstr)) {
                cretria = new ArrayList<>();
                cretria.add(b);
            }
        }
        if (cretria.isEmpty()) {
            System.out.println("there are no data matches your search criteria... ");
        } else {
            System.out.println("The following books matched your query. Enter the book ID to see more details, or <Enter> to return.");
            for (Book b : cretria) {
                System.out.println("[" + b.getId() + "] " + b.getTitle());
            }
            System.out.print("Book ID: ");
            int bookId = scanner.nextInt();
            for (Book book : cretria) {
                if (bookId == book.getId()) {
                    System.out.println("ID: " + book.getId() + "\n"
                            + "	Title: " + book.getTitle() + "\n"
                            + "	Author: " + book.getAuthor() + "\n"
                            + "	Description: " + book.getDescription());
                    break;
                }
            }
        }
    }
}
