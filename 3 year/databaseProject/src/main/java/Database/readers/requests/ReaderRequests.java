package Database.readers.requests;

import Database.readers.models.ReaderEntity;
import Database.readers.models.ReaderType;
import Database.readers.models.StudentEntity;
import Database.readers.models.TeacherEntity;
import Database.search.DateSearch;
import UI.ReaderUiEntity;
import UI.StudentUiEntity;
import UI.TeacherUiEntity;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class ReaderRequests {
    Connection conn;

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Integer addReader(ReaderEntity reader) {
        int readerId = getNextReaderId();
        reader.setReaderId(readerId);
        String sqlAddReader = "INSERT INTO libraryAdmin.readers_table VALUES (" +
                readerId + ", " +
                "'" + reader.getReaderName() + "' ," +
                "'" + reader.getReaderLastName() + "' ," +
                "'" + reader.getFatherName() + "' ," +
                "'" + reader.getReaderType() + "' ," +
                toDate(reader.getRegistrationDate()) + " ," +
                toDate(reader.getLeavingDate()) + " ," +
                readerId + ")";

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlAddReader);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't add new reader");
            throwables.printStackTrace();
        }
        return readerId;

    }

    public void deleteReader(Integer readerId) {
        String sqlDeleteReader = "DELETE FROM libraryAdmin.readers_table WHERE reader_id=" + readerId;
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlDeleteReader);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't delete reader");
            throwables.printStackTrace();

        }


    }

    public void updateReader(ReaderEntity reader) {
        String sqlUpdateReader = "UPDATE libraryAdmin.readers_table SET ";
        sqlUpdateReader += "reader_name= " + "'" + reader.getReaderName() + "' ";
        sqlUpdateReader += ",reader_last_name= " + "'" + reader.getReaderLastName() + "' ";
        sqlUpdateReader += ",reader_father_name= " + "'" + reader.getFatherName() + "' ";
        sqlUpdateReader += ",registration_date= " + toDate(reader.getRegistrationDate()) + " ";
        sqlUpdateReader += ",leaving_date= " + toDate(reader.getLeavingDate()) + " ";

        sqlUpdateReader += "WHERE reader_id=" + reader.getReaderId();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlUpdateReader);
            preStatementReader.executeUpdate();
        } catch (SQLException throwables) {
            System.err.println("can't update reader");
            throwables.printStackTrace();
        }


    }

    public List<ReaderEntity> getReaders() {
        String sqlGetReaders = "SELECT * from libraryAdmin.readers_table ";
        List<ReaderEntity> readers = new LinkedList<>();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetReaders);
            ResultSet rs = preStatementReader.executeQuery(sqlGetReaders);
            while (rs.next()) {
                ReaderEntity reader = new ReaderEntity();
                reader.setReaderId(rs.getInt("reader_id"));
                reader.setReaderName(rs.getString("reader_name"));
                reader.setReaderLastName(rs.getString("reader_last_name"));
                reader.setFatherName(rs.getString("reader_father_name"));
                reader.setReaderType(ReaderType.valueOf(rs.getString("reader_type")));
                reader.setRegistrationDate(rs.getDate("registration_date"));
                Date leavingDate = rs.getDate("LEAVING_DATE");
                if (leavingDate != null)
                    reader.setLeavingDate(leavingDate);
                reader.setTicketNumber(rs.getInt("ticket_number"));
                readers.add(reader);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get readers");
            throwables.printStackTrace();

        }

        return readers;
    }

    public List<ReaderEntity> getReadersWithFilter(ReaderEntity readerEntityFilter, Date start1, Date start2, Date end1, Date end2) {
        String sqlGetReaders = "SELECT * from libraryAdmin.readers_table rt ";
        ReaderType type = readerEntityFilter.getReaderType();
        if (type != null) {
            switch (type) {
                case ЧИТАТЕЛЬ:
                    break;
                case СТУДЕНТ:
                    sqlGetReaders += "INNER JOIN libraryAdmin.students_table st ON rt.reader_id = st.reader_id ";
                    break;

                case УЧИТЕЛЬ:
                    sqlGetReaders += "INNER JOIN libraryAdmin.teachers_table st ON rt.reader_id = st.reader_id ";
                    break;
            }
        }
        String sqlFilter = "";
        if (readerEntityFilter.getReaderName() != null) {
            sqlFilter += " AND reader_name = '" + readerEntityFilter.getReaderName() + "' ";

        }

        if (readerEntityFilter.getReaderLastName() != null) {
            sqlFilter += " AND reader_last_name = '" + readerEntityFilter.getReaderLastName() + "' ";


        }

        if (readerEntityFilter.getFatherName() != null) {
            sqlFilter += " AND reader_father_name = '" + readerEntityFilter.getFatherName() + "' ";

        }


        if (readerEntityFilter.getRegistrationDate() != null) {
            sqlFilter += " AND registration_date = " + toDate(readerEntityFilter.getRegistrationDate());


        } else if (start1 != null && start2 != null) {
            sqlFilter += " AND registration_date BETWEEN (" + toDate(start1) + "," + toDate(start2) + ")";


        } else if (start1 != null) {
            sqlFilter += " AND registration_date >= " + toDate(start1);


        } else if (start2 != null) {
            sqlFilter += " AND registration_date <= " + toDate(start2);


        }


        if (readerEntityFilter.getLeavingDate() != null) {
            sqlFilter += " AND LEAVING_DATE = " + toDate(readerEntityFilter.getLeavingDate());


        } else if (end1 != null && end2 != null) {
            sqlFilter += " AND LEAVING_DATE BETWEEN (" + toDate(end1) + "," + toDate(end2) + ")";


        } else if (end1 != null) {
            sqlFilter += " AND LEAVING_DATE >= " + toDate(end1);


        } else if (end2 != null) {
            sqlFilter += " AND LEAVING_DATE <= " + toDate(end2);


        }

        if (type != null) {
            sqlFilter += " AND reader_type = '" + readerEntityFilter.getReaderType() + "' ";
            switch (type) {
                case СТУДЕНТ:
                    StudentEntity studentEntity = (StudentEntity) readerEntityFilter;
                    if (studentEntity.getDepartment() != null) {
                        sqlFilter += " AND student_department = '" + studentEntity.getDepartment() + "' ";

                    }
                    if (studentEntity.getGroup() != null) {
                        sqlFilter += " AND student_group = " + studentEntity.getGroup();

                    }
                    break;
                case ЧИТАТЕЛЬ:
                    TeacherEntity teacherEntity = (TeacherEntity) readerEntityFilter;
                    if (teacherEntity.getCathedra() != null) {
                        sqlFilter += " AND teacher_cathedra = '" + teacherEntity.getCathedra() + "' ";

                    }
                    if (teacherEntity.getDegree() != null) {
                        sqlFilter += " AND teacher_degree = '" + teacherEntity.getDegree() + "' ";

                    }

            }


        }

        if (sqlFilter.contains("AND")) {
            sqlFilter = sqlFilter.replaceFirst("AND", "WHERE");
            sqlGetReaders += sqlFilter;

        }

        List<ReaderEntity> readers = new LinkedList<>();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetReaders);
            ResultSet rs = preStatementReader.executeQuery(sqlGetReaders);
            switch (type) {
                case УЧИТЕЛЬ:
                    while (rs.next()) {
                        TeacherEntity reader = new TeacherEntity();
                        reader.setReaderId(rs.getInt("reader_id"));
                        reader.setReaderName(rs.getString("reader_name"));
                        reader.setReaderLastName(rs.getString("reader_last_name"));
                        reader.setFatherName(rs.getString("reader_father_name"));
                        reader.setReaderType(ReaderType.valueOf(rs.getString("reader_type")));
                        reader.setRegistrationDate(rs.getDate("registration_date"));
                        Date leavingDate = rs.getDate("LEAVING_DATE");
                        if (leavingDate != null)
                            reader.setLeavingDate(leavingDate);
                        reader.setTicketNumber(rs.getInt("ticket_number"));
                        reader.setCathedra(rs.getString("teacher_cathedra"));
                        reader.setDegree(rs.getString("teacher_degree"));
                        readers.add(reader);
                    }
                    break;
                case СТУДЕНТ:
                    while (rs.next()) {
                        StudentEntity reader = new StudentEntity();
                        reader.setReaderId(rs.getInt("reader_id"));
                        reader.setReaderName(rs.getString("reader_name"));
                        reader.setReaderLastName(rs.getString("reader_last_name"));
                        reader.setFatherName(rs.getString("reader_father_name"));
                        reader.setReaderType(ReaderType.valueOf(rs.getString("reader_type")));
                        reader.setRegistrationDate(rs.getDate("registration_date"));
                        Date leavingDate = rs.getDate("LEAVING_DATE");
                        if (leavingDate != null)
                            reader.setLeavingDate(leavingDate);
                        reader.setTicketNumber(rs.getInt("ticket_number"));
                        reader.setDepartment(rs.getString("student_department"));
                        reader.setGroup(rs.getInt("student_group"));
                        readers.add(reader);
                    }
                    break;

                case ЧИТАТЕЛЬ:
                    while (rs.next()) {
                        ReaderEntity reader = new ReaderEntity();
                        reader.setReaderId(rs.getInt("reader_id"));
                        reader.setReaderName(rs.getString("reader_name"));
                        reader.setReaderLastName(rs.getString("reader_last_name"));
                        reader.setFatherName(rs.getString("reader_father_name"));
                        reader.setReaderType(ReaderType.valueOf(rs.getString("reader_type")));
                        reader.setRegistrationDate(rs.getDate("registration_date"));
                        Date leavingDate = rs.getDate("LEAVING_DATE");
                        if (leavingDate != null)
                            reader.setLeavingDate(leavingDate);
                        reader.setTicketNumber(rs.getInt("ticket_number"));
                        readers.add(reader);
                    }
                    break;
            }
        } catch (SQLException throwables) {
            System.err.println("can't get readers");
            throwables.printStackTrace();

        }


        return readers;
    }


    public List<ReaderUiEntity> getReadersWithFilter(ReaderUiEntity readerEntityFilter, DateSearch dateTake, DateSearch dateRet){
        String sqlGroup=" group by rt.reader_id, rt.reader_name, rt.reader_last_name, rt.reader_father_name, rt.reader_type, " +
                "rt.registration_date, rt.leaving_date,tt.bookNum,ttt.dNum";
        String sqlHaving="  ";

        String sqlGetReaders = " select rt.reader_id, rt.reader_name, rt.reader_last_name, rt.reader_father_name, " +
                "rt.reader_type, rt.registration_date, rt.leaving_date, count(pt.punishment_id) as punNum, " +
                "sum(pt.punishment_payment) as punSum,tt.bookNum ,ttt.dNum " +
                "from readers_table rt " +
                "left JOIN PUNISHMENTS_TABLE pt ON rt.reader_id=pt.reader_id " +
                "left Join (select reader_id,count(record_id) as bookNum from taken_books_table " +
                "where record_status='ВЗЯТО' group by reader_id) " +
                "tt on rt.reader_id=tt.reader_id " +
                "left Join (select reader_id,count(record_id) as dNum from taken_books_table " +
                "where record_status='ВЗЯТО' AND returning_date<=sysdate group by reader_id) " +
                "ttt on rt.reader_id=ttt.reader_id ";

        String sqlFilter = "";
        if (readerEntityFilter.getReaderName() != null) {
            sqlFilter += " AND reader_name = '" + readerEntityFilter.getReaderName() + "' ";

        }

        if (readerEntityFilter.getReaderLastName() != null) {
            sqlFilter += " AND reader_last_name = '" + readerEntityFilter.getReaderLastName() + "' ";

        }

        if (readerEntityFilter.getFatherName() != null) {
            sqlFilter += " AND reader_father_name = '" + readerEntityFilter.getFatherName() + "' ";

        }

        if(readerEntityFilter.getRegistrationDate()!=null){
            switch (dateTake){
                case РАВНО:
                    sqlFilter += " AND taking_date = " + toDate(readerEntityFilter.getRegistrationDate());
                    break;

                case БОЛЬШЕ:
                    sqlFilter += " AND taking_date > " + toDate(readerEntityFilter.getRegistrationDate());
                    break;

                case МЕНЬШЕ:
                    sqlFilter += " AND taking_date < " + toDate(readerEntityFilter.getRegistrationDate());
                    break;

            }
        }

        if(readerEntityFilter.getLeavingDate()!=null){
            switch (dateRet){
                case РАВНО:
                    sqlFilter += " AND returning_date = " + toDate(readerEntityFilter.getLeavingDate());
                    break;

                case БОЛЬШЕ:
                    sqlFilter += " AND returning_date > " + toDate(readerEntityFilter.getLeavingDate());
                    break;

                case МЕНЬШЕ:
                    sqlFilter += " AND returning_date < " + toDate(readerEntityFilter.getLeavingDate());
                    break;

            }
        }

        if (readerEntityFilter.getReaderType() != null) {
            sqlFilter += " AND reader_type = '" + readerEntityFilter.getReaderType() + "' ";
        }
        if(readerEntityFilter.getPaymentField()!=null){
            sqlHaving += " AND sum(pt.punishment_payment) = " + readerEntityFilter.getPaymentField() ;
        }
        if(readerEntityFilter.getPunishmentNumField()!=null){
            sqlHaving += " AND count(pt.punishment_id) = " + readerEntityFilter.getPunishmentNumField() ;
        }
        if(readerEntityFilter.getDebtField()!=null){
            sqlFilter += " AND dNum = " + readerEntityFilter.getDebtField() ;
        }

        if(readerEntityFilter.getBookNumField()!=null){
            sqlFilter += " AND bookNum = "+readerEntityFilter.getBookNumField();

        }

        if (sqlFilter.contains("AND")) {
            sqlFilter = sqlFilter.replaceFirst("AND", "WHERE");
            sqlGetReaders += sqlFilter;

        }
        if (sqlHaving.contains("AND")) {
            sqlHaving = sqlHaving.replaceFirst("AND", "HAVING");
            sqlGetReaders += sqlHaving;
        }
        sqlGetReaders+=sqlGroup;


        List<ReaderUiEntity> readers = new LinkedList<>();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetReaders);
            ResultSet rs = preStatementReader.executeQuery(sqlGetReaders);

                        while (rs.next()) {
                            ReaderUiEntity reader = new ReaderUiEntity();
                            reader.setReaderId(rs.getInt("reader_id"));
                            reader.setReaderName(rs.getString("reader_name"));
                            reader.setReaderLastName(rs.getString("reader_last_name"));
                            reader.setFatherName(rs.getString("reader_father_name"));
                            reader.setReaderType(ReaderType.valueOf(rs.getString("reader_type")));
                            reader.setRegistrationDate(rs.getDate("registration_date"));
                            Date leavingDate = rs.getDate("LEAVING_DATE");
                            if (leavingDate != null)
                                reader.setLeavingDate(leavingDate);
                            //reader.setTicketNumber(rs.getInt("ticket_number"));
                            reader.setPunishmentNumField(rs.getInt("punNum"));
                            reader.setPaymentField(rs.getInt("punSum"));
                            reader.setDebtField(rs.getInt("dNum"));
                            reader.setBookNumField(rs.getInt("bookNum"));
                            readers.add(reader);

            }
        } catch (SQLException throwables) {
            System.err.println("can't get readers");
            throwables.printStackTrace();

        }


        return readers;
    }


    public ReaderType getReaderType(Integer readerId) {
        String sqlGetReaderType = "SELECT reader_type from libraryAdmin.readers_table WHERE reader_id = " + readerId;
        PreparedStatement preStatementReader = null;
        try {
            preStatementReader = conn.prepareStatement(sqlGetReaderType);
            ResultSet rs = preStatementReader.executeQuery(sqlGetReaderType);
            if (rs.next())
                return ReaderType.valueOf(rs.getString("reader_type"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    public String toDate(Date date) {
        if (date == null)
            return "TO_DATE (null, 'dd.mm.yyyy')";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedString = date.toLocalDate().format(formatter);

        return "TO_DATE ( '" + formattedString + "', 'dd.mm.yyyy')";
    }

    public Integer getNextReaderId() {
        String sql = "select libraryAdmin.sq_reader.nextval from DUAL";
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

    public Integer getNextTicketId() {
        String sql = "select sq_ticket.nextval from DUAL";
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
