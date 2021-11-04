package UI.punishments;

import Database.DBRequests;
import Database.files.models.Punishment;
import Database.files.models.PunishmentStatus;
import Database.files.models.PunishmentType;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;

public class UpdatePunishmentController {
    private final DBRequests dbRequests = DBRequests.getInstance();
    @FXML
    TextField readerIdField;
    @FXML
    ComboBox<PunishmentType> typeBox;
    @FXML
    ComboBox<PunishmentStatus> statusBox;
    @FXML
    DatePicker startDateField;
    @FXML
    DatePicker endDateField;
    @FXML
    TextField paymentField;
    @FXML
    Button addPunishmentButton;
    @FXML
    TextField punishmentField;
    @FXML
    TextArea infoArea;
    @FXML
    CheckBox getOldInfoCheck;
    String infoStart = "Нужно ввести или выбрать:\n ";

    public void initController() {
        typeBox.getItems().addAll(
                PunishmentType.БЛОКИРОВКА,
                PunishmentType.ШТРАФ
        );
        statusBox.getItems().setAll(PunishmentStatus.ЗАБЛОКИРОВАН,PunishmentStatus.ЗАВЕРШЕНО,
                PunishmentStatus.НУЖНО_ОПЛАТИТЬ,PunishmentStatus.ОПЛАЧЕНО);
    }

    @FXML
    public void getOldInfo() {
        cleanFields();
        if (getOldInfoCheck.isSelected() && punishmentField.getText() != null&&!punishmentField.getText().equals("")) {
            Punishment punishment = dbRequests.getPunishment(Integer.parseInt(punishmentField.getText()));
            if(punishment!=null) {
                typeBox.setValue(punishment.getPunishmentType());
                statusBox.setValue(punishment.getStatus());
                if (punishment.getPayment() != null) paymentField.setText(punishment.getPayment().toString());
                if (punishment.getStartDate() != null) startDateField.setValue(punishment.getStartDate().toLocalDate());
                if (punishment.getEndDate() != null) endDateField.setValue(punishment.getEndDate().toLocalDate());
                punishment.setStatus(statusBox.getValue());
                readerIdField.setText(punishment.getReaderId().toString());
            }
        }
    }

    public void cleanFields() {
        paymentField.clear();
        startDateField.setValue(null);
        endDateField.setValue(null);
        readerIdField.clear();
        typeBox.setValue(null);
        statusBox.setValue(null);
    }

    @FXML
    public void updatePunishment() {
        infoArea.clear();
        String info = "";
        if (readerIdField.getText().equals("") || typeBox.getValue() == null ||punishmentField.getText().equals("")) {
            if (punishmentField.getText().equals(""))
                info += "Идентификатор наказания\n";
            if (readerIdField.getText().equals(""))
                info += "Идентификатор читателя\n";
            if (typeBox.getValue() == null)
                info += "Тип наказания\n";
            infoArea.setText(infoStart + info);
            return;
        }
        Punishment punishment = new Punishment();
        punishment.setPunishmentId(Integer.valueOf(punishmentField.getText()));
        punishment.setPunishmentType(typeBox.getValue());
        if (!paymentField.getText().equals("")) punishment.setPayment(Integer.valueOf(paymentField.getText()));
        if (startDateField.getValue() != null) punishment.setStartDate(Date.valueOf(startDateField.getValue()));
        if (endDateField.getValue() != null) punishment.setEndDate(Date.valueOf(endDateField.getValue()));
        punishment.setReaderId(Integer.valueOf(readerIdField.getText()));
        punishment.setStatus(statusBox.getValue());
        dbRequests.updatePunishment(punishment);
        infoArea.setText("Выполнено");

    }

}
