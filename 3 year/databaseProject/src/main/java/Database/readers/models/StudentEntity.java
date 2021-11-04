package Database.readers.models;

public class StudentEntity extends ReaderEntity {
    private Integer studentId;
    private String department;
    private Integer group;
    private Integer readerId;

    @Override
    public String toString() {


        return "readerId=" + readerId +
                ", readerName='" + getReaderName() + '\'' +
                ", readerLastName='" + getReaderLastName() + '\'' +
                ", FatherName='" + getFatherName() + '\'' +
                ", readerType=" + getReaderType() +
                ", registrationDate='" + getRegistrationDate() + '\'' +
                ", leavingDate='" + getLeavingDate() + '\'' +
                ", ticketNumber=" + getTicketNumber() +
                ", department='" + getDepartment() + '\'' +
                ", group=" + getGroup();

    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
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

    @Override
    public Integer getReaderId() {
        return readerId;
    }

    @Override
    public void setReaderId(Integer readerId) {
        this.readerId = readerId;
    }
}
