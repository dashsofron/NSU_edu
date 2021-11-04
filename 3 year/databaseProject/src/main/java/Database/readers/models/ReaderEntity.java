package Database.readers.models;

import java.sql.Date;

public class ReaderEntity {
    private Integer readerId;
    private String readerName;
    private String readerLastName;
    private String fatherName;
    private ReaderType readerType;
    private Date registrationDate;
    private Date leavingDate;
    private Integer ticketNumber;

    @Override
    public String toString() {
        return "readerId=" + readerId +
                ", readerName='" + getReaderName() + '\'' +
                ", readerLastName='" + getReaderLastName() + '\'' +
                ", FatherName='" + getFatherName() + '\'' +
                ", readerType=" + getReaderType() +
                ", registrationDate='" + getRegistrationDate() + '\'' +
                ", leavingDate='" + getLeavingDate() + '\'' +
                ", ticketNumber=" + getTicketNumber();
    }

    public Integer getReaderId() {
        return readerId;
    }


    public void setReaderId(Integer readerId) {
        this.readerId = readerId;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getReaderLastName() {
        return readerLastName;
    }

    public void setReaderLastName(String readerLastName) {
        this.readerLastName = readerLastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        if(fatherName==null||!fatherName.equals("null"))
        this.fatherName = fatherName;
        else this.fatherName="нет";
    }

    public ReaderType getReaderType() {
        return readerType;
    }

    public void setReaderType(ReaderType readerType) {
        this.readerType = readerType;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getLeavingDate() {
        return leavingDate;
    }

    public void setLeavingDate(Date leavingDate) {
        this.leavingDate = leavingDate;
    }

    public Integer getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(Integer ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
}
