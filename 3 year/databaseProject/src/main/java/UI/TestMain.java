package UI;

import Database.DBInit;
import Database.DBRequests;
import Database.places.models.Place;
import Database.places.models.PlaceType;
import Database.readers.models.ReaderEntity;
import Database.readers.models.ReaderType;
import Database.readers.models.StudentEntity;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class TestMain {
    final static String someUrl = "jdbc:oracle:thin:@";
    final static String port = ":1521";
    final static String courseServerIp = "84.237.50.81";
    final static String courseUsername = "18208_Sofronova";
    final static String coursePassword = "nnhWHc9";

    public static void main(String[] args) throws SQLException {
        DBInit dbInit = DBInit.getInstance();
        dbInit.startConnection(someUrl + courseServerIp + port, courseUsername, coursePassword);
        DBRequests dbRequests = DBRequests.getInstance();
        StudentEntity student1 = new StudentEntity();
        student1.setReaderName("Дарья");
        student1.setReaderType(ReaderType.СТУДЕНТ);
        Place place=new Place();
        place.setPlaceType(PlaceType.АБОНЕМЕНТ);

        List<ReaderEntity> l1=dbRequests.getReaders(null,null,null,null,null);


        List<ReaderEntity> l2=dbRequests.getReaders(null,student1,null,null,null);

        List<ReaderEntity> l3=dbRequests.getReaders(place,student1,null,null,null);
    }
}
