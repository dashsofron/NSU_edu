package Database.files.requests;

import Database.books.models.BookStatus;
import Database.files.models.BookGenre;
import Database.files.models.BookTakingRecord;
import Database.search.DateSearch;
import UI.BookUIEntity;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class RecordRequests {
    Connection conn;

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Integer addRecord(BookTakingRecord record) {
        int recordId = getNextRecordId();
        record.setRecordId(recordId);
        String values = "";
        values += recordId + "," + record.getReaderId() + "," + record.getBookId();
        if (record.getDateOfTaking() != null && record.getDateOfReturning() != null)
            values +="," + toDate(record.getDateOfTaking()) + "," + toDate(record.getDateOfReturning());
        else values += ",sysdate" + ",add_months(sysdate,+1)";

        values += ",'" + record.getStatus() + "'";
        String sqlAddRecord = "INSERT INTO libraryAdmin.taken_books_table VALUES (" + values + ")";

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlAddRecord);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't add new record");
            throw new RuntimeException();
        }
        return recordId;


    }

    public void deleteRecord(Integer recordId) {
        String sqlDeleteRecord = "DELETE FROM libraryAdmin.taken_books_table WHERE record_id = " + recordId;
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlDeleteRecord);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't delete record");
            throwables.printStackTrace();

        }

    }

    public void updateRecord(BookTakingRecord record) {
        String sqlUpdateRecord = "UPDATE libraryAdmin.taken_books_table SET ";
        sqlUpdateRecord += "reader_id = " + record.getReaderId() + " ";
        sqlUpdateRecord += ",book_id = " + record.getBookId() + " ";
        sqlUpdateRecord += ",taking_date = " + toDate(record.getDateOfTaking()) + " ";
        sqlUpdateRecord += ",returning_date = " + toDate(record.getDateOfReturning()) + " ";
        sqlUpdateRecord += ",record_status = '" + record.getStatus() + "'";
        sqlUpdateRecord += "WHERE record_id = " + record.getRecordId();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlUpdateRecord);
            preStatementReader.executeUpdate();
        } catch (SQLException throwables) {
            System.err.println("can't update record");
            throwables.printStackTrace();
        }

    }

    public BookTakingRecord getRecordByBookId(Integer bookId) {
        String sqlGetRecord = "SELECT * from libraryAdmin.taken_books_table WHERE book_id = " + bookId;
        BookTakingRecord record = null;

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetRecord);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                record = new BookTakingRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setReaderId(rs.getInt("reader_id"));
                record.setBookId(rs.getInt("book_id"));
                record.setStatus(BookStatus.valueOf(rs.getString("record_status")));
                record.setDateOfReturning(rs.getDate("returning_date"));
                record.setDateOfTaking(rs.getDate("taking_date"));

            }
        } catch (SQLException throwables) {
            System.err.println("can't get record");
            throwables.printStackTrace();
        }
        return record;
    }

    public BookTakingRecord getRecordByBookIdAndStatus(Integer bookId, BookStatus status) {
        String sqlGetRecord = "SELECT * from libraryAdmin.taken_books_table WHERE book_id = " + bookId + " AND record_status='" + status + "' ";
        BookTakingRecord record = null;

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetRecord);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                record = new BookTakingRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setReaderId(rs.getInt("reader_id"));
                record.setBookId(rs.getInt("book_id"));
                record.setStatus(BookStatus.valueOf(rs.getString("record_status")));
                record.setDateOfReturning(rs.getDate("returning_date"));
                record.setDateOfTaking(rs.getDate("taking_date"));

            }
        } catch (SQLException throwables) {
            System.err.println("can't get record");
            throwables.printStackTrace();
        }
        return record;
    }

    public BookTakingRecord getRecord(Integer recordId) {
        String sqlGetRecord = "SELECT * from libraryAdmin.taken_books_table WHERE record_id = " + recordId;
        BookTakingRecord record = null;

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetRecord);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                record = new BookTakingRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setReaderId(rs.getInt("reader_id"));
                record.setBookId(rs.getInt("book_id"));
                record.setStatus(BookStatus.valueOf(rs.getString("record_status")));
                record.setDateOfReturning(rs.getDate("returning_date"));
                record.setDateOfTaking(rs.getDate("taking_date"));

            }
        } catch (SQLException throwables) {
            System.err.println("can't get record");
            throwables.printStackTrace();
        }
        return record;
    }

    public List<BookTakingRecord> getRecords() {
        String sqlGetBooks = "SELECT * from libraryAdmin.taken_books_table ";
        List<BookTakingRecord> records = new LinkedList<>();
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetBooks);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                BookTakingRecord record = new BookTakingRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setReaderId(rs.getInt("reader_id"));
                record.setBookId(rs.getInt("book_id"));
                record.setStatus(BookStatus.valueOf(rs.getString("record_status")));
                record.setDateOfReturning(rs.getDate("returning_date"));
                record.setDateOfTaking(rs.getDate("taking_date"));
                records.add(record);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get records");
            throwables.printStackTrace();
        }
        return records;

    }

    public List<BookTakingRecord> getRecordsWithFilter(BookTakingRecord recordFilter, Date start1, Date start2, Date end1, Date end2) {

        String sqlGetBooks = "SELECT * from libraryAdmin.taken_books_table ";
        String sqlFilter = "";
        if (recordFilter.getStatus() != null) {
            sqlFilter += " AND record_status = '" + recordFilter.getStatus() + "' ";
        }
        if (recordFilter.getReaderId() != null) {
            sqlFilter += " AND reader_id = " + recordFilter.getReaderId();

        }

        if (recordFilter.getBookId() != null) {
            sqlFilter += " AND book_id = " + recordFilter.getBookId();


        }

        if (recordFilter.getDateOfTaking() != null) {
            sqlFilter += " AND taking_date = " + toDate(recordFilter.getDateOfTaking());


        } else if (start1 != null && start2 != null) {
            sqlFilter += " AND taking_date BETWEEN (" + toDate(start1) + "," + toDate(start2) + ")";


        } else if (start1 != null) {
            sqlFilter += " AND taking_date >= " + toDate(start1);

        } else if (start2 != null) {
            sqlFilter += " AND taking_date <= " + toDate(start2);


        }


        if (recordFilter.getDateOfReturning() != null) {
            sqlFilter += " AND taking_date = " + toDate(recordFilter.getDateOfReturning());


        } else if (end1 != null && end2 != null) {
            sqlFilter += " AND taking_date BETWEEN (" + toDate(end1) + "," + toDate(end2
            ) + ")";

        } else if (end1 != null) {
            sqlFilter += " AND taking_date >= " + toDate(end1);

        } else if (end2 != null) {
            sqlFilter += " AND taking_date <= " + toDate(end2);

        }

        if (sqlFilter.contains("AND")) {
            sqlFilter = sqlFilter.replaceFirst("AND", "WHERE");
            sqlGetBooks += sqlFilter;

        }

        List<BookTakingRecord> records = new LinkedList<>();
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetBooks);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                BookTakingRecord record = new BookTakingRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setReaderId(rs.getInt("reader_id"));
                record.setBookId(rs.getInt("book_id"));
                record.setStatus(BookStatus.valueOf(rs.getString("record_status")));
                record.setDateOfReturning(rs.getDate("returning_date"));
                record.setDateOfTaking(rs.getDate("taking_date"));
                records.add(record);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get records");
            throwables.printStackTrace();
        }
        return records;


    }


    public BookUIEntity getBookTakenRecord(Integer recordId) {
        String sqlGetBooks = "SELECT tbt.record_id, tbt.book_id,tbt.taking_date, tbt.returning_date,tbt.record_status, tbt.READER_ID , " +
                "bt.book_status,bt.book_type_id,bt.book_place_id, " +
                "btt.book_name, btt.book_author, btt.book_genre, " +
                "pt.place_name " +
                "from libraryAdmin.taken_books_table tbt " +

                "INNER JOIN libraryAdmin.books_table bt " +
                "ON tbt.book_id = bt.book_id " +

                "INNER JOIN libraryAdmin.book_types_table btt " +
                "ON bt.book_type_id = btt.book_type_id " +

                "INNER JOIN libraryAdmin.places_table pt " +
                "ON bt.book_place_id = pt.place_id " +
                "WHERE record_id = " + recordId;
        BookUIEntity record = new BookUIEntity();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetBooks);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                record.setReaderId(rs.getInt("READER_ID"));
                record.setId(rs.getInt("book_id"));
                record.setName(rs.getString("book_name"));
                record.setAuthor(rs.getString("book_author"));
                record.setGenre(BookGenre.valueOf(rs.getString("book_genre")));
                record.setPlace(rs.getString("place_name"));
                record.setStatus(BookStatus.valueOf(rs.getString("record_status")));
                record.setTakingDate(rs.getDate("taking_date"));
                record.setReturningDate(rs.getDate("returning_date"));
            }
        } catch (SQLException throwables) {
            System.err.println("can't get records");
            throwables.printStackTrace();
        }
        return record;


    }

    public List<BookUIEntity> getBookRecords() {
        String sqlGetBooks = "SELECT tbt.book_id,tbt.taking_date, tbt.returning_date,tbt.record_status, tbt.READER_ID , " +
                "bt.book_status,bt.book_type_id,bt.book_place_id, " +
                "btt.book_name, btt.book_author, btt.book_genre, " +
                "pt.place_name " +
                "from libraryAdmin.taken_books_table tbt " +

                "INNER JOIN libraryAdmin.books_table bt " +
                "ON tbt.book_id = bt.book_id " +

                "INNER JOIN libraryAdmin.book_types_table btt " +
                "ON bt.book_type_id = btt.book_type_id " +

                "INNER JOIN libraryAdmin.places_table pt " +
                "ON bt.book_place_id = pt.place_id ";
        List<BookUIEntity> records = new LinkedList<>();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetBooks);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                BookUIEntity record = new BookUIEntity();
                record.setReaderId(rs.getInt("READER_ID"));
                record.setId(rs.getInt("book_id"));
                record.setName(rs.getString("book_name"));
                record.setAuthor(rs.getString("book_author"));
                record.setGenre(BookGenre.valueOf(rs.getString("book_genre")));
                record.setPlace(rs.getString("place_name"));
                record.setStatus(BookStatus.valueOf(rs.getString("record_status")));
                record.setTakingDate(rs.getDate("taking_date"));
                record.setReturningDate(rs.getDate("returning_date"));
                records.add(record);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get records");
            throwables.printStackTrace();
        }
        return records;


    }

    public List<BookUIEntity> getBooksWithFilter(BookTakingRecord recordFilter, Date start1, Date start2, Date end1, Date end2) {

        String sqlGetBooks = "SELECT tbt.book_id,tbt.taking_date, tbt.returning_date,tbt.record_status, tbt.READER_ID , " +
                "bt.book_status,bt.book_type_id,bt.book_place_id, " +
                "btt.book_name, btt.book_author, btt.book_genre, " +
                "pt.place_name " +
                "from libraryAdmin.taken_books_table tbt " +

                "INNER JOIN libraryAdmin.books_table bt " +
                "ON tbt.book_id = bt.book_id " +

                "INNER JOIN libraryAdmin.book_types_table btt " +
                "ON bt.book_type_id = btt.book_type_id " +

                "INNER JOIN libraryAdmin.places_table pt " +
                "ON bt.book_place_id = pt.place_id ";

        String sqlFilter = "";
        if (recordFilter.getStatus() != null) {
            sqlFilter += " AND record_status = '" + recordFilter.getStatus() + "'";
        }
        if (recordFilter.getReaderId() != null) {
            sqlFilter += " AND reader_id = " + recordFilter.getReaderId();

        }

        if (recordFilter.getBookId() != null) {
            sqlFilter += " AND book_id = " + recordFilter.getBookId();


        }

        if (recordFilter.getDateOfTaking() != null) {
            sqlFilter += " AND taking_date = " + toDate(recordFilter.getDateOfTaking());


        } else if (start1 != null && start2 != null) {
            sqlFilter += " AND taking_date BETWEEN (" + toDate(start1) + "," + toDate(start2) + ")";


        } else if (start1 != null) {
            sqlFilter += " AND taking_date >= " + toDate(start1);

        } else if (start2 != null) {
            sqlFilter += " AND taking_date <= " + toDate(start2);


        }


        if (recordFilter.getDateOfReturning() != null) {
            sqlFilter += " AND taking_date=" + toDate(recordFilter.getDateOfReturning());


        } else if (end1 != null && end2 != null) {
            sqlFilter += " AND taking_date BETWEEN(" + toDate(end1) + "," + toDate(end2
            ) + ")";

        } else if (end1 != null) {
            sqlFilter += " AND taking_date>=" + toDate(end1);

        } else if (end2 != null) {
            sqlFilter += " AND taking_date<=" + toDate(end2);

        }

        if (sqlFilter.contains("AND")) {
            sqlFilter = sqlFilter.replaceFirst("AND", "WHERE");
            sqlGetBooks += sqlFilter;

        }


        List<BookUIEntity> records = new LinkedList<>();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetBooks);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                BookUIEntity record = new BookUIEntity();
                record.setReaderId(rs.getInt("READER_ID"));
                record.setId(rs.getInt("book_id"));
                record.setName(rs.getString("book_name"));
                record.setAuthor(rs.getString("book_author"));
                record.setGenre(BookGenre.valueOf(rs.getString("book_genre")));
                record.setPlace(rs.getString("place_name"));
                record.setStatus(BookStatus.valueOf(rs.getString("record_status")));
                record.setTakingDate(rs.getDate("taking_date"));
                record.setReturningDate(rs.getDate("returning_date"));
                records.add(record);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get records");
            throwables.printStackTrace();
        }
        return records;

    }

    public List<BookUIEntity> getBooksWithFilter(BookUIEntity filter, DateSearch dateTake, DateSearch dateRet) {
        String sqlGetBooks = "SELECT tbt.record_id, tbt.book_id,tbt.taking_date, tbt.returning_date,tbt.record_status, tbt.READER_ID , " +
                "bt.book_status,bt.book_type_id,bt.book_place_id, " +
                "btt.book_name, btt.book_author, btt.book_genre, " +
                "pt.place_name " +
                "from libraryAdmin.taken_books_table tbt " +

                "INNER JOIN libraryAdmin.books_table bt " +
                "ON tbt.book_id = bt.book_id " +

                "INNER JOIN libraryAdmin.book_types_table btt " +
                "ON bt.book_type_id = btt.book_type_id " +

                "INNER JOIN libraryAdmin.places_table pt " +
                "ON bt.book_place_id = pt.place_id ";

        String sqlFilter = "";
        if (filter.getStatus() != null) {
            sqlFilter += " AND record_status = '" + filter.getStatus() + "'";
        }
        if (filter.getReaderId() != null) {
            sqlFilter += " AND tbt.reader_id = " + filter.getReaderId();

        }

        if (filter.getBookId() != null) {
            sqlFilter += " AND tbt.book_id = " + filter.getBookId();
        }
        if (filter.getAuthor() != null)
            sqlFilter += " AND book_author = " + filter.getAuthor();

        if (filter.getName() != null)
            sqlFilter += " AND book_name = " + filter.getName();

        if (filter.getGenre() != null)
            sqlFilter += " AND book_genre = " + filter.getGenre();

        if (filter.getPlace() != null)
            sqlFilter += " AND place_name = " + filter.getPlace();


        if (filter.getTakingDate() != null) {
            switch (dateTake) {
                case РАВНО:
                    sqlFilter += " AND taking_date = " + toDate(filter.getTakingDate());
                    break;

                case БОЛЬШЕ:
                    sqlFilter += " AND taking_date > " + toDate(filter.getTakingDate());
                    break;

                case МЕНЬШЕ:
                    sqlFilter += " AND taking_date < " + toDate(filter.getTakingDate());
                    break;

            }
        }

        if (filter.getReturningDate() != null) {
            switch (dateRet) {
                case РАВНО:
                    sqlFilter += " AND returning_date = " + toDate(filter.getReturningDate());
                    break;

                case БОЛЬШЕ:
                    sqlFilter += " AND returning_date > " + toDate(filter.getReturningDate());
                    break;

                case МЕНЬШЕ:
                    sqlFilter += " AND returning_date < " + toDate(filter.getReturningDate());
                    break;

            }
        }

        if (sqlFilter.contains("AND")) {
            sqlFilter = sqlFilter.replaceFirst("AND", "WHERE");
            sqlGetBooks += sqlFilter;

        }


        List<BookUIEntity> records = new LinkedList<>();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetBooks);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                BookUIEntity record = new BookUIEntity();
                record.setReaderId(rs.getInt("READER_ID"));
                record.setId(rs.getInt("record_id"));
                record.setName(rs.getString("book_name"));
                record.setAuthor(rs.getString("book_author"));
                record.setGenre(BookGenre.valueOf(rs.getString("book_genre")));
                record.setPlace(rs.getString("place_name"));
                record.setStatus(BookStatus.valueOf(rs.getString("record_status")));
                record.setTakingDate(rs.getDate("taking_date"));
                record.setReturningDate(rs.getDate("returning_date"));
                records.add(record);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get records");
            throwables.printStackTrace();
        }
        return records;

    }

    public List<BookUIEntity> getTakenBookRecords(Integer readerId) {
        String sqlGetBooks = "SELECT tbt.book_id,tbt.taking_date, tbt.returning_date,tbt.record_status, tbt.READER_ID , " +
                "bt.book_status,bt.book_type_id,bt.book_place_id, " +
                "btt.book_name, btt.book_author, btt.book_genre, " +
                "pt.place_name " +
                "from libraryAdmin.taken_books_table tbt " +

                "INNER JOIN libraryAdmin.books_table bt " +
                "ON tbt.book_id = bt.book_id " +

                "INNER JOIN libraryAdmin.book_types_table btt " +
                "ON bt.book_type_id = btt.book_type_id " +

                "INNER JOIN libraryAdmin.places_table pt " +
                "ON bt.book_place_id = pt.place_id " +

                "WHERE tbt.reader_id = " + readerId + " AND tbt.record_status= 'ВЗЯТО'";
        List<BookUIEntity> records = new LinkedList<>();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetBooks);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                BookUIEntity record = new BookUIEntity();
                record.setId(rs.getInt("book_id"));
                record.setName(rs.getString("book_name"));
                record.setAuthor(rs.getString("book_author"));
                record.setGenre(BookGenre.valueOf(rs.getString("book_genre")));
                record.setPlace(rs.getString("place_name"));
                record.setStatus(BookStatus.valueOf(rs.getString("record_status")));
                record.setTakingDate(rs.getDate("taking_date"));
                record.setReturningDate(rs.getDate("returning_date"));
                records.add(record);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get records");
            throwables.printStackTrace();
        }
        return records;


    }

    public List<BookUIEntity> getOrderedBookRecords(Integer readerId) {
        String sqlGetBooks = "SELECT tbt.book_id,tbt.taking_date, tbt.returning_date,tbt.record_status, tbt.READER_ID,  " +
                "bt.book_status,bt.book_type_id,bt.book_place_id, " +
                "btt.book_name, btt.book_author, btt.book_genre, " +
                "pt.place_name " +
                "from libraryAdmin.taken_books_table tbt " +

                "INNER JOIN libraryAdmin.books_table bt " +
                "ON tbt.book_id = bt.book_id " +

                "INNER JOIN libraryAdmin.book_types_table btt " +
                "ON bt.book_type_id = btt.book_type_id " +

                "INNER JOIN libraryAdmin.places_table pt " +
                "ON bt.book_place_id = pt.place_id " +

                "WHERE tbt.reader_id = " + readerId + " AND (tbt.record_status = 'ЗАКАЗАНО' OR tbt.record_status = 'ГОТОВИТСЯ') ";

        List<BookUIEntity> records = new LinkedList<>();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetBooks);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                BookUIEntity record = new BookUIEntity();
                record.setId(rs.getInt("book_id"));
                record.setName(rs.getString("book_name"));
                record.setAuthor(rs.getString("book_author"));
                record.setGenre(BookGenre.valueOf(rs.getString("book_genre")));
                record.setPlace(rs.getString("place_name"));
                record.setStatus(BookStatus.valueOf(rs.getString("record_status")));
                record.setTakingDate(rs.getDate("taking_date"));
                record.setReturningDate(rs.getDate("returning_date"));
                records.add(record);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get records");
            throwables.printStackTrace();
        }
        return records;

    }


    public Integer getNextRecordId() {
        String sql = "select libraryAdmin.sq_record.nextval from DUAL";
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

    public String toDate(Date date) {
        if (date == null)
            return "TO_DATE (null, 'dd.mm.yyyy')";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedString = date.toLocalDate().format(formatter);

        return "TO_DATE ( '" + formattedString + "', 'dd.mm.yyyy')";
    }


}
