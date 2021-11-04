package UI.punishments;

import Database.DBRequests;
import Database.files.models.Punishment;
import Database.files.models.PunishmentStatus;
import Database.files.models.PunishmentType;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;

public class AddPunishmentController {
    private final DBRequests dbRequests = DBRequests.getInstance();
    @FXML
    TextField readerIdField;
    @FXML
    ComboBox<PunishmentType> typeBox;
    @FXML
    DatePicker startDateField;
    @FXML
    DatePicker endDateField;
    @FXML
    TextField paymentField;

    @FXML
    TextArea infoArea;
    String idL = "Идентификатор: ";
    String infoStart = "Нужно ввести или выбрать:\n ";

    public void initController() {
        typeBox.getItems().addAll(
                PunishmentType.БЛОКИРОВКА,
                PunishmentType.ШТРАФ
        );

    }

    @FXML
    public void addPunishment() {
        infoArea.clear();
        String info = "";
        if (readerIdField.getText().equals("") || typeBox.getValue() == null ) {
            if (readerIdField.getText().equals(""))
                info += "Идентификатор читателя\n";

            if (typeBox.getValue() == null)
                info += "Тип наказания\n";
            infoArea.setText(infoStart + info);
            return;
        }
        Punishment punishment = new Punishment();
        punishment.setPunishmentType(typeBox.getValue());
        if (!paymentField.getText().equals("")) punishment.setPayment(Integer.valueOf(paymentField.getText()));
        if (startDateField.getValue() != null) punishment.setStartDate(Date.valueOf(startDateField.getValue()));
        if (endDateField.getValue() != null) punishment.setEndDate(Date.valueOf(endDateField.getValue()));
        punishment.setReaderId(Integer.valueOf(readerIdField.getText()));
        switch (punishment.getPunishmentType()) {
            case ШТРАФ:
                punishment.setStatus(PunishmentStatus.НУЖНО_ОПЛАТИТЬ);
                break;
            case БЛОКИРОВКА:
                punishment.setStatus(PunishmentStatus.ЗАБЛОКИРОВАН);
                break;
        }
//        try {
            Integer id = dbRequests.addPunishment(punishment);
            infoArea.setText(idL + id);
//        } catch (Exception ignore) {
//
//        }
    }
}
