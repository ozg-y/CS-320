import java.awt.image.BufferedImage;

public class Student {

    private String studentName;
    private String studentSurname;
    private BufferedImage studentProfilePhoto;
    private String studentEmail;
    private String studentPassword;
    private DatabaseOperation databaseOperation;


    public Student(String _studentName,String _studentSurname,BufferedImage _studentProfilePhoto,String _studentEmail,String _studentPassword,DatabaseOperation _databaseOperation){
        studentName = _studentName;
        studentSurname = _studentSurname;
        studentProfilePhoto = _studentProfilePhoto;
        studentEmail = _studentEmail;
        studentPassword = _studentPassword;
        databaseOperation = _databaseOperation;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSurname() {
        return studentSurname;
    }

    public void setStudentSurname(String studentSurname) {
        this.studentSurname = studentSurname;
    }

    public BufferedImage getStudentProfilePhoto() {
        return studentProfilePhoto;
    }

    public void setStudentProfilePhoto(BufferedImage studentProfilePhoto) {
        this.studentProfilePhoto = studentProfilePhoto;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public void postProduct(Product p){

    }

    public void manageProduct(Product p){

    }

}
