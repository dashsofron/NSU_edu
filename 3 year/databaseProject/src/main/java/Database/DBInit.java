package Database;

import Database.books.models.Book;
import Database.books.models.BookStatus;
import Database.books.models.BookType;
import Database.files.models.*;
import Database.places.models.Place;
import Database.places.models.PlaceType;
import Database.readers.models.ReaderType;
import Database.readers.models.StudentEntity;
import Database.readers.models.TeacherEntity;
import Database.user.User;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.*;
import java.util.*;

public class DBInit {
    private static DBInit instance;
    private static Connection conn;
    private DBRequests dbRequests;
    private String url = null;

    private DBInit() {

    }

    public static synchronized DBInit getInstance() {
        if (instance == null) {
            instance = new DBInit();
        }
        return instance;
    }

    public static Connection getConn() {
        return conn;
    }

    public Connection startConnection(String url, String username, String password) throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Properties props = new Properties();//или OracleDataSource
        props.setProperty("user", username);
        props.setProperty("password", password);

        TimeZone timeZone = TimeZone.getTimeZone("GMT+7");
        TimeZone.setDefault(timeZone);
        Locale.setDefault(Locale.ENGLISH);
        dbRequests = DBRequests.getInstance();

        this.conn = DriverManager.getConnection(url, props);
        if(dbRequests!=null)dbRequests.setCon();
        this.url = url;
        return this.conn;
    }

    public void init() throws SQLException, UnsupportedEncodingException {
        dbRequests = DBRequests.getInstance();
        drop();
        createTables();
        createSequences();
        setTestData();
    }

    public void initAll() throws SQLException, UnsupportedEncodingException {
        dbRequests = DBRequests.getInstance();
        dropUsersAcc();
        drop();
        addAdmin();
        startConnection(url, "libraryAdmin", "admin");
        createTables();
        createSequences();
        addUsersAcc();
        setTestData();
    }

    public void addAdmin() throws SQLException {
        PreparedStatement preStatement = null;

        String sqlDropUser = "DROP user " + "libraryAdmin";
        try {
            preStatement = conn.prepareStatement(sqlDropUser);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop admin libraryAdmin");

        }
        String dropRoleSql = "drop role library_admin";
        String createRoleSql = "create role library_admin";
        String grantSessionSql = "grant connect to library_admin";
        String grantPrivilegesSql = "grant all PRIVILEGES to library_admin";
        String grantPrivilegeDBASql = "grant DBA to libraryAdmin";


        try {
            preStatement = conn.prepareStatement(dropRoleSql);
            preStatement.executeUpdate();
        } catch (SQLException ignored) {
            System.err.println("can't drop admin role ");
        }
        try {
            preStatement = conn.prepareStatement(createRoleSql);
            preStatement.executeUpdate();
        } catch (SQLException ignored) {
            System.err.println("can't create admin role ");
        }
        try {
            preStatement = conn.prepareStatement(grantSessionSql);
            preStatement.executeUpdate();
        } catch (SQLException ignored) {
            System.err.println("can't grant session admin role ");
        }
        try {
            preStatement = conn.prepareStatement(grantPrivilegesSql);
            preStatement.executeUpdate();
        } catch (SQLException ignored) {
            System.err.println("can't grant privileges admin role ");
        }
//        try {
//            preStatement = conn.prepareStatement(grantPrivilegeDBASql);
//            preStatement.executeUpdate();
//        } catch (SQLException ignored) {
//            System.err.println("can't grant DBA admin role ");
//        }



        String createStartSql = "create user ";
        String createPassSql = " identified by ";
        String grantSql = "grant library_admin to ";
        //String grantTablespaceSql = "create smallfile tablespace libraryAdminPlace datafile '/u01/app/oracle/oradata/ORCL/users.dbf' size 10g";


        String createUserSql = createStartSql + "libraryAdmin" + createPassSql + "admin";
        String userGrantSql = grantSql + "libraryAdmin";
        try {
            preStatement = conn.prepareStatement(createUserSql);
            preStatement.executeUpdate();


        } catch (SQLException ignored) {
            System.err.println("can't create user " + "libraryAdmin");

        }
        try {
            preStatement = conn.prepareStatement(userGrantSql);
            preStatement.executeUpdate();


        } catch (SQLException ignored) {
            System.err.println("can't grant to user " + "libraryAdmin");

        }

        try {
            preStatement = conn.prepareStatement(grantPrivilegeDBASql);
            preStatement.executeUpdate();
        } catch (SQLException ignored) {
            System.err.println("can't grant DBA admin role ");
        }
//        try {
//            preStatement = conn.prepareStatement(grantTablespaceSql);
//            preStatement.executeUpdate();
//
//
//        } catch (SQLException ignored) {
//            System.err.println("can't create tablespace");
//
//        }


    }

    public void addUsersAcc() {
        String createRoleSql = "create role library_user";
        PreparedStatement preStatement = null;

        try {
            preStatement = conn.prepareStatement(createRoleSql);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't create role ");

        }

        String roleGrant = "grant connect to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }

        roleGrant = "grant select on libraryAdmin.sq_record to library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant  seq to role ");

        }

        roleGrant = "grant select on libraryAdmin.sq_reader to library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant  seq to role ");

        }

        roleGrant = "grant select on libraryAdmin.sq_student to library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant  seq to role ");

        }
        roleGrant = "grant select on libraryAdmin.sq_teacher to library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant  seq to role ");

        }

        roleGrant = "grant select on libraryAdmin.sq_ticket to library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant  seq to role ");

        }

        roleGrant = "grant select on libraryAdmin.USER_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }

        roleGrant = "grant update on libraryAdmin.USER_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }

        roleGrant = "grant select on libraryAdmin.TEACHERS_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }

        roleGrant = "grant alter on libraryAdmin.TEACHERS_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }
        roleGrant = "grant insert on libraryAdmin.TEACHERS_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }


        roleGrant = "grant select on libraryAdmin.STUDENTS_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }

        roleGrant = "grant alter on libraryAdmin.STUDENTS_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }
        roleGrant = "grant insert on libraryAdmin.STUDENTS_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }


        roleGrant = "grant select on libraryAdmin.READERS_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }

        roleGrant = "grant alter on libraryAdmin.READERS_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }
        roleGrant = "grant insert on libraryAdmin.READERS_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }

        roleGrant = "grant select on libraryAdmin.TAKEN_BOOKS_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }

        roleGrant = "grant insert on libraryAdmin.TAKEN_BOOKS_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }

        roleGrant = "grant update on libraryAdmin.TAKEN_BOOKS_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }

        roleGrant = "grant alter on libraryAdmin.TAKEN_BOOKS_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }

        roleGrant = "grant select on libraryAdmin.PUNISHMENTS_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }

        roleGrant = "grant select on libraryAdmin.PLACES_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }

        roleGrant = "grant select on libraryAdmin.BOOK_TYPES_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }

        roleGrant = "grant select on libraryAdmin.BOOKS_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }

        roleGrant = "grant update on libraryAdmin.BOOKS_TABLE to  library_user";
        try {
            preStatement = conn.prepareStatement(roleGrant);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't grant to role ");

        }


        String createStartSql = "create user ";
        String createPassSql = " identified by ";
        String grantSql = "grant library_user to ";

        Map<String, String> users = new HashMap<>();
        users.put("dashsofron", "dasha111");
        users.put("ivan", "ivanivan");
        users.put("darishka", "darishka222");
        users.put("ivanIvenko", "ivenkoIvan");
        users.put("teacherJenya", "teacher666");
        users.put("teacherAndrew", "teacher007");
        users.put("bestteacher", "jejhabc");
        users.put("user8", "newuser");

        for (String username : users.keySet()) {
            String creteUserSql = createStartSql + username + createPassSql + users.get(username);
            String userGrantSql = grantSql + username;
            try {
                preStatement = conn.prepareStatement(creteUserSql);
                preStatement.executeUpdate();


            } catch (SQLException ignored) {
                System.err.println("can't create user " + username);

            }
            try {
                preStatement = conn.prepareStatement(userGrantSql);
                preStatement.executeUpdate();


            } catch (SQLException ignored) {
                System.err.println("can't grant role to user " + username);

            }
        }


    }

    public void dropUsersAcc() {
        PreparedStatement preStatement = null;

        List<User> users = null;
        try {
            users = dbRequests.getUsers();
        } catch (Exception e) {
            System.err.println("can't get Users");
        }
        for (User user : users) {
            if (!user.getUserRole().equals(UserRole.АДМИНИСТРАТОР)) {
                String sqlDropUser = "DROP user " + user.getUsername();
                try {
                    preStatement = conn.prepareStatement(sqlDropUser);
                    preStatement.executeUpdate();

                } catch (SQLException ignored) {
                    System.err.println("can't drop user " + user.getUsername());

                }
            }
        }
        String sqlDropRole = "DROP role  library_user ";
        try {
            preStatement = conn.prepareStatement(sqlDropRole);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop role ");

        }


    }

    public void setTestData() throws SQLException {
        StudentEntity student1 = new StudentEntity();
        student1.setReaderName("Дарья");
        student1.setReaderLastName("Софронова");
        student1.setFatherName(null);
        student1.setReaderType(ReaderType.СТУДЕНТ);
        student1.setRegistrationDate(Date.valueOf("2018-10-10"));
        student1.setDepartment("фит");
        student1.setGroup(18208);

        User user1 = new User();
        user1.setUserId(1);
        user1.setUsername("dashsofron");
        user1.setPassword("dasha111");
        user1.setUserRole(UserRole.ПОЛЬЗОВАТЕЛЬ);
        user1.setReaderId(1);

        StudentEntity student2 = new StudentEntity();
        student2.setReaderName("Иван");
        student2.setReaderLastName("Иванов");
        student2.setFatherName(null);
        student2.setReaderType(ReaderType.СТУДЕНТ);
        student2.setRegistrationDate(Date.valueOf("2018-10-17"));
        student2.setDepartment("фф");
        student2.setGroup(18308);

        User user2 = new User();
        user2.setUserId(2);
        user2.setUsername("ivan");
        user2.setPassword("ivanivan");
        user2.setUserRole(UserRole.ПОЛЬЗОВАТЕЛЬ);
        user2.setReaderId(2);

        StudentEntity student3 = new StudentEntity();
        student3.setReaderName("Дарья");
        student3.setReaderLastName("Александрова");
        student3.setFatherName("Александровна");
        student3.setReaderType(ReaderType.СТУДЕНТ);
        student3.setRegistrationDate(Date.valueOf("2018-10-15"));
        student3.setDepartment("фит");
        student3.setGroup(18201);

        User user3 = new User();
        user3.setUserId(3);
        user3.setUsername("darishka");
        user3.setPassword("darishka222");
        user3.setUserRole(UserRole.ПОЛЬЗОВАТЕЛЬ);
        user3.setReaderId(3);

        StudentEntity student4 = new StudentEntity();
        student4.setReaderName("Иван");
        student4.setReaderLastName("Ивенко");
        student4.setFatherName("Иванович");
        student4.setReaderType(ReaderType.СТУДЕНТ);
        student4.setRegistrationDate(Date.valueOf("2018-10-17"));
        student4.setDepartment("фф");
        student4.setGroup(18305);

        User user4 = new User();
        user4.setUserId(4);
        user4.setUsername("ivanIvenko");
        user4.setPassword("ivenkoIvan");
        user4.setUserRole(UserRole.ПОЛЬЗОВАТЕЛЬ);
        user4.setReaderId(4);

         dbRequests.addReader(student1);
        dbRequests.addReader(student2);
        dbRequests.addReader(student3);
        dbRequests.addReader(student4);

        TeacherEntity teacher1 = new TeacherEntity();
        teacher1.setReaderName("Евгений");
        teacher1.setReaderLastName("Петров");
        teacher1.setFatherName("Иванович");
        teacher1.setReaderType(ReaderType.УЧИТЕЛЬ);
        teacher1.setRegistrationDate(Date.valueOf("2000-10-17"));
        teacher1.setCathedra("КС");
        teacher1.setDegree("старший преподаватель");

        User user5 = new User();
        user5.setUserId(5);
        user5.setUsername("teacherJenya");
        user5.setPassword("teacher666");
        user5.setUserRole(UserRole.ПОЛЬЗОВАТЕЛЬ);
        user5.setReaderId(5);

        TeacherEntity teacher2 = new TeacherEntity();
        teacher2.setReaderName("Андрей");
        teacher2.setReaderLastName("Павлов");
        teacher2.setFatherName("Иванович");
        teacher2.setReaderType(ReaderType.УЧИТЕЛЬ);
        teacher2.setRegistrationDate(Date.valueOf("2002-10-17"));
        teacher2.setCathedra("КТ");
        teacher2.setDegree("доцент");

        User user6 = new User();
        user6.setUserId(6);
        user6.setUsername("teacherAndrew");
        user6.setPassword("teacher007");
        user6.setUserRole(UserRole.ПОЛЬЗОВАТЕЛЬ);
        user6.setReaderId(6);

        TeacherEntity teacher3 = new TeacherEntity();
        teacher3.setReaderName("Дарья");
        teacher3.setReaderLastName("Алексевна");
        teacher3.setFatherName("Софронова");
        teacher3.setReaderType(ReaderType.УЧИТЕЛЬ);
        teacher3.setRegistrationDate(Date.valueOf("2019-10-17"));
        teacher3.setCathedra("КТ");
        teacher3.setDegree("преподаватель");

        User user7 = new User();
        user7.setUserId(7);
        user7.setUsername("bestteacher");
        user7.setPassword("jejhabc");
        user7.setUserRole(UserRole.ПОЛЬЗОВАТЕЛЬ);
        user7.setReaderId(7);

        User user8 = new User();
        user8.setUserId(8);
        user8.setUsername("user8");
        user8.setPassword("newuser");
        user8.setUserRole(UserRole.ПОЛЬЗОВАТЕЛЬ);
        user8.setReaderId(8);

        dbRequests.addUser(user1);
        dbRequests.addUser(user2);
        dbRequests.addUser(user3);
        dbRequests.addUser(user4);
        dbRequests.addUser(user5);
        dbRequests.addUser(user6);
        dbRequests.addUser(user7);
        dbRequests.addUser(user8);

        dbRequests.addReader(teacher1);
        dbRequests.addReader(teacher2);
        dbRequests.addReader(teacher3);


        Place place1 = new Place();
        place1.setPlaceName("Абонемент 1");
        place1.setPlaceAddress("Пирогова 14");
        place1.setPlaceType(PlaceType.АБОНЕМЕНТ);

        Place place2 = new Place();
        place2.setPlaceName("Читальный зал НГУ");
        place2.setPlaceAddress("Пирогова 1");
        place2.setPlaceType(PlaceType.ЧИТАЛЬНЫЙ_ЗАЛ);

        Place place3 = new Place();
        place3.setPlaceName("Абонемент 2");
        place3.setPlaceAddress("Пирогова 10");
        place3.setPlaceType(PlaceType.АБОНЕМЕНТ);

        Place place4 = new Place();
        place4.setPlaceName("Читальный зал за НГУ");
        place4.setPlaceAddress("Пирогова 1/2");
        place4.setPlaceType(PlaceType.ЧИТАЛЬНЫЙ_ЗАЛ);

        dbRequests.addPlace(place1);
        dbRequests.addPlace(place2);
        dbRequests.addPlace(place3);
        dbRequests.addPlace(place4);


        BookType type1 = new BookType();
        type1.setBookName("Операционные системы");
        type1.setBookAuthor("Иртегов Д.В.");
        type1.setGenre(BookGenre.IT);

        dbRequests.addBookType(type1);

        Book book1 = new Book();
        book1.setType(type1);
        book1.setPlace(place1);
        book1.setStatus(BookStatus.ДОСТУПНО);

        Book book2 = new Book();
        book2.setType(type1);
        book2.setPlace(place2);
        book2.setStatus(BookStatus.ДОСТУПНО);

        Book book3 = new Book();
        BookType type2 = new BookType();
        book3.setStatus(BookStatus.ДОСТУПНО);
        book3.setPlace(place2);
        type2.setBookAuthor("Пирогов А.А.");
        type2.setBookName("Книга о своем");
        type2.setGenre(BookGenre.АНГЛИЙСКИЙ_ЯЗЫК);

        dbRequests.addBookType(type2);

        book3.setType(type2);

        dbRequests.addBook(book1);
        dbRequests.addBook(book2);
        dbRequests.addBook(book3);

        Punishment punishment1 = new Punishment();
        punishment1.setPunishmentType(PunishmentType.ШТРАФ);
        punishment1.setReaderId(1);
        punishment1.setPayment(100);
        punishment1.setStatus(PunishmentStatus.НУЖНО_ОПЛАТИТЬ);
        punishment1.setReason("Потеря книги");
        dbRequests.addPunishment(punishment1);

        Punishment punishment2 = new Punishment();
        punishment2.setPunishmentType(PunishmentType.БЛОКИРОВКА);
        punishment2.setReaderId(1);
        punishment2.setStartDate(Date.valueOf("2018-1-1"));
        punishment2.setEndDate(Date.valueOf("2022-8-31"));
        punishment2.setStatus(PunishmentStatus.ЗАБЛОКИРОВАН);
        punishment2.setReason("Долгое держание множества книг");
        dbRequests.addPunishment(punishment2);

        //TODO сделать изменение статусов книг при добавлении записи о взятии
        BookTakingRecord record1 = new BookTakingRecord();
        record1.setRecordId(1);
        record1.setBookId(1);
        record1.setReaderId(1);
        record1.setStatus(BookStatus.ДОСТУПНО);
        record1.setDateOfTaking(Date.valueOf("2018-1-1"));
        record1.setDateOfReturning(Date.valueOf("2022-8-31"));


        BookTakingRecord record2 = new BookTakingRecord();
        record2.setRecordId(1);
        record2.setBookId(3);
        record2.setReaderId(1);
        record2.setStatus(BookStatus.ДОСТУПНО);
        record2.setDateOfTaking(Date.valueOf("2018-1-1"));
        record2.setDateOfReturning(Date.valueOf("2022-8-31"));
        dbRequests.addRecord(record1);
        dbRequests.addRecord(record2);
        dbRequests.updateBookStatus(3,BookStatus.ГОТОВИТСЯ,null);
        dbRequests.updateBookStatus(1,BookStatus.ВЗЯТО,null);
    }

    public void drop() {
        String sqlDropReaders = "DROP TABLE  libraryAdmin.readers_table";
        String sqlDropStudents = "DROP TABLE  libraryAdmin.students_table";
        String sqlDropTeachers = "DROP TABLE  libraryAdmin.teachers_table";
        String sqlDropBookTypes = "DROP TABLE  libraryAdmin.book_types_table";
        String sqlDropBooks = "DROP TABLE  libraryAdmin.books_table";
        String sqlDropTakenBooks = "DROP TABLE  libraryAdmin.taken_books_table";
        String sqlDropPunishments = "DROP TABLE  libraryAdmin.punishments_table";
        String sqlDropPlaces = "DROP TABLE  libraryAdmin.places_table";

        String sqlDropReaderSeq = "DROP SEQUENCE libraryAdmin.sq_reader";
        String sqlDropStudentSeq = "DROP SEQUENCE libraryAdmin.sq_student";
        String sqlDropTeacherSeq = "DROP SEQUENCE libraryAdmin.sq_teacher";
        String sqlDropTicketSeq = "DROP SEQUENCE libraryAdmin.sq_ticket";
        String sqlDropBookTypeSeq = "DROP SEQUENCE libraryAdmin.sq_book_type";
        String sqlDropBookSeq = "DROP SEQUENCE libraryAdmin.sq_book";
        String sqlDropRecordSeq = "DROP SEQUENCE libraryAdmin.sq_record";
        String sqlDropPunishmentSeq = "DROP SEQUENCE libraryAdmin.sq_punishment";
        String sqlDropPlaceSeq = "DROP SEQUENCE libraryAdmin.sq_place";


        PreparedStatement preStatement = null;
        String sqlDropUsers = "DROP TABLE  libraryAdmin.user_table";

        try {
            preStatement = conn.prepareStatement(sqlDropUsers);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop users table");

        }


        String sqlDropUserSeq = "DROP SEQUENCE libraryAdmin.sq_user";

        try {
            preStatement = conn.prepareStatement(sqlDropUserSeq);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop user sequence");

        }

        try {
            preStatement = conn.prepareStatement(sqlDropTakenBooks);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop records table");

        }

        try {
            preStatement = conn.prepareStatement(sqlDropPunishments);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop punishments table");

        }

        try {
            preStatement = conn.prepareStatement(sqlDropStudents);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop students table");
        }
        try {
            preStatement = conn.prepareStatement(sqlDropTeachers);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop teachers table");
        }

        try {
            preStatement = conn.prepareStatement(sqlDropReaders);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop readers table");

        }
        try {
            preStatement = conn.prepareStatement(sqlDropBooks);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop books table");

        }

        try {
            preStatement = conn.prepareStatement(sqlDropBookTypes);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop book types  table");

        }

        try {
            preStatement = conn.prepareStatement(sqlDropPlaces);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop places table");

        }

        try {
            preStatement = conn.prepareStatement(sqlDropReaderSeq);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop readers sequence");

        }
        try {
            preStatement = conn.prepareStatement(sqlDropStudentSeq);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop students sequence");

        }
        try {
            preStatement = conn.prepareStatement(sqlDropTeacherSeq);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop teachers sequence");

        }
        try {
            preStatement = conn.prepareStatement(sqlDropTicketSeq);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop ticket sequence");

        }
        try {
            preStatement = conn.prepareStatement(sqlDropBookTypeSeq);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop book type sequence");

        }
        try {
            preStatement = conn.prepareStatement(sqlDropBookSeq);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop book sequence");

        }

        try {
            preStatement = conn.prepareStatement(sqlDropRecordSeq);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop record sequence");

        }
        try {
            preStatement = conn.prepareStatement(sqlDropPunishmentSeq);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop punishment sequence");

        }

        try {
            preStatement = conn.prepareStatement(sqlDropPlaceSeq);
            preStatement.executeUpdate();

        } catch (SQLException ignored) {
            System.err.println("can't drop places sequence");

        }

    }

    public void createTables() {
        PreparedStatement preStatement = null;

        String sqlUserTable = "CREATE TABLE user_table (" +
                "user_id number(10) PRIMARY KEY, " +
                "username varchar(30) NOT NULL, " +
                "password varchar(30) NOT NULL, " +
                "user_role varchar(30) NOT NULL CHECK (user_role IN ('ПОЛЬЗОВАТЕЛЬ','АДМИНИСТРАТОР')  )," +
                "reader_id number(10))";

        try {
            preStatement = conn.prepareStatement(sqlUserTable);
            preStatement.executeUpdate();

        } catch (SQLException throwables) {
            System.out.println("can't create users table");
            throwables.printStackTrace();
        }


        String sqlCreateReadersTable = "CREATE TABLE readers_table (" +
                "reader_id number(10) PRIMARY KEY, " +
                "reader_name varchar(30) NOT NULL, " +
                "reader_last_name varchar(50) NOT NULL, " +
                "reader_father_name varchar(50), " +
                "reader_type varchar(50) NOT NULL CHECK ( reader_type IN('СТУДЕНТ','ЧИТАТЕЛЬ','УЧИТЕЛЬ') ), " +
                "registration_date date DEFAULT sysdate , " +
                "leaving_date date, " +
                "ticket_number number(10) NOT NULL, " +
                "CONSTRAINT leaving_date CHECK (leaving_date>=registration_date)" +
                ")";


        String sqlCreateStudentsTable = "CREATE TABLE students_table (" +
                "student_id number(10) PRIMARY KEY, " +
                "student_department varchar(50) NOT NULL, " +
                "student_group number(6) NOT NULL ," +
                "reader_id number(10) unique, " +
                "CONSTRAINT READER_ID_FOR_STUDENT FOREIGN KEY (reader_id) REFERENCES readers_table)";


        String sqlCreateTeachersTable = "CREATE TABLE teachers_table (" +
                "teacher_id number(10) PRIMARY KEY, " +
                "teacher_cathedra varchar(50) NOT NULL, " +
                "teacher_degree varchar(50) ," +
                "reader_id number(10) unique, " +
                "CONSTRAINT READER_ID_FOR_TEACHER FOREIGN KEY (reader_id) REFERENCES readers_table)";


        String sqlCreateBookTypesTable = "CREATE TABLE book_types_table (" +
                "book_type_id number(10) PRIMARY KEY, " +
                "book_name varchar(100) unique NOT NULL, " +
                "book_author varchar(100), " +
                "book_genre varchar(100) " +
                ")";


        String sqlCreateBooksTable = "CREATE TABLE books_table (" +
                "book_id number(10) PRIMARY KEY, " +
                "book_type_id number(10) NOT NULL ," +
                "book_place_id number(10) NOT NULL ," +
                "book_status varchar(100) NOT NULL CHECK ( book_status IN('ПОТЕРЯНО','ВЗЯТО','ДОСТУПНО','ГОТОВИТСЯ','ЗАКАЗАНО') ), " +
                "CONSTRAINT BOOK_TYPE_ID FOREIGN KEY (book_type_id) REFERENCES book_types_table(book_type_id), " +
                "CONSTRAINT PLACE_ID FOREIGN KEY (book_place_id) REFERENCES places_table(place_id)" +
                ")";


        String sqlCreateTakenBooksTable = "CREATE TABLE taken_books_table (" +
                "record_id number(10) PRIMARY KEY, " +
                "reader_id number(10) NOT NULL ," +
                "book_id number(10) NOT NULL ," +
                "taking_date date DEFAULT sysdate ," +
                "returning_date date DEFAULT add_months(sysdate,+1) ," +
                "record_status varchar(30) NOT NULL check ( record_status  IN ('ПОТЕРЯНО','ВЗЯТО','ДОСТУПНО','ГОТОВИТСЯ','ЗАКАЗАНО','ВОЗВРАЩЕНО') ) ," +
                "CONSTRAINT returning_date CHECK (returning_date>=taking_date), " +
                "CONSTRAINT READER_ID_FOR_BOOK FOREIGN KEY (reader_id) REFERENCES readers_table(reader_id)," +
                "CONSTRAINT BOOK_ID FOREIGN KEY (book_id) REFERENCES books_table(book_id)" +
                ")";


        String sqlCreatePunishmentsTable = "CREATE TABLE punishments_table (" +
                "punishment_id number(10) PRIMARY KEY, " +
                "reader_id number(10) NOT NULL, " +
                "punishment_type varchar(30) NOT NULL CHECK ( punishment_type IN ('ШТРАФ','БЛОКИРОВКА') ), " +
                "punishment_start_date date DEFAULT sysdate, " +
                "punishment_end_date date DEFAULT add_months(sysdate,+2), " +
                "punishment_payment number(10), " +
                "punishment_status varchar(30) CHECK ( punishment_status IN('ЗАБЛОКИРОВАН','ОПЛАЧЕНО','НУЖНО_ОПЛАТИТЬ','ЗАВЕРШЕНО') ), " +
                "punishment_comment varchar(200), " +
                "CONSTRAINT punishment_end_date CHECK (punishment_end_date>=punishment_start_date), " +
                "CONSTRAINT READER_ID_FOR_PUNISHMENT FOREIGN KEY (reader_id) REFERENCES readers_table(reader_id)" +
                ")";


        String sqlCreatePlacesTable = "CREATE TABLE places_table (" +
                "place_id number(10) PRIMARY KEY, " +
                "place_name varchar(60) unique NOT NULL ," +
                "place_type varchar(30) NOT NULL CHECK ( place_type IN ('ЧИТАЛЬНЫЙ_ЗАЛ','АБОНЕМЕНТ'))," +
                "place_address varchar (100) NOT NULL" +
                ")";


        try {
            preStatement = conn.prepareStatement(sqlCreateReadersTable);
            preStatement.executeUpdate();

        } catch (SQLException throwables) {
            System.out.println("can't create readers table");
            throwables.printStackTrace();
        }
        try {
            preStatement = conn.prepareStatement(sqlCreateStudentsTable);
            preStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("can't create students table");
            throwables.printStackTrace();
        }
        try {
            preStatement = conn.prepareStatement(sqlCreateTeachersTable);
            preStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("can't create teachers table");
            throwables.printStackTrace();
        }

        try {
            preStatement = conn.prepareStatement(sqlCreateBookTypesTable);
            preStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("can't create book types table");
            throwables.printStackTrace();
        }
        try {
            preStatement = conn.prepareStatement(sqlCreatePlacesTable);
            preStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("can't create places table");
            throwables.printStackTrace();
        }
        try {
            preStatement = conn.prepareStatement(sqlCreateBooksTable);
            preStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("can't create books table");
            throwables.printStackTrace();
        }
        try {
            preStatement = conn.prepareStatement(sqlCreateTakenBooksTable);
            preStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("can't create taken books table");
            throwables.printStackTrace();
        }
        try {
            preStatement = conn.prepareStatement(sqlCreatePunishmentsTable);
            preStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("can't create punishments table");
            throwables.printStackTrace();
        }
    }

    public void createSequences() throws SQLException {

        String sqlCreateReaderSequence = "CREATE SEQUENCE sq_reader " +
                "START WITH 1 " +
                "INCREMENT BY 1 " +
                "NOMAXVALUE";
        String sqlCreateStudentSequence = "CREATE SEQUENCE sq_student " +
                "START WITH 1 " +
                "INCREMENT BY 1 " +
                "NOMAXVALUE";
        String sqlCreateTeacherSequence = "CREATE SEQUENCE sq_teacher " +
                "START WITH 1 " +
                "INCREMENT BY 1 " +
                "NOMAXVALUE";
        //+
        String sqlCreateTicketSequence = "CREATE SEQUENCE sq_ticket " +
                "START WITH 1 " +
                "INCREMENT BY 1 " +
                "NOMAXVALUE";


        String sqlCreateBookTypeSequence = "CREATE SEQUENCE sq_book_type " +
                "START WITH 1 " +
                "INCREMENT BY 1 " +
                "NOMAXVALUE";
        String sqlCreateBookSequence = "CREATE SEQUENCE sq_book " +
                "START WITH 1 " +
                "INCREMENT BY 1 " +
                "NOMAXVALUE";
        String sqlCreateRecordSequence = "CREATE SEQUENCE sq_record " +
                "START WITH 1 " +
                "INCREMENT BY 1 " +
                "NOMAXVALUE";
        String sqlCreatePunishmentSequence = "CREATE SEQUENCE sq_punishment " +
                "START WITH 1 " +
                "INCREMENT BY 1 " +
                "NOMAXVALUE";
        String sqlCreatePlaceSequence = "CREATE SEQUENCE sq_place " +
                "START WITH 1 " +
                "INCREMENT BY 1 " +
                "NOMAXVALUE";


        PreparedStatement readerStatement = conn.prepareStatement(sqlCreateReaderSequence);
        PreparedStatement studentStatement = conn.prepareStatement(sqlCreateStudentSequence);
        PreparedStatement teacherStatement = conn.prepareStatement(sqlCreateTeacherSequence);
        PreparedStatement ticketStatement = conn.prepareStatement(sqlCreateTicketSequence);
        PreparedStatement bookTypeStatement = conn.prepareStatement(sqlCreateBookTypeSequence);
        PreparedStatement bookStatement = conn.prepareStatement(sqlCreateBookSequence);
        PreparedStatement recordStatement = conn.prepareStatement(sqlCreateRecordSequence);
        PreparedStatement punishmentStatement = conn.prepareStatement(sqlCreatePunishmentSequence);
        PreparedStatement placeStatement = conn.prepareStatement(sqlCreatePlaceSequence);


        ticketStatement.executeUpdate();
        readerStatement.executeUpdate();
        studentStatement.executeUpdate();
        teacherStatement.executeUpdate();
        bookTypeStatement.executeUpdate();
        bookStatement.executeUpdate();
        recordStatement.executeUpdate();
        punishmentStatement.executeUpdate();
        placeStatement.executeUpdate();

        String sqlCreateUserSequence = "CREATE SEQUENCE sq_user " +
                "START WITH 1 " +
                "INCREMENT BY 1 " +
                "NOMAXVALUE";

        try {
            PreparedStatement userStatement = conn.prepareStatement(sqlCreateUserSequence);

            userStatement.executeUpdate();

        } catch (SQLException throwables) {
            System.out.println("can't create user seq");
            throwables.printStackTrace();
        }

    }

    //TODO сделать триггеры
    public void createInsertTriggers() throws SQLException {
        //+
        String sqlCreateReaderTrigger = "CREATE OR REPLACE TRIGGER reader_insert_trig " +
                "before INSERT " +
                "ON readers_table " +
                "FOR EACH ROW " +
                "BEGIN " +
                "SELECT sq_reader.nextval INTO :new.reader_id FROM dual; " +
                "END;";

        PreparedStatement triggerStatement = conn.prepareStatement(sqlCreateReaderTrigger);
        triggerStatement.executeUpdate();
        //+
        String sqlCreateStudentTrigger = "CREATE OR REPLACE TRIGGER student_insert_trig " +
                "before INSERT " +
                "ON students_table " +
                "FOR EACH ROW " +
                "BEGIN " +
                "SELECT sq_student.nextval INTO :new.student_id FROM dual; " +
                "END;";

        triggerStatement = conn.prepareStatement(sqlCreateStudentTrigger);
        triggerStatement.executeUpdate();

        String sqlCreateReaderTriggerTicket = "CREATE OR REPLACE TRIGGER reader_insert_trig1 " +
                "before INSERT " +
                "ON readers_table " +
                "FOR EACH ROW " +
                "BEGIN " +
                "SELECT sq_ticket.nextval INTO :new.ticket_number FROM dual; " +
                "END;";
    }

    public void createDeleteTriggers() throws SQLException {
        //+
        String sqlDeleteStudentTrigger = "CREATE OR REPLACE TRIGGER delete_student before DELETE ON readers_table " +
                "FOR EACH ROW " +
                "BEGIN " +
                "DELETE from students_table " +
                "WHERE students_table.reader_id =:OLD.reader_id; " +
                "END;";
        PreparedStatement triggerStatement = conn.prepareStatement(sqlDeleteStudentTrigger);
        triggerStatement.executeUpdate();

    }


}
