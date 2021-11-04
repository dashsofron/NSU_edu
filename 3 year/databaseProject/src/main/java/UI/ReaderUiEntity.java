package UI;

import Database.readers.models.ReaderType;

import java.sql.Date;
import java.time.LocalDate;

public class ReaderUiEntity {
    private Integer readerId;
    private String readerName;
    private String readerLastName;
    private String fatherName;
    private ReaderType readerType;
    private Date registrationDate;
    private Date leavingDate;
    private Integer ticketNumber;
    private Integer bookNumField;
    private Integer debtField;
    private Integer punishmentNumField;
    private Integer paymentField;


    public ReaderUiEntity(Integer readerId, String readerName, String readerLastName, String fatherName, ReaderType readerType,
                          LocalDate registrationDate, LocalDate leavingDate, Integer ticketNumber, String bookNumField,
                          String debtField, String punishmentNumField, String paymentField) {
        this.setReaderId(readerId);
        if (readerName!=null&&!readerName.equals(""))
            this.setReaderName(readerName);
        if (readerLastName!=null&&!readerLastName.equals(""))
            this.setReaderLastName(readerLastName);
        if (fatherName!=null&&!fatherName.equals(""))
            this.setFatherName(fatherName);
        this.setReaderType(readerType);
        if (registrationDate != null)
            this.setRegistrationDate(Date.valueOf(registrationDate));
        if (leavingDate != null)
            this.setLeavingDate(Date.valueOf(leavingDate));
        this.setTicketNumber(ticketNumber);
        if(!bookNumField.equals(""))
        this.setBookNumField(Integer.valueOf(bookNumField));
        if(!debtField.equals(""))
            this.setDebtField(Integer.valueOf(debtField));
        if(!paymentField.equals(""))
            this.setPaymentField(Integer.valueOf(paymentField));
        if(!punishmentNumField.equals(""))
            this.setPunishmentNumField(Integer.valueOf(punishmentNumField));

    }

    public ReaderUiEntity() {

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
        if(fatherName!=null&&fatherName.equals("null"))this.fatherName = "нет";
        else this.fatherName = fatherName;
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

    public Integer getBookNumField() {
        return bookNumField;
    }

    public void setBookNumField(Integer bookNumField) {
        this.bookNumField = bookNumField;
    }

    public Integer getDebtField() {
        return debtField;
    }

    public void setDebtField(Integer debtField) {
        this.debtField = debtField;
    }

    public Integer getPunishmentNumField() {
        return punishmentNumField;
    }

    public void setPunishmentNumField(Integer punishmentNumField) {
        this.punishmentNumField = punishmentNumField;
    }

    public Integer getPaymentField() {
        return paymentField;
    }

    public void setPaymentField(Integer paymentField) {
        this.paymentField = paymentField;
    }
}
