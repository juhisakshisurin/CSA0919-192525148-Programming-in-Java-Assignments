import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Button;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Campus Library Search Screen
 * Demonstrates nested AWT layout managers and responsive resizing.
 */
public class LibrarySearch extends Frame {
    private final TextField searchField = new TextField(24);
    private final Choice categoryChoice = new Choice();
    private final List resultsList = new List(10, false);
    private final Label statusLabel = new Label("Ready. Enter a search term or select a category.");
    private final Button searchButton = new Button("Search");
    private final Button prevButton = new Button("Prev");
    private final Button nextButton = new Button("Next");
    private final Button reserveButton = new Button("Reserve");

    private final ArrayList<Book> allBooks = new ArrayList<>();
    private ArrayList<Book> filteredBooks = new ArrayList<>();
    private int currentPage = 0;
    private static final int PAGE_SIZE = 8;

    public LibrarySearch() {
        super("Campus Library Search");

        loadSampleBooks();
        filteredBooks = new ArrayList<>(allBooks);

        // Main frame: BorderLayout lets the central results region take extra space.
        setLayout(new BorderLayout(8, 8));

        // Header and search row are grouped in a top panel.
        Panel topPanel = new Panel(new BorderLayout(4, 4));
        Label title = new Label("CAMPUS LIBRARY SEARCH", Label.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(title, BorderLayout.NORTH);

        Panel searchPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        searchPanel.add(new Label("Search:"));
        searchPanel.add(searchField);

        categoryChoice.add("All");
        categoryChoice.add("Title");
        categoryChoice.add("Author");
        categoryChoice.add("Category");
        searchPanel.add(new Label("Category:"));
        searchPanel.add(categoryChoice);
        searchPanel.add(searchButton);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        // Results occupy CENTER and expand/shrink with the window.
        add(topPanel, BorderLayout.NORTH);
        add(resultsList, BorderLayout.CENTER);

        // Bottom panel keeps navigation controls and status message together.
        Panel bottomPanel = new Panel(new BorderLayout(4, 4));
        Panel navigationPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        navigationPanel.add(prevButton);
        navigationPanel.add(nextButton);
        navigationPanel.add(reserveButton);
        bottomPanel.add(navigationPanel, BorderLayout.NORTH);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Event handlers
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 0) {
                    currentPage--;
                    showCurrentPage();
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int lastPage = Math.max(0, (filteredBooks.size() - 1) / PAGE_SIZE);
                if (currentPage < lastPage) {
                    currentPage++;
                    showCurrentPage();
                }
            }
        });

        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = resultsList.getSelectedIndex();
                if (selected < 0) {
                    statusLabel.setText("Select a book from the results before reserving.");
                    return;
                }
                int actualIndex = currentPage * PAGE_SIZE + selected;
                if (actualIndex >= 0 && actualIndex < filteredBooks.size()) {
                    Book book = filteredBooks.get(actualIndex);
                    statusLabel.setText("Reservation request recorded for: " + book.title);
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setSize(760, 500);
        setLocationRelativeTo(null);
        updateNavigationButtons();
        showCurrentPage();
    }

    private void loadSampleBooks() {
        allBooks.add(new Book("Java: The Complete Reference", "Herbert Schildt", "Programming"));
        allBooks.add(new Book("Effective Java", "Joshua Bloch", "Programming"));
        allBooks.add(new Book("Clean Code", "Robert C. Martin", "Software Engineering"));
        allBooks.add(new Book("Introduction to Algorithms", "Thomas H. Cormen", "Computer Science"));
        allBooks.add(new Book("Artificial Intelligence: A Modern Approach", "Stuart Russell", "AI"));
        allBooks.add(new Book("Deep Learning", "Ian Goodfellow", "AI"));
        allBooks.add(new Book("Database System Concepts", "Abraham Silberschatz", "Database"));
        allBooks.add(new Book("Operating System Concepts", "Abraham Silberschatz", "Operating Systems"));
        allBooks.add(new Book("Computer Networks", "Andrew S. Tanenbaum", "Networking"));
        allBooks.add(new Book("The C Programming Language", "Brian W. Kernighan", "Programming"));
        allBooks.add(new Book("Design Patterns", "Erich Gamma", "Software Engineering"));
        allBooks.add(new Book("Computer Organization", "Carl Hamacher", "Computer Science"));
        allBooks.add(new Book("Python Crash Course", "Eric Matthes", "Programming"));
        allBooks.add(new Book("Learning SQL", "Alan Beaulieu", "Database"));
        allBooks.add(new Book("Hands-On Machine Learning", "Aurélien Géron", "AI"));
        allBooks.add(new Book("Data Communications", "Behrouz Forouzan", "Networking"));
    }

    private void performSearch() {
        String query = searchField.getText().trim().toLowerCase();
        String category = categoryChoice.getSelectedItem();
        filteredBooks = new ArrayList<>();

        for (Book book : allBooks) {
            boolean matches;
            if (query.isEmpty() || "All".equals(category)) {
                matches = query.isEmpty()
                        || book.title.toLowerCase().contains(query)
                        || book.author.toLowerCase().contains(query)
                        || book.category.toLowerCase().contains(query);
            } else if ("Title".equals(category)) {
                matches = book.title.toLowerCase().contains(query);
            } else if ("Author".equals(category)) {
                matches = book.author.toLowerCase().contains(query);
            } else {
                matches = book.category.toLowerCase().contains(query);
            }

            if (matches) {
                filteredBooks.add(book);
            }
        }

        currentPage = 0;
        showCurrentPage();
        statusLabel.setText(filteredBooks.size() + " matching book(s) found.");
    }

    private void showCurrentPage() {
        resultsList.removeAll();
        int start = currentPage * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, filteredBooks.size());

        for (int i = start; i < end; i++) {
            Book book = filteredBooks.get(i);
            resultsList.add(book.title + "  |  " + book.author + "  |  " + book.category);
        }

        if (filteredBooks.isEmpty()) {
            statusLabel.setText("No matching books found.");
        } else {
            int totalPages = (filteredBooks.size() + PAGE_SIZE - 1) / PAGE_SIZE;
            statusLabel.setText("Showing " + (start + 1) + "-" + end
                    + " of " + filteredBooks.size() + " books | Page "
                    + (currentPage + 1) + " of " + totalPages);
        }
        updateNavigationButtons();
    }

    private void updateNavigationButtons() {
        prevButton.setEnabled(currentPage > 0);
        nextButton.setEnabled((currentPage + 1) * PAGE_SIZE < filteredBooks.size());
    }

    private static class Book {
        private final String title;
        private final String author;
        private final String category;

        private Book(String title, String author, String category) {
            this.title = title;
            this.author = author;
            this.category = category;
        }
    }

    public static void main(String[] args) {
        LibrarySearch window = new LibrarySearch();
        window.setVisible(true);
    }
}
