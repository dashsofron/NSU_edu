package UI;

import Database.readers.models.ReaderType;

import java.sql.Date;
import java.time.LocalDate;

public class StudentUiEntity extends ReaderUiEntity {
    private String department;
    private Integer group;

    public StudentUiEntity(Integer readerId, String readerName, String readerLastName, String fatherName, ReaderType readerType, LocalDate registrationDate,
                           LocalDate leavingDate, Integer ticketNumber, String bookNumField, String debtField, String punishmentNumField, String paymentField,
                           String department,String group) {
        super(readerId, readerName, readerLastName, fatherName, readerType, registrationDate, leavingDate, ticketNumber, bookNumField, debtField, punishmentNumField, paymentField);
    setDepartment(department);
    setGroup(group);
    }

    public StudentUiEntity() {
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }
    public void setGroup(String group) {
        if(group!=null&&!group.equals(""))
        this.group = Integer.valueOf(group);
    }

}
