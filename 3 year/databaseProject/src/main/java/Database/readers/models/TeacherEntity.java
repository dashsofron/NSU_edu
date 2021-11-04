package Database.readers.models;

public class TeacherEntity extends ReaderEntity {
    private Integer teacherId;
    private String cathedra;
    private String degree;
    private Integer readerId;

    @Override
    public String toString() {
        return
                "readerId=" + readerId +
                ", readerName='" + getReaderName() + '\'' +
                ", readerLastName='" + getReaderLastName() + '\'' +
                ", FatherName='" + getFatherName() + '\'' +
                ", readerType=" + getReaderType() +
                ", registrationDate='" + getRegistrationDate() + '\'' +
                ", leavingDate='" + getLeavingDate() + '\'' +
                ", ticketNumber=" + getTicketNumber() +
                ", cathedra='" + cathedra + '\'' +
                ", degree='" + degree + '\'';}

    public void setCathedra(String cathedra) {
        this.cathedra = cathedra;
    }
    @Override
    public void setReaderId(Integer readerId) {
        this.readerId = readerId;
    }
    @Override
    public Integer getReaderId() {
        return readerId;
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
