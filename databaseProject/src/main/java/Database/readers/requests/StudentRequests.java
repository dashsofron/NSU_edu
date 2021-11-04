package Database.readers.requests;

import Database.readers.models.ReaderEntity;
import Database.readers.models.ReaderType;
import Database.readers.models.StudentEntity;
import Database.search.DateSearch;
import UI.ReaderUiEntity;
import UI.StudentUiEntity;
import UI.TeacherUiEntity;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class StudentRequests {
    Connection conn;

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void addStudent(StudentEntity student) {
        String sqlAddStudent = "INSERT INTO libraryAdmin.students_table VALUES (" +
                "libraryAdmin.sq_student.nextval, " +
                "'" + student.getDepartment() + "' ," +
                student.getGroup() + ", " +
                student.getReaderId() +
                ")";
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlAddStudent);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't add new student info");
            throwables.printStackTrace();

        }


    }

    public void deleteStudent(Integer readerId) {
        String sqlDeleteReader = "DELETE FROM libraryAdmin.students_table WHERE reader_id = " + readerId;
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlDeleteReader);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't delete student info");
        }

    }

    public StudentEntity getStudent(Integer readerId) {
        String sqlGetStudent = "SELECT  rt.READER_ID,rt.READER_NAME,rt.READER_LAST_NAME,rt.READER_FATHER_NAME, " +
                "rt.READER_TYPE,rt.REGISTRATION_DATE,rt.leaving_date ,rt.ticket_number,student_department, STUDENT_GROUP FROM libraryAdmin.students_table st " +
                "INNER JOIN libraryAdmin.readers_table rt " +
                "ON st.reader_id = rt.reader_id " +
                "WHERE st.reader_id = " + readerId;
        StudentEntity student = new StudentEntity();
        ResultSet rs = null;
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetStudent);
            rs = preStatementReader.executeQuery(sqlGetStudent);

        } catch (SQLException throwables) {
            System.err.println("can't get student");
            throwables.printStackTrace();

        }
        try {
            if (rs.next()) {
                student.setReaderId(rs.getInt("reader_id"));
                student.setReaderName(rs.getString("reader_name"));
                student.setReaderLastName(rs.getString("reader_last_name"));
                student.setFatherName(rs.getString("reader_father_name"));
                student.setReaderType(ReaderType.valueOf(rs.getString("reader_type")));
                student.setRegistrationDate(rs.getDate("registration_date"));
                Date leavingDate = rs.getDate("LEAVING_DATE");
                if (leavingDate != null)
                    student.setLeavingDate(leavingDate);
                student.setTicketNumber(rs.getInt("ticket_number"));
                student.setDepartment(rs.getString("student_department"));
                student.setGroup(rs.getInt("STUDENT_GROUP"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return student;

    }

    public void updateStudent(StudentEntity student) {
        String sqlUpdateReader = "UPDATE libraryAdmin.students_table SET ";
        sqlUpdateReader += "student_department = '" + student.getDepartment() + "' ";
        sqlUpdateReader += ",student_group = " + student.getGroup();
        sqlUpdateReader += "WHERE reader_id =" + student.getReaderId();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlUpdateReader);
            preStatementReader.executeUpdate();
        } catch (SQLException throwables) {
            System.err.println("can't update student info");
            throwables.printStackTrace();
        }

    }

    public List<ReaderEntity> getStudents() {

        String sqlGetStudents = "SELECT  rt.READER_ID,rt.READER_NAME,rt.READER_LAST_NAME,rt.READER_FATHER_NAME, " +
                "rt.READER_TYPE,rt.REGISTRATION_DATE,rt.leaving_date,rt.ticket_number,student_department, STUDENT_GROUP FROM libraryAdmin.students_table st " +
                "INNER JOIN libraryAdmin.readers_table rt " +
                "ON st.reader_id = rt.reader_id ";
        List<ReaderEntity> students = new LinkedList<>();

        try {
            PreparedStatement preStatement = conn.prepareStatement(sqlGetStudents);
            ResultSet rs = preStatement.executeQuery(sqlGetStudents);
            while (rs.next()) {
                StudentEntity student = new StudentEntity();
                student.setReaderId(rs.getInt("reader_id"));
                student.setReaderName(rs.getString("reader_name"));
                student.setReaderLastName(rs.getString("reader_last_name"));
                student.setFatherName(rs.getString("reader_father_name"));
                student.setReaderType(ReaderType.valueOf(rs.getString("reader_type")));
                student.setRegistrationDate(rs.getDate("registration_date"));
                Date leavingDate = rs.getDate("LEAVING_DATE");
                if (leavingDate != null)
                    student.setLeavingDate(leavingDate);
                student.setTicketNumber(rs.getInt("ticket_number"));
                student.setDepartment(rs.getString("student_department"));
                student.setGroup(rs.getInt("STUDENT_GROUP"));
                students.add(student);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get students");
            throwables.printStackTrace();

        }
        return students;

    }


    public List<StudentUiEntity> getStudentsWithFilter(StudentUiEntity readerEntityFilter, DateSearch dateTake, DateSearch dateRet) {
        String sqlGroup=" group by rt.reader_id, rt.reader_name, rt.reader_last_name, rt.reader_father_name, rt.reader_type, " +
                "rt.registration_date, rt.leaving_date,tt.bookNum,ttt.dNum,st.STUDENT_GROUP,st.student_department";
        String sqlHaving="  ";

        String sqlGetReaders = " select rt.reader_id, rt.reader_name, rt.reader_last_name, rt.reader_father_name, " +
                "rt.reader_type, rt.registration_date, rt.leaving_date,st.student_department,st.STUDENT_GROUP, count(pt.punishment_id) as punNum, " +
                "sum(pt.punishment_payment) as punSum,tt.bookNum ,ttt.dNum " +
                "from readers_table rt " +
                "INNER JOIN libraryAdmin.students_table st " +
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
            if (readerEntityFilter.getDepartment() != null&&!readerEntityFilter.getDepartment().equals("")) {
                sqlFilter += " AND student_department = '" + readerEntityFilter.getDepartment() + "' ";

            }
            if (readerEntityFilter.getGroup() != null) {
                sqlFilter += " AND student_group = " + readerEntityFilter.getGroup();

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


        List<StudentUiEntity> readers = new LinkedList<>();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetReaders);
            ResultSet rs = preStatementReader.executeQuery(sqlGetReaders);
                        while (rs.next()) {
                            StudentUiEntity reader = new StudentUiEntity();
                            reader.setReaderId(rs.getInt("reader_id"));
                            reader.setReaderName(rs.getString("reader_name"));
                            reader.setReaderLastName(rs.getString("reader_last_name"));
                            reader.setFatherName(rs.getString("reader_father_name"));
                            reader.setReaderType(ReaderType.valueOf(rs.getString("reader_type")));
                            reader.setRegistrationDate(rs.getDate("registration_date"));
                            Date leavingDate = rs.getDate("LEAVING_DATE");
                            if (leavingDate != null)
                                reader.setLeavingDate(leavingDate);
                            reader.setDepartment(rs.getString("student_department"));
                            reader.setGroup(rs.getInt("student_group"));
                            reader.setPunishmentNumField(rs.getInt("punNum"));
                            reader.setPaymentField(rs.getInt("punSum"));
                            reader.setDebtField(rs.getInt("dNum"));
                            reader.setBookNumField(rs.getInt("bookNum"));
                            readers.add(reader);
                        }
            } catch (SQLException throwables) {
            System.err.println("can't get students");
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
