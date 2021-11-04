package Database.books.requests;

import Database.books.models.Book;
import Database.books.models.BookStatus;
import Database.books.models.BookType;
import Database.files.models.BookGenre;
import Database.places.models.Place;
import Database.places.models.PlaceType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BookRequests {
    Connection conn;

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Integer addBook(Book book) {
        int bookId = getNextBookId();
        book.setBookId(bookId);
        String sqlAddBook = "INSERT INTO libraryAdmin.books_table VALUES (" +
                bookId + ", " +
                book.getType().getBookTypeId() + "," +
                book.getPlace().getPlaceId() + "," +
                "'" + book.getStatus() + "' )";


        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlAddBook);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't add new book");
            throwables.printStackTrace();
        }
        return bookId;

    }

    public void deleteBook(Integer bookId) {
        String sqlDeleteBook = "DELETE FROM libraryAdmin.books_table WHERE book_id = " + bookId;
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlDeleteBook);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't delete book");
            throwables.printStackTrace();

        }

    }

    public void updateBook(Book book) {
        String sqlUpdateBook = "UPDATE libraryAdmin.books_table SET ";
        sqlUpdateBook += "book_type_id = " + book.getType().getBookTypeId();
        if(book.getPlace()!=null&&book.getPlace().getPlaceId()!=null)
        sqlUpdateBook += " ,book_place_id = " + book.getPlace().getPlaceId();
        if(book.getStatus()!=null)
            sqlUpdateBook += " ,BOOK_STATUS = '" + book.getStatus()+"' ";

        sqlUpdateBook += " WHERE book_id = " + book.getBookId();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlUpdateBook);
            preStatementReader.executeUpdate();
        } catch (SQLException throwables) {
            System.err.println("can't update book");
            throwables.printStackTrace();
        }
    }


    public Book getBook(Integer bookId) {
        String sqlGetBook = "SELECT bt.book_id,bt.book_status, " +
                "btt.book_name, btt.book_author, btt.book_genre,btt.book_type_id, " +
                "pt.place_type, pt.place_address " +
                "from libraryAdmin.books_table bt " +
                "INNER JOIN libraryAdmin.book_types_table btt " +
                "ON bt.book_type_id = btt.book_type_id " +
                "INNER JOIN libraryAdmin.places_table pt " +
                "ON bt.book_place_id = pt.place_id " +
                "WHERE book_id = " + bookId;
        Book book = null;

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetBook);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                book = new Book();
                book.setBookId(rs.getInt("book_id"));
                Place place = new Place();
                place.setPlaceAddress(rs.getString("place_address"));
                place.setPlaceType(PlaceType.valueOf(rs.getString("place_type")));
                BookType type = new BookType();
                type.setBookTypeId(rs.getInt("book_type_id"));
                type.setBookAuthor(rs.getString("book_author"));
                type.setBookName(rs.getString("book_name"));
                type.setGenre(BookGenre.valueOf(rs.getString("book_genre")));
                book.setType(type);
                book.setPlace(place);
                book.setStatus(BookStatus.valueOf(rs.getString("book_status")));
            }
        } catch (SQLException throwables) {
            System.err.println("can't get book");
            throwables.printStackTrace();
        }
        return book;
    }

    public List<Book> getBooksWithFilter(BookStatus status, BookType type, Place place) {
        String sqlGetBooks = "SELECT bt.book_id,bt.book_status, " +
                "btt.book_type_id, btt.book_name, btt.book_author, btt.book_genre, " +
                "pt.place_id,pt.place_name,pt.place_type, pt.place_address " +
                "from libraryAdmin.books_table bt " +
                "INNER JOIN libraryAdmin.book_types_table btt " +
                "ON bt.book_type_id = btt.book_type_id " +
                "INNER JOIN libraryAdmin.places_table pt " +
                "ON bt.book_place_id = pt.place_id ";

        if (status != null)
            sqlGetBooks += "WHERE book_status = '" + status + "' ";
        if (type != null)
            sqlGetBooks += "WHERE book_type_id = " + type.getBookTypeId() + " ";
        if (type.getGenre() != null)
            sqlGetBooks += "WHERE book_genre = '" + type.getGenre() + "' ";
        if (type.getBookAuthor() != null)
            sqlGetBooks += "WHERE book_author = '" + type.getBookAuthor() + "' ";
        if (type.getBookName() != null)
            sqlGetBooks += "WHERE book_name = '" + type.getBookName() + "' ";
        if (place != null)
            sqlGetBooks += "WHERE book_place_id = " + place.getPlaceId() + " ";
        List<Book> books = new LinkedList<>();
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetBooks);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                Place place1 = new Place();
                book.setBookId(rs.getInt("book_id"));
                place1.setPlaceAddress(rs.getString("place_address"));
                place1.setPlaceName(rs.getString("place_name"));
                place1.setPlaceType(PlaceType.valueOf(rs.getString("place_type")));
                BookType type1 = new BookType();
                type1.setBookAuthor(rs.getString("book_author"));
                type1.setBookName(rs.getString("book_name"));
                type1.setGenre(BookGenre.valueOf(rs.getString("book_genre")));
                book.setType(type1);
                book.setPlace(place1);
                book.setStatus(BookStatus.valueOf(rs.getString("book_status")));
                books.add(book);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get books");
            throwables.printStackTrace();
        }
        return books;
    }

    public List<Book> getBooks() {
        String sqlGetBooks = "SELECT bt.book_id,bt.book_status, " +
                "btt.book_type_id, btt.book_name, btt.book_author, btt.book_genre, " +
                "pt.place_id,pt.place_name,pt.place_type, pt.place_address " +
                "from libraryAdmin.books_table bt " +
                "INNER JOIN libraryAdmin.book_types_table btt " +
                "ON bt.book_type_id = btt.book_type_id " +
                "INNER JOIN libraryAdmin.places_table pt " +
                "ON bt.book_place_id = pt.place_id ";

        List<Book> books = new LinkedList<>();
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetBooks);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                Place place1 = new Place();
                book.setBookId(rs.getInt("book_id"));
                place1.setPlaceAddress(rs.getString("place_address"));
                place1.setPlaceName(rs.getString("place_name"));
                place1.setPlaceType(PlaceType.valueOf(rs.getString("place_type")));
                BookType type1 = new BookType();
                type1.setBookAuthor(rs.getString("book_author"));
                type1.setBookName(rs.getString("book_name"));
                type1.setGenre(BookGenre.valueOf(rs.getString("book_genre")));
                book.setType(type1);
                book.setPlace(place1);
                book.setStatus(BookStatus.valueOf(rs.getString("book_status")));
                books.add(book);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get books");
            throwables.printStackTrace();
        }
        return books;


    }

    public List<Book> getBooksWithFilter(Book filter) {
        String sqlGetBooks = "SELECT bt.book_id,bt.book_status, " +
                "btt.book_type_id, btt.book_name, btt.book_author, btt.book_genre, " +
                "pt.place_id,pt.place_name,pt.place_type, pt.place_address " +
                "from libraryAdmin.books_table bt " +
                "INNER JOIN libraryAdmin.book_types_table btt " +
                "ON bt.book_type_id = btt.book_type_id " +
                "INNER JOIN libraryAdmin.places_table pt " +
                "ON bt.book_place_id = pt.place_id ";

        String sqlFilter = "";
        if (filter.getStatus() != null) {
            sqlFilter += " AND book_status = '" + filter.getStatus().name() + "'";
        }

        if (filter.getPlace() != null) {
            if (filter.getPlace().getPlaceType() != null)
                sqlFilter += " AND place_type = '" + filter.getPlace().getPlaceType().name() + "'";
            if (filter.getPlace().getPlaceName() != null)
                sqlFilter += " AND place_name = '" + filter.getPlace().getPlaceName() + "'";

        }
        if (filter.getType() != null) {
            if (filter.getType().getBookName() != null)
                sqlFilter += " AND book_name = '" + filter.getType().getBookName() + "'";
            if (filter.getType().getBookAuthor() != null)
                sqlFilter += " AND book_author = '" + filter.getType().getBookAuthor() + "'";
            if (filter.getType().getGenre() != null)
                sqlFilter += " AND book_genre = '" + filter.getType().getGenre().name() + "'";


        }

        if (sqlFilter.contains("AND")) {
            sqlFilter = sqlFilter.replaceFirst("AND", "WHERE");
            sqlGetBooks += sqlFilter;

        }
        List<Book> books = new LinkedList<>();
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetBooks);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                Place place1 = new Place();
                book.setBookId(rs.getInt("book_id"));
                place1.setPlaceAddress(rs.getString("place_address"));
                place1.setPlaceName(rs.getString("place_name"));
                place1.setPlaceType(PlaceType.valueOf(rs.getString("place_type")));
                BookType type1 = new BookType();
                type1.setBookAuthor(rs.getString("book_author"));
                type1.setBookName(rs.getString("book_name"));
                type1.setGenre(BookGenre.valueOf(rs.getString("book_genre")));
                book.setType(type1);
                book.setPlace(place1);
                book.setStatus(BookStatus.valueOf(rs.getString("book_status")));
                books.add(book);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get books");
            throwables.printStackTrace();
        }
        return books;


    }


    public BookStatus getStatusByBookId(Integer bookId) {
        String sqlGetBook = "SELECT bt.book_status " +
                "from libraryAdmin.books_table bt " +
                "WHERE book_id = " + bookId;
        BookStatus status = null;

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetBook);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                status = BookStatus.valueOf(rs.getString("book_status"));
            }
        } catch (SQLException throwables) {
            System.err.println("can't get book");
            throwables.printStackTrace();
        }
        return status;
    }

    public Integer addBookType(BookType bookType) {
        int bookTypeId = getNextBookTypeId();
        bookType.setBookTypeId(bookTypeId);
        String sqlAddBookType = "INSERT INTO libraryAdmin.book_types_table VALUES (" +
                bookTypeId + ", " +
                "'" + bookType.getBookName() + "' ," +
                "'" + bookType.getBookAuthor() + "' ," +
                "'" + bookType.getGenre() + "' )";


        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlAddBookType);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't add new book type");
            throwables.printStackTrace();
        }
        return bookTypeId;

    }

    public void deleteBookType(Integer bookTypeId) {
        String sqlDeleteBookType = "DELETE FROM libraryAdmin.book_types_table WHERE book_type_id = " + bookTypeId;
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlDeleteBookType);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't delete book type");
            throwables.printStackTrace();

        }

    }

    public void updateBookType(BookType bookType) {
        String sqlUpdateBookType = "UPDATE libraryAdmin.book_types_table SET ";
        sqlUpdateBookType += "book_type_id = " + bookType.getBookTypeId();
        sqlUpdateBookType += ",book_name = '" + bookType.getBookName() + "' ";
        sqlUpdateBookType += ",book_author = '" + bookType.getBookAuthor() + "' ";
        sqlUpdateBookType += ",book_genre = '" + bookType.getGenre() + "' ";

        sqlUpdateBookType += "WHERE book_type_id = " + bookType.getBookTypeId();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlUpdateBookType);
            preStatementReader.executeUpdate();
        } catch (SQLException throwables) {
            System.err.println("can't update book type");
            throwables.printStackTrace();
        }
    }

    public BookType getBookType(Integer bookTypeId) {
        String sqlGetBookType = "SELECT * from libraryAdmin.book_types_table " +
                "WHERE book_type_id = " + bookTypeId;
        BookType bookType = new BookType();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetBookType);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                bookType.setBookTypeId(rs.getInt("book_type_id"));
                bookType.setBookName("book_name");
                bookType.setBookAuthor("book_author");
                bookType.setGenre(BookGenre.valueOf("book_genre"));
            }
        } catch (SQLException throwables) {
            System.err.println("can't get book type");
            throwables.printStackTrace();
        }
        return bookType;
    }

    public BookType getBookTypeByName(String name) {
        String sqlGetBookType = "SELECT * from libraryAdmin.book_types_table " +
                "WHERE book_name = '" + name + "' ";
        BookType bookType = new BookType();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetBookType);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                bookType.setBookTypeId(rs.getInt("book_type_id"));
                bookType.setBookName(rs.getString("book_name"));
                bookType.setBookAuthor(rs.getString("book_author"));
                bookType.setGenre(BookGenre.valueOf(rs.getString("book_genre")));
            }
        } catch (SQLException throwables) {
            System.err.println("can't get book type");
            throwables.printStackTrace();
        }
        return bookType;
    }

    public List<BookType> getBookTypes() {
        String sqlGetBookTypes = "SELECT * from libraryAdmin.book_types_table ";
        List<BookType> bookTypes = new LinkedList<>();
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetBookTypes);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                BookType type = new BookType();
                type.setBookTypeId(rs.getInt("book_type_id"));
                type.setGenre(BookGenre.valueOf(rs.getString("book_genre")));
                type.setBookName(rs.getString("book_name"));
                type.setBookAuthor(rs.getString("book_author"));
                bookTypes.add(type);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get books");
            throwables.printStackTrace();
        }
        return bookTypes;
    }

    public Integer getNextBookId() {
        String sql = "select sq_book.nextval from DUAL";
        Integer nextID = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                nextID = rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return nextID;
    }

    public Integer getNextBookTypeId() {
        String sql = "select sq_book_type.nextval from DUAL";
        Integer nextID = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                nextID = rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return nextID;
    }
}

