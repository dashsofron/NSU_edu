package Database;

import Database.books.models.Book;
import Database.books.models.BookStatus;
import Database.books.models.BookType;
import Database.books.requests.BookRequests;
import Database.files.models.BookGenre;
import Database.files.models.BookTakingRecord;
import Database.files.models.Punishment;
import Database.files.requests.PunishmentRequests;
import Database.files.requests.RecordRequests;
import Database.places.models.Place;
import Database.places.models.PlaceType;
import Database.places.requests.PlaceRequests;
import Database.readers.models.ReaderEntity;
import Database.readers.models.ReaderType;
import Database.readers.models.StudentEntity;
import Database.readers.models.TeacherEntity;
import Database.readers.requests.ReaderRequests;
import Database.readers.requests.StudentRequests;
import Database.readers.requests.TeacherRequests;
import Database.search.DateSearch;
import Database.user.User;
import Database.user.UserRequests;
import UI.BookUIEntity;
import UI.ReaderUiEntity;
import UI.StudentUiEntity;
import UI.TeacherUiEntity;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class DBRequests {
    static ReaderRequests readerRequests;
    static StudentRequests studentRequests;
    static TeacherRequests teacherRequests;
    static BookRequests bookRequests;
    static PlaceRequests placeRequests;
    static RecordRequests recordRequests;
    static PunishmentRequests punishmentRequests;
    static UserRequests userRequests;

    private static DBRequests instance;
    static private Connection conn;

    private DBRequests() {

    }

    public static synchronized DBRequests getInstance() {
        if (instance == null) {
            instance = new DBRequests();
            DBRequests.conn = DBInit.getConn();
            readerRequests = new ReaderRequests();
            readerRequests.setConn(conn);
            studentRequests = new StudentRequests();
            studentRequests.setConn(conn);
            teacherRequests = new TeacherRequests();
            teacherRequests.setConn(conn);
            bookRequests = new BookRequests();
            bookRequests.setConn(conn);
            placeRequests = new PlaceRequests();
            placeRequests.setConn(conn);
            recordRequests = new RecordRequests();
            recordRequests.setConn(conn);
            punishmentRequests = new PunishmentRequests();
            punishmentRequests.setConn(conn);
            userRequests = new UserRequests();
            userRequests.setConn(conn);
        }
        return instance;
    }

    public  void setCon (){
        Connection conn=DBInit.getConn();
        DBRequests.conn = conn;
        readerRequests.setConn(conn);
        studentRequests.setConn(conn);
        teacherRequests.setConn(conn);
        bookRequests.setConn(conn);
        placeRequests.setConn(conn);
        recordRequests.setConn(conn);
        punishmentRequests.setConn(conn);
        userRequests.setConn(conn);

    }

    //*****FILTER

    public List<Place> getPlacesWithFilter(Place filter){
        return placeRequests.getPlacesWithFilter(filter);
    }
    public List<Punishment> getPunishmentsWithFilter(Punishment punishmentFilter, DateSearch dateTake,DateSearch dateRet){
        return punishmentRequests.getPunishmentsWithFilter(punishmentFilter,dateTake,dateRet);
    }
    public List<BookUIEntity> getBooksWithFilter(BookUIEntity filter,DateSearch dateTake,DateSearch dateRet){
        return recordRequests.getBooksWithFilter( filter, dateTake,dateRet);
    }
    public List<Book> getBooksWithFilter(Book filter) {
        return bookRequests.getBooksWithFilter(filter);
    }

    public List<ReaderUiEntity> getReadersWithFilter(ReaderUiEntity filter, DateSearch dateTake,DateSearch dateRet) {
        return readerRequests.getReadersWithFilter(filter, dateTake,dateRet);
    }
    public List<StudentUiEntity> getStudentsWithFilter(StudentUiEntity filter, DateSearch dateTake, DateSearch dateRet) {
        return studentRequests.getStudentsWithFilter(filter, dateTake,dateRet);
    }
    public List<TeacherUiEntity> getTeachersWithFilter(TeacherUiEntity filter, DateSearch dateTake, DateSearch dateRet) {
        return teacherRequests.getTeachersWithFilter(filter, dateTake,dateRet);
    }


    public Integer addReader(ReaderEntity reader) throws SQLException {
        Integer id = null;
        try {
            conn.setAutoCommit(false);

            id = readerRequests.addReader(reader);
            switch (reader.getReaderType()) {
                case СТУДЕНТ:
                    studentRequests.addStudent((StudentEntity) reader);
                    break;
                case УЧИТЕЛЬ:
                    teacherRequests.addTeacher((TeacherEntity) reader);
                    break;
                case ЧИТАТЕЛЬ:
                default:
                    System.err.println("no such type of reader is available: " + reader.getReaderType());
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException throwables) {
            conn.rollback();
            conn.setAutoCommit(true);
            throwables.printStackTrace();
        }
        return id;
    }

    public void deleteReader(Integer readerId) throws SQLException {
        try {
            conn.setAutoCommit(false);


            ReaderType type = readerRequests.getReaderType(readerId);
            switch (type) {
                case СТУДЕНТ:
                    studentRequests.deleteStudent(readerId);
                    break;
                case УЧИТЕЛЬ:
                    teacherRequests.deleteTeacher(readerId);
                    break;
                case ЧИТАТЕЛЬ:
                default:
                    System.err.println("no such type of reader is available: " + type);
            }
            readerRequests.deleteReader(readerId);
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException throwables) {
            conn.rollback();
            conn.setAutoCommit(true);
            throwables.printStackTrace();
        }

    }

    public ReaderEntity getReader(Integer readerId) {

        ReaderType type = readerRequests.getReaderType(readerId);
        if (type == null) {
            return null;
        }
        switch (type) {
            case СТУДЕНТ:
                return studentRequests.getStudent(readerId);
            case УЧИТЕЛЬ:
                return teacherRequests.getTeacher(readerId);
            default:
                System.err.println("no such type of reader is available: " + type);
                return null;

        }
    }

    public void updateReader(ReaderEntity reader) throws SQLException {
        try {
            conn.setAutoCommit(false);
            readerRequests.updateReader(reader);
            switch (reader.getReaderType()) {
                case СТУДЕНТ:
                    studentRequests.updateStudent((StudentEntity) reader);
                    break;
                case УЧИТЕЛЬ:
                    teacherRequests.updateTeacher((TeacherEntity) reader);
                    break;
                default:
                    System.err.println("no such type of reader is available: " + reader.getReaderType());
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException throwables) {
            conn.rollback();
            conn.setAutoCommit(true);
            throwables.printStackTrace();
        }
    }

    public List<ReaderEntity> getReaders() {
        return readerRequests.getReaders();
    }

//    public List<ReaderEntity> getReaders(ReaderType type) {
//
//        String sqlGetReaders = "SELECT  rt.READER_ID,rt.READER_NAME,rt.READER_LAST_NAME,rt.READER_FATHER_NAME, " +
//                "rt.READER_TYPE,rt.REGISTRATION_DATE,rt.leaving_date date,rt.ticket_number";
//        //List<ReaderEntity> readers = new LinkedList<>();
//        switch (type) {
//            case СТУДЕНТ: {
//                return studentRequests.getStudents();
//            }
//            case УЧИТЕЛЬ:
//                return teacherRequests.getTeachers();
//            case ЧИТАТЕЛЬ:
//                return readerRequests.getReaders();
//            default:
//                System.err.println("no such type is available");
//                return null;
//        }
//
//
//    }

    public Integer addUser(User user) {
        return userRequests.addUser(user);
    }

    public Integer createUser(User user) throws SQLException {
        return userRequests.createNewUser(user);
    }
//    public void deleteUser(Integer userId) {
//        userRequests.deleteUser(userId);
//    }

    public void updateUser(User user) {
        userRequests.updateUser(user);
    }

//    public User getUser(Integer userId) {
//        return userRequests.getUser(userId);
//    }

    public User getUserByName(String username) {
        return userRequests.getUserByName(username);
    }

    public Integer addPlace(Place place) {
        return placeRequests.addPlace(place);
    }

    public void deletePlace(Integer placeId) {
        placeRequests.deletePlace(placeId);
    }

    public void updatePlace(Place place) {
        placeRequests.updatePlace(place);
    }

    public Place getPlace(Integer placeId) {
        return placeRequests.getPlace(placeId);
    }

    public List<Place> getPlaces(PlaceType type) {
        return placeRequests.getPlaces(type);
    }

    public List<Place> getPlaces() {
        return placeRequests.getPlaces(null);
    }

    public Integer addBook(Book book) {
        return bookRequests.addBook(book);
    }

    public void deleteBook(Integer bookId) {
        bookRequests.deleteBook(bookId);
    }

    public void updateBook(Book book) {
        bookRequests.updateBook(book);
    }

    //TODO сделать нормальную смену статуса для книги и записи ее взятия
    public void updateBookStatus(Book book,BookTakingRecord record,Boolean newB) throws SQLException {
        //conn.setAutoCommit(false);
        bookRequests.updateBook(book);

//        if(newB){
//            recordRequests.addRecord(record);
//        }
//        else
//        recordRequests.updateRecord(record);
//        conn.setAutoCommit(true);
    }

    public void updateBookStatus(Integer bookId, BookStatus newStatus,Integer readerId) throws SQLException {
        Book book=bookRequests.getBook(bookId);
        BookTakingRecord record=recordRequests.getRecordByBookIdAndStatus(bookId,book.getStatus());
        conn.setAutoCommit(false);

        try {
            if (record == null) {
                BookTakingRecord recordNew = new BookTakingRecord();
                recordNew.setBookId(bookId);
                recordNew.setReaderId(readerId);
                recordNew.setStatus(newStatus);
                addRecord(recordNew);
                conn.commit();
            } else {
                record.setStatus(newStatus);
                recordRequests.updateRecord(record);
                conn.commit();
            }
        }
        catch (Exception e){
            conn.rollback();
            conn.setAutoCommit(true);
            throw e;
        }

        try {
            if (book.getStatus().equals(BookStatus.ВЗЯТО) && newStatus.equals(BookStatus.ВОЗВРАЩЕНО))
                book.setStatus(BookStatus.ДОСТУПНО);
            else book.setStatus(newStatus);
            bookRequests.updateBook(book);
            conn.commit();
        }
        catch (Exception e){
            conn.rollback();
            conn.setAutoCommit(true);
            e.printStackTrace();
            throw e;

        }

        conn.setAutoCommit(true);
    }

        public Book getBook(Integer bookId) {
        return bookRequests.getBook(bookId);
    }

    public List<Book> getBooks(BookStatus status, BookType type, BookGenre genre, Place place) {
        return bookRequests.getBooksWithFilter(status, type, place);
    }

    public List<Book> getBooks() {
        return bookRequests.getBooks();
    }

    public BookType getBookTypeByName(String name) {
        return bookRequests.getBookTypeByName(name);
    }

    public Place getPlaceByName(String name) {
        return placeRequests.getPlaceByName(name);
    }

    public Integer addBookType(BookType bookType) {
        return bookRequests.addBookType(bookType);
    }

//    public void deleteBookType(Integer bookTypeId) {
//        bookRequests.deleteBook(bookTypeId);
//    }

//    public void updateBookType(BookType bookType) {
//        bookRequests.updateBookType(bookType);
//    }

//    public BookType getBookType(Integer bookTypeId) {
//        return bookRequests.getBookType(bookTypeId);
//    }

//    public List<BookType> getBookTypes(BookStatus status, BookType type, BookGenre genre, Place place) {
//        return bookRequests.getBookTypes();
//    }

    public List<BookType> getBookTypes() {
        return bookRequests.getBookTypes();
    }

    public Integer addPunishment(Punishment punishment) {
        return punishmentRequests.addPunishment(punishment);
    }

    public void deletePunishment(Integer punishmentId) {
        punishmentRequests.deletePunishment(punishmentId);
    }

    public void updatePunishment(Punishment punishment) {
        punishmentRequests.updatePunishment(punishment);
    }

    public Punishment getPunishment(Integer punishmentId) {
        return punishmentRequests.getPunishment(punishmentId);
    }

    public List<Punishment> getPunishments() {
        return punishmentRequests.getPunishments();
    }

    public List<Punishment> getPunishments(Integer readerId) {
        return punishmentRequests.getPunishments(readerId);
    }


    public Integer addRecord(BookTakingRecord record) {
        return recordRequests.addRecord(record);
    }


    public void deleteRecord(Integer recordId) {
        recordRequests.deleteRecord(recordId);
    }

    public void updateRecord(BookTakingRecord record) {
        recordRequests.updateRecord(record);
    }

    public BookTakingRecord getRecord(Integer recordId) {
        return recordRequests.getRecord(recordId);
    }

    public BookTakingRecord getRecordByBookId(Integer bookId) {
        return recordRequests.getRecordByBookId(bookId);
    }

    public BookTakingRecord getRecordByBookIdAndStatus(Integer bookId,BookStatus status) {
        return recordRequests.getRecordByBookIdAndStatus(bookId,status);
    }

    public BookUIEntity getBookTakenRecord(Integer recordId) {
        return recordRequests.getBookTakenRecord(recordId);
    }

    public List<BookTakingRecord> getRecords() {
        return recordRequests.getRecords();
    }

//    public BookStatus getStatusByBookId(Integer bookId) {
//        return bookRequests.getStatusByBookId(bookId);
//
//    }

//    public List<BookUIEntity> getBookRecords() {
//        return recordRequests.getBookRecords();
//    }

    public List<BookUIEntity> getTakenBooks(Integer readerId) {
        return recordRequests.getTakenBookRecords(readerId);

    }

    public List<BookUIEntity> getOrderedBooks(Integer readerId) {
        return recordRequests.getOrderedBookRecords(readerId);
    }

    public List<ReaderEntity> getReaders(Place place, ReaderEntity reader, Boolean debtor, Punishment punishment, Boolean left) {
        String sqlGet = "SELECT tbt.record_id,tbt.reader_id, tbt.book_id,tbt.taking_date, " +
                "tbt.returning_date,tbt.record_status, tbt.READER_ID , " +
                "bt.book_status,bt.book_type_id,bt.book_place_id, " +
                "btt.book_name, btt.book_author, btt.book_genre, " +
                "pt.place_name, " +
                "rt.reader_name, rt.reader_last_name, rt.reader_father_name,rt.reader_type, rt.leaving_date, " +
                "punt.punishment_type,punt.punishment_start_date,punt.punishment_end_date,punt.punishment_payment, " +
                "punt.punishment_status,punt.punishment_comment " +
                "from libraryAdmin.taken_books_table tbt " +

                "INNER JOIN libraryAdmin.books_table bt " +
                "ON tbt.book_id = bt.book_id " +

                "INNER JOIN libraryAdmin.book_types_table btt " +
                "ON bt.book_type_id = btt.book_type_id " +

                "INNER JOIN libraryAdmin.places_table pt " +
                "ON bt.book_place_id = pt.place_id " +

                "INNER JOIN libraryAdmin.readers_table rt " +
                "ON tbt.reader_id=rt.reader_id " +

                "INNER JOIN libraryAdmin.punishments_table punt " +
                "ON tbt.reader_id=punt.reader_id ";


        String sqlSearch = "";
        if (place != null)
            sqlSearch += " AND place_name='" + place.getPlaceName() + "' ";
        if (left != null && left) sqlSearch += "AND leaving_date!=" + null;
        if (reader != null) {
            if (reader.getReaderType() != null)
                sqlSearch += " AND reader_type='" + reader.getReaderType() + "' ";
            if (reader.getReaderName() != null)
                sqlSearch += " AND reader_name='" + reader.getReaderName() + "' ";
            if (reader.getReaderLastName() != null)
                sqlSearch += " AND reader_last_name='" + reader.getReaderLastName() + "' ";
        }
        if (punishment != null) {
            if (punishment.getStatus() != null)
                sqlSearch += " AND punishment_status='" + punishment.getStatus() + "' ";
            if (punishment.getPunishmentType() != null)
                sqlSearch += " AND punishment_type='" + punishment.getPunishmentType() + "'";
        }
        if (sqlSearch.contains("AND")) {
            sqlSearch = sqlSearch.replaceFirst("AND", "WHERE");
            sqlGet += sqlSearch;

        }
        List<ReaderEntity> readers = new LinkedList<>();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlSearch);
            ResultSet rs = preStatementReader.executeQuery(sqlSearch);
            while (rs.next()) {
                ReaderEntity reader1  = new ReaderEntity();
                reader1.setReaderId(rs.getInt("reader_id"));
                reader1.setReaderName(rs.getString("reader_name"));
                reader1.setReaderLastName(rs.getString("reader_last_name"));
                reader1.setFatherName(rs.getString("reader_father_name"));
                reader1.setReaderType(ReaderType.valueOf(rs.getString("reader_type")));
                reader1.setRegistrationDate(rs.getDate("registration_date"));
                Date leavingDate = rs.getDate("LEAVING_DATE");
                if (leavingDate != null)
                    reader1.setLeavingDate(leavingDate);
                reader1.setTicketNumber(rs.getInt("ticket_number"));
                readers.add(reader1);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get readers");
            throwables.printStackTrace();

        }

        return readers;
    }

    public  List<User> getUsers(){
        return userRequests.getUsers();
    }


    public void getBooks(Book book) {

    }
}