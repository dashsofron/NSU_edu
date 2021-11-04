package Database.files.models;

import java.sql.Date;
import java.time.LocalDate;

public class Punishment {

    private Integer punishmentId;
    private Integer readerId;
    private PunishmentType punishmentType;
    private PunishmentStatus status;
    private Date startDate;
    private Date endDate;
    private Integer payment;
    private String reason;

    public Punishment(Integer punishmentId, String readerId, PunishmentType punishmentType, PunishmentStatus status,
                      LocalDate startDate, LocalDate endDate, String payment, String reason) {
        this.setReaderId(punishmentId);
        if (!readerId.equals(""))
            this.setReaderId(Integer.valueOf(readerId));
        this.setPunishmentType(punishmentType);
        this.setStatus(status);
        if (startDate != null)
            this.setStartDate(Date.valueOf(startDate));
        if (endDate != null)
            this.setEndDate(Date.valueOf(endDate));
        if (!payment.equals(""))
            this.setPayment(Integer.valueOf(payment));
        if (reason != null && !reason.equals(""))
            setReason(reason);

    }

//    public Punishment(Integer punishmentId, String readerId, PunishmentType punishmentType, PunishmentStatus status,
//                      LocalDate startDate, LocalDate endDate, String payment, String reason) {
//        setReaderId(punishmentId);
//        if(readerId!=null&&!readerId.equals(""))
//        setReaderId(Integer.valueOf(readerId));
//        setPunishmentType(punishmentType);
//        setStatus(status);
//        if (startDate != null)
//            this.setStartDate(Date.valueOf(startDate));
//        if (endDate != null)
//            this.setEndDate(Date.valueOf(endDate));
//        setPayment(payment);
//        if (reason != null && !reason.equals(""))
//            setReason(reason);
//
//    }

    public Punishment() {

    }

    public Integer getPunishmentId() {
        return punishmentId;
    }

    public void setPunishmentId(Integer punishmentId) {
        this.punishmentId = punishmentId;
    }

    public Integer getReaderId() {
        return readerId;
    }

    public void setReaderId(Integer readerId) {
        this.readerId = readerId;
    }

    public PunishmentType getPunishmentType() {
        return punishmentType;
    }

    public void setPunishmentType(PunishmentType punishmentType) {
        this.punishmentType = punishmentType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public PunishmentStatus getStatus() {
        return status;
    }

    public void setStatus(PunishmentStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
