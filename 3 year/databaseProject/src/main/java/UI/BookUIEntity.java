package UI;

import Database.books.models.Book;
import Database.books.models.BookStatus;
import Database.files.models.BookGenre;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class BookUIEntity {
    private Integer id;
    private Integer readerId;
    private Integer bookId;
    private String name;
    private String author;
    private BookGenre genre;
    private Date takingDate;
    private Date returningDate;
    private String place;
    private BookStatus status;

    public BookUIEntity() {

    }

    public BookUIEntity(Integer readerId, String name, String author, BookGenre genre, LocalDate takingDate, LocalDate returningDate, String place, BookStatus status, Integer bookId) {
        this.setReaderId(readerId);
        if (name!=null&&!name.equals(""))
            this.setName(name);
        if (author!=null&&!author.equals(""))
            this.setAuthor(author);
        this.setGenre(genre);
        if (takingDate != null)
            this.setTakingDate(Date.valueOf(takingDate));
        if (returningDate != null)
            this.setReturningDate(Date.valueOf(returningDate));
        if (place!=null&&!place.equals(""))
            this.setPlace(place);
        this.setStatus(status);
        this.setBookId(bookId);
    }

    public static BookUIEntity getBookUI(Book book) {
        BookUIEntity bookUIEntity = new BookUIEntity();
        bookUIEntity.setId(book.getBookId());
        bookUIEntity.setStatus(book.getStatus());
        bookUIEntity.setGenre(book.getType().getGenre());
        bookUIEntity.setPlace(book.getPlace().getPlaceName());
        bookUIEntity.setName(book.getType().getBookName());
        bookUIEntity.setAuthor(book.getType().getBookAuthor());
        return bookUIEntity;
    }

    public static List<BookUIEntity> getBooksUI(List<Book> books) {
        List<BookUIEntity> bookUIEntities = new LinkedList<>();
        for (Book book : books) {
            BookUIEntity bookUIEntity = new BookUIEntity();
            bookUIEntity.setId(book.getBookId());
            bookUIEntity.setStatus(book.getStatus());
            bookUIEntity.setGenre(book.getType().getGenre());
            bookUIEntity.setPlace(book.getPlace().getPlaceName());
            bookUIEntity.setName(book.getType().getBookName());
            bookUIEntity.setAuthor(book.getType().getBookAuthor());
            bookUIEntities.add(bookUIEntity);
        }
        return bookUIEntities;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BookGenre getGenre() {
        return genre;
    }

    public void setGenre(BookGenre genre) {
        this.genre = genre;
    }

    public Date getTakingDate() {
        return takingDate;
    }

    public void setTakingDate(Date takingDate) {
        this.takingDate = takingDate;
    }

    public Date getReturningDate() {
        return returningDate;
    }

    public void setReturningDate(Date returningDate) {
        this.returningDate = returningDate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public Integer getReaderId() {
        return readerId;
    }

    public void setReaderId(Integer readerId) {
        this.readerId = readerId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
}
