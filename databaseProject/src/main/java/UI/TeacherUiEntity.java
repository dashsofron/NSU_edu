package UI;

import Database.readers.models.ReaderType;

import java.sql.Date;
import java.time.LocalDate;

public class TeacherUiEntity extends ReaderUiEntity{
    private String cathedra;
    private String degree;
    public TeacherUiEntity(Integer readerId, String readerName, String readerLastName, String fatherName,
                           ReaderType readerType, LocalDate registrationDate, LocalDate leavingDate,
                           Integer ticketNumber, String bookNumField, String debtField, String punishmentNumField,
                           String paymentField,String cathedra,String degree) {
        super(readerId, readerName, readerLastName, fatherName, readerType, registrationDate, leavingDate,
                ticketNumber, bookNumField, debtField, punishmentNumField, paymentField);
        setCathedra(cathedra);
        setDegree(degree);
    }
public TeacherUiEntity(){
    super();
}
    public void setCathedra(String cathedra) {
        this.cathedra = cathedra;
    }

    public String getCathedra(){
        return cathedra;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
    public String getDegree(){
        return degree;
    }
}
