package UI.readers;

import Database.DBRequests;
import Database.readers.models.ReaderEntity;
import Database.readers.models.ReaderType;
import Database.readers.models.StudentEntity;
import Database.readers.models.TeacherEntity;
import UI.ErrorController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpdateReaderController {
    private final DBRequests dbRequests = DBRequests.getInstance();
    @FXML
    TextField readerNameField;
    @FXML
    TextField readerFatherNameField;
    @FXML
    TextField readerLastNameField;
    @FXML
    DatePicker readerRegistrationDateField;
    @FXML
    DatePicker readerLeavingDateField;
    @FXML
    ComboBox<ReaderType> readerTypeBox;
    @FXML
    TextField studentDepartment;
    @FXML
    TextField studentGroup;
    @FXML
    TextField teacherCathedra;
    @FXML
    TextField teacherDegree;
    @FXML
    Button updateReaderButton;
    @FXML
    TextField readerIdField;
    @FXML
    TextField infoArea;
    @FXML
    CheckBox getOldInfoCheck;

    public void initController() {
        readerTypeBox.getItems().addAll(
                ReaderType.СТУДЕНТ,
                ReaderType.УЧИТЕЛЬ,
                ReaderType.ЧИТАТЕЛЬ
        );
    }

    @FXML
    public void getOldInfo() {
        cleanFields();
        if (getOldInfoCheck.isSelected() && readerIdField.getText() != null) {
            ReaderEntity readerEntity = dbRequests.getReader(Integer.valueOf(readerIdField.getText()));
            if(readerEntity!=null) {
                readerIdField.setText(readerEntity.getReaderId().toString());
                readerTypeBox.setValue(readerEntity.getReaderType());
                readerNameField.setText(readerEntity.getReaderName());
                readerFatherNameField.setText(readerEntity.getFatherName());
                readerLastNameField.setText(readerEntity.getReaderLastName());
                readerRegistrationDateField.setValue(readerEntity.getRegistrationDate().toLocalDate());
                if (readerEntity.getLeavingDate() != null)
                    readerLeavingDateField.setValue(readerEntity.getLeavingDate().toLocalDate());
                switch (readerEntity.getReaderType()) {
                    case СТУДЕНТ:
                        StudentEntity studentEntity = (StudentEntity) readerEntity;
                        teacherCathedra.setVisible(false);
                        teacherDegree.setVisible(false);
                        studentGroup.setVisible(true);
                        studentDepartment.setVisible(true);
                        studentDepartment.setText(studentEntity.getDepartment());
                        studentGroup.setText(studentEntity.getGroup().toString());
                        break;
                    case УЧИТЕЛЬ:
                        TeacherEntity teacherEntity = (TeacherEntity) readerEntity;
                        teacherCathedra.setVisible(true);
                        teacherDegree.setVisible(true);
                        studentGroup.setVisible(false);
                        studentDepartment.setVisible(false);
                        teacherCathedra.setText(teacherEntity.getCathedra());
                        teacherDegree.setText(teacherEntity.getDegree());
                        break;
                }
            }

        }

    }

    public void cleanFields() {
        readerTypeBox.setValue(null);
        readerNameField.clear();
        readerFatherNameField.clear();
        readerLastNameField.clear();
        readerRegistrationDateField.setValue(null);
        readerLeavingDateField.setValue(null);

        teacherCathedra.clear();
        teacherDegree.clear();
        studentGroup.clear();
        studentDepartment.clear();

    }


    @FXML
    public void showExtraFields() {
        ReaderType typeGet = readerTypeBox.getValue();
        switch (typeGet) {
            case СТУДЕНТ:
                teacherCathedra.setVisible(false);
                teacherDegree.setVisible(false);
                studentGroup.setVisible(true);
                studentDepartment.setVisible(true);
                break;
            case УЧИТЕЛЬ:
                teacherCathedra.setVisible(true);
                teacherDegree.setVisible(true);
                studentGroup.setVisible(false);
                studentDepartment.setVisible(false);
                break;
        }
    }

    @FXML
    public void updateReader() throws UnsupportedEncodingException, SQLException {
        infoArea.clear();
        String moreInfoError = "";
        moreInfoError = checkReader(moreInfoError);
        if (!moreInfoError.equals("")) {
            openErrorWindow(moreInfoError);
            return;
        }
        ReaderType typeGet = readerTypeBox.getValue();
        LocalDate localDate = readerRegistrationDateField.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedString = localDate.format(formatter);
        switch (typeGet) {
            case СТУДЕНТ: {
                moreInfoError = checkStudentInfo(moreInfoError);
                if (!moreInfoError.equals("")) {
                    openErrorWindow(moreInfoError);
                    return;
                }
                StudentEntity student = new StudentEntity();
                student.setReaderId(Integer.valueOf(readerIdField.getText()));
                student.setReaderName(readerNameField.getText());
                student.setReaderLastName(readerLastNameField.getText());
                student.setFatherName(readerFatherNameField.getText());
                student.setReaderType(typeGet);
                student.setRegistrationDate(Date.valueOf(localDate));
                student.setLeavingDate(Date.valueOf(localDate));

                student.setDepartment(studentDepartment.getText());
                student.setGroup(Integer.valueOf(studentGroup.getText()));
                dbRequests.updateReader(student);
            }
            break;
            case УЧИТЕЛЬ: {
                moreInfoError = checkTeacherInfo(moreInfoError);
                if (!moreInfoError.equals("")) {
                    openErrorWindow(moreInfoError);
                    return;
                }
                TeacherEntity teacher = new TeacherEntity();
                teacher.setReaderName(readerNameField.getText());
                teacher.setReaderLastName(readerLastNameField.getText());
                teacher.setFatherName(readerFatherNameField.getText());
                teacher.setReaderType(typeGet);
                teacher.setRegistrationDate(Date.valueOf(localDate));
                teacher.setLeavingDate(Date.valueOf(localDate));
                teacher.setCathedra(teacherCathedra.getText());
                teacher.setDegree(teacherDegree.getText());
                dbRequests.updateReader(teacher);
            }
            break;
            case ЧИТАТЕЛЬ:
        }
        infoArea.setText("OK");
    }

    public String checkReader(String moreInfoError) {
        String moreInfo = "";
        if (readerIdField.getText().isEmpty())
            moreInfo += "пожалуйста, введите идентификатор читателя \n";
        if (readerNameField.getText().isEmpty())
            moreInfo += "пожалуйста, введите имя читателя \n";
        if (readerLastNameField.getText().isEmpty())
            moreInfo += "пожалуйста, введите фамилию читателя \n";
        if (readerRegistrationDateField.getValue() == null)
            moreInfo += "пожалуйста, введите дату регистрации \n";
        try {
            return new String(moreInfo.getBytes("Cp1251"), StandardCharsets.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return moreInfo;
    }

    public String checkStudentInfo(String moreInfo) {
        if (studentDepartment.getText().isEmpty())
            moreInfo += "пожалуйста, введите факультет студента или измените тип читателя \n";
        if (studentGroup.getText().isEmpty())
            moreInfo += "пожалуйста, введите группу студента или измените тип читателя \n";
        if (!studentGroup.getText().matches("\\d{0,7}([\\.]\\d{0,4})?")) {
            moreInfo += "пожалуйста, введите группу в виде числа \n";
        }
        try {
            return new String(moreInfo.getBytes("Cp1251"), StandardCharsets.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return moreInfo;
    }

    public String checkTeacherInfo(String moreInfo) {
        if (teacherCathedra.getText().isEmpty())
            moreInfo += "пожалуйста, введите кафедру преподавателя или смените тип читателя \n";
        if (teacherDegree.getText().isEmpty())
            moreInfo += "пожалуйста, введите ученую степень преподавателя или смените тип читателя \n";
        try {
            return new String(moreInfo.getBytes("Cp1251"), StandardCharsets.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return moreInfo;
    }


    public void openErrorWindow(String error) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/errorWindow.fxml"));

            root = loader.load();
            ErrorController c = loader.getController();
            c.setError(error);
            Stage stage = new Stage();
            stage.setTitle("Error");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNeededFields(ActionEvent actionEvent) {
        ReaderType selectedValue = readerTypeBox.getValue();
        switch (selectedValue) {
            case СТУДЕНТ:
                studentGroup.setVisible(true);
                studentDepartment.setVisible(true);
                teacherCathedra.setVisible(false);
                teacherDegree.setVisible(false);
                break;
            case УЧИТЕЛЬ:
                studentGroup.setVisible(false);
                studentDepartment.setVisible(false);
                teacherCathedra.setVisible(true);
                teacherDegree.setVisible(true);
                break;
            default:
        }
    }
}
