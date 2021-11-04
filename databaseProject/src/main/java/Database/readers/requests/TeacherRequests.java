package Database.readers.requests;

import Database.readers.models.ReaderEntity;
import Database.readers.models.ReaderType;
import Database.readers.models.TeacherEntity;
import Database.search.DateSearch;
import UI.StudentUiEntity;
import UI.TeacherUiEntity;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class TeacherRequests {
    Connection conn;

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void addTeacher(TeacherEntity teacher) {
        String sqlAddTeacher = "INSERT INTO libraryAdmin.teachers_table VALUES (" +
                "libraryAdmin.sq_teacher.nextval, " +
                "'" + teacher.getCathedra() + "' ," +
                "'" + teacher.getDegree() + "' , " +
                teacher.getReaderId() +
                ")";
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlAddTeacher);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't add new teacher info");
        }


    }

    public void deleteTeacher(Integer readerId) {
        String sqlDeleteTeacher = "DELETE FROM libraryAdmin.teachers_table WHERE reader_id = " + readerId;
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlDeleteTeacher);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't delete teacher info");
        }

    }

    public TeacherEntity getTeacher(Integer readerId) {
        String sqlGetTeacher = "SELECT  rt.READER_ID,rt.READER_NAME,rt.READER_LAST_NAME,rt.READER_FATHER_NAME, " +
                "rt.READER_TYPE,rt.REGISTRATION_DATE,rt.leaving_date ,rt.ticket_number,teacher_cathedra, teacher_degree FROM libraryAdmin.teachers_table tt " +
                "INNER JOIN libraryAdmin.readers_table rt " +
                "ON tt.reader_id = rt.reader_id " +
                "WHERE tt.reader_id = " + readerId;
        TeacherEntity teacher = new TeacherEntity();
        ResultSet rs = null;
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetTeacher);
            rs = preStatementReader.executeQuery(sqlGetTeacher);

        } catch (SQLException throwables) {
            System.err.println("can't get teacher");
        }
        try {
            if (rs.next()) {
                teacher.setReaderId(rs.getInt("reader_id"));
                teacher.setReaderName(rs.getString("reader_name"));
                teacher.setReaderLastName(rs.getString("reader_last_name"));
                teacher.setFatherName(rs.getString("reader_father_name"));
                teacher.setReaderType(ReaderType.valueOf(rs.getString("reader_type")));
                teacher.setRegistrationDate(rs.getDate("registration_date"));
                Date leavingDate = rs.getDate("LEAVING_DATE");
                if (leavingDate != null)
                    teacher.setLeavingDate(leavingDate);
                teacher.setTicketNumber(rs.getInt("ticket_number"));
                teacher.setCathedra(rs.getString("teacher_cathedra"));
                teacher.setDegree(rs.getString("teacher_degree"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return teacher;

    }

    public void updateTeacher(TeacherEntity teacher) {
        String sqlUpdateTeacher = "UPDATE libraryAdmin.teachers_table SET ";
        sqlUpdateTeacher += "teacher_cathedra = '" + teacher.getCathedra() + "' ";
        sqlUpdateTeacher += ",teacher_degree = '" + teacher.getDegree()+ "' ";
        sqlUpdateTeacher += "WHERE reader_id =" + teacher.getReaderId();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlUpdateTeacher);
            preStatementReader.executeUpdate();
        } catch (SQLException throwables) {
            System.err.println("can't update teacher info");
            throwables.printStackTrace();
        }

    }

    public List<ReaderEntity> getTeachers() {

        String sqlGetTeachers = "SELECT  rt.READER_ID,rt.READER_NAME,rt.READER_LAST_NAME,rt.READER_FATHER_NAME, " +
                "rt.READER_TYPE,rt.REGISTRATION_DATE,rt.leaving_date ,rt.ticket_number,teacher_cathedra, teacher_degree FROM libraryAdmin.teachers_table tt " +
                "INNER JOIN libraryAdmin.readers_table rt " +
                "ON tt.reader_id = rt.reader_id ";
        List<ReaderEntity> teachers = new LinkedList<>();

        try {
            PreparedStatement preStatement = conn.prepareStatement(sqlGetTeachers);
            ResultSet rs = preStatement.executeQuery(sqlGetTeachers);
            while (rs.next()) {
                TeacherEntity teacher = new TeacherEntity();
                teacher.setReaderId(rs.getInt("reader_id"));
                teacher.setReaderName(rs.getString("reader_name"));
                teacher.setReaderLastName(rs.getString("reader_last_name"));
                teacher.setFatherName(rs.getString("reader_father_name"));
                teacher.setReaderType(ReaderType.valueOf(rs.getString("reader_type")));
                teacher.setRegistrationDate(rs.getDate("registration_date"));
                Date leavingDate = rs.getDate("LEAVING_DATE");
                if (leavingDate != null)
                    teacher.setLeavingDate(leavingDate);
                teacher.setTicketNumber(rs.getInt("ticket_number"));
                teacher.setCathedra(rs.getString("teacher_cathedra"));
                teacher.setDegree(rs.getString("teacher_degree"));
                teachers.add(teacher);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get teachers");
        }
        return teachers;

    }


    public List<TeacherUiEntity> getTeachersWithFilter(TeacherUiEntity readerEntityFilter, DateSearch dateTake, DateSearch dateRet) {
        String sqlGroup=" group by rt.reader_id, rt.reader_name, rt.reader_last_name, rt.reader_father_name, rt.reader_type, " +
                "rt.registration_date, rt.leaving_date,tt.bookNum,ttt.dNum,st.teacher_cathedra,st.teacher_degree";
        String sqlHaving="  ";

        String sqlGetReaders = " select rt.reader_id, rt.reader_name, rt.reader_last_name, rt.reader_father_name, " +
                "rt.reader_type, rt.registration_date, rt.leaving_date,st.teacher_cathedra,st.teacher_degree, count(pt.punishment_id) as punNum, " +
                "sum(pt.punishment_payment) as punSum,tt.bookNum ,ttt.dNum " +
                "from readers_table rt " +
                "INNER JOIN libraryAdmin.teachers_table st " +
                "ON rt.reader_id = st.reader_id " +
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

        if (readerEntityFilter.getRegistrationDate() != null) {
            switch (dateTake) {
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

        if (readerEntityFilter.getLeavingDate() != null) {
            switch (dateRet) {
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
            if (readerEntityFilter.getCathedra() != null&&!readerEntityFilter.getCathedra().equals("")) {
                sqlFilter += " AND teacher_cathedra = '" + readerEntityFilter.getCathedra() + "' ";

            }
            if (readerEntityFilter.getDegree() != null&&!readerEntityFilter.getDegree().equals("")) {
                sqlFilter += " AND teacher_degree = " + readerEntityFilter.getDegree();

            }

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

        List<TeacherUiEntity> readers = new LinkedList<>();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetReaders);
            ResultSet rs = preStatementReader.executeQuery(sqlGetReaders);
            while (rs.next()) {
                TeacherUiEntity reader = new TeacherUiEntity();
                reader.setReaderId(rs.getInt("reader_id"));
                reader.setReaderName(rs.getString("reader_name"));
                reader.setReaderLastName(rs.getString("reader_last_name"));
                reader.setFatherName(rs.getString("reader_father_name"));
                reader.setReaderType(ReaderType.valueOf(rs.getString("reader_type")));
                reader.setRegistrationDate(rs.getDate("registration_date"));
                Date leavingDate = rs.getDate("LEAVING_DATE");
                if (leavingDate != null)
                    reader.setLeavingDate(leavingDate);
                reader.setCathedra(rs.getString("teacher_cathedra"));
                reader.setDegree(rs.getString("teacher_degree"));
                reader.setPunishmentNumField(rs.getInt("punNum"));
                reader.setPaymentField(rs.getInt("punSum"));
                reader.setDebtField(rs.getInt("dNum"));
                reader.setBookNumField(rs.getInt("bookNum"));
                readers.add(reader);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get teachers");
            throwables.printStackTrace();
        }
        return readers;
    }
    public String toDate(Date date) {
        if (date == null)
            return "TO_DATE (null, 'dd.mm.yyyy')";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedString = date.toLocalDate().format(formatter);

        return "TO_DATE ( '" + formattedString + "', 'dd.mm.yyyy')";
    }

}
