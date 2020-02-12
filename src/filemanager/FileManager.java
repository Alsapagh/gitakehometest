package filemanager;

import bookmanager.BookManager;
import gitakehometest.Book;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Alsapagh
 */
public class FileManager {

    static final String PATH = "Books.txt";
    int id;
    String title;
    String author;
    String descr;
    private List<Book> books;

    public List<Book> readFromFile() {
        List<Book> list = new ArrayList<>();
        Book b;
        try {
            BufferedReader br = new BufferedReader(new FileReader(PATH));
            String line;
            while ((line = br.readLine()) != null) {
                b = new Book();
                String tmp[] = line.split(",");
                b.setId(Integer.parseInt(tmp[0]));
                b.setTitle(tmp[1]);
                b.setAuthor(tmp[2]);
                b.setDescription(tmp[3]);
                list.add(b);
            }
        } catch (IOException e) {
        }
        this.books = list;
        return list;
    }

    public void writeToFile(boolean flag, List<Book> data) throws IOException {
        this.books = data;
        if (flag) {
            FileManager.this.writeToFile(data);
        } else {
            for (Book b : data) {
                System.out.println(b.getId() + "\t" + b.getTitle() + "\t" + b.getAuthor() + "\t" + b.getDescription());
            }
            agin();
        }

    }

    private void writeToFile(List<Book> list) throws IOException {
        BufferedWriter out = null;

        File file = new File(PATH);
        /**
         * check if file exists delete and recreate to add data from beginning
         */
        if (file.delete()) {
//            System.out.println("Books.txt File deleted from Project root directory and recreated again");
        } else {
//            System.out.println("Books file.txt doesn't exist in the project root directory and have been created");
        }

        out = new BufferedWriter(new FileWriter(file, true));
        for (Book b : list) {
            out.write(b.getId() + "," + b.getTitle() + "," + b.getAuthor() + "," + b.getDescription());
            out.newLine();
        }
        out.close();
        System.out.println("Data have been written to file " + PATH + " successfully");

    }

    void agin() throws IOException {
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
                Book b = bm.addBook(this.books.size() + 1);
                this.books.add(b);
                agin();
                break;
            case 3:
                books = bm.editBook(books);
                agin();
                break;
            case 4:
                bm.searchForBook(books);
                agin();
                break;
            case 5:
                fm.writeToFile(true, books);
                break;
            default:
                System.out.println("invalid choice :( ");
                agin();
                break;

        }
    }
}
