package Database.books.models;

import Database.files.models.BookGenre;

public class BookType {
    private Integer bookTypeId;
    private String bookName;
    private String bookAuthor;
    private BookGenre genre;

    public BookType() {

    }

    public BookType(Integer bookTypeId, String bookName, String bookAuthor, BookGenre genre) {
            setBookTypeId(bookTypeId);
        if (bookName != null && !bookName.equals(""))
            setBookName(bookName);
        if (bookAuthor != null && !bookAuthor.equals(""))
            setBookAuthor(bookAuthor);
        setGenre(genre);
    }

    public Integer getBookTypeId() {
        return bookTypeId;
    }

    public void setBookTypeId(Integer bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    public String getBookName() {
            return bookName;
    }

    public void setBookName(String bookName) {
        if (bookName != null && !bookName.equals(""))

            this.bookName = bookName;
    }

    public String getBookAuthor() {

            return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        if (bookAuthor != null && !bookAuthor.equals(""))

            this.bookAuthor = bookAuthor;
    }

    public BookGenre getGenre() {
        return genre;
    }

    public void setGenre(BookGenre genre) {
        this.genre = genre;
    }
}
