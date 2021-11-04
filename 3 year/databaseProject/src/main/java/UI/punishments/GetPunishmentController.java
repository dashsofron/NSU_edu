package UI.punishments;

import Database.DBRequests;
import Database.files.models.Punishment;
import Database.files.models.PunishmentStatus;
import Database.files.models.PunishmentType;
import Database.search.DateSearch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class GetPunishmentController {
    private final DBRequests dbRequests = DBRequests.getInstance();
    @FXML
    Button getPunishmentButton;
    @FXML
    TableView<Punishment> punishmentsTable;
    @FXML
    TableColumn<Punishment, Integer> punishmentIdColumn;
    @FXML
    TableColumn<Punishment, Integer> readerIdColumn;
    @FXML
    TableColumn<Punishment, PunishmentType> punishTypeColumn;
    @FXML
    TableColumn<Punishment, LocalDate> startBlockingColumn;
    @FXML
    TableColumn<Punishment, LocalDate> endBlockingColumn;
    @FXML
    TableColumn<Punishment, Integer> paymentColumn;
    @FXML
    CheckBox idCheck;
    @FXML
    TextField idField;
    @FXML
    TableColumn<Punishment, PunishmentStatus> statusColumn;
    @FXML
    TextField readerIdField;
    @FXML
    TextField paymentField;
    @FXML
    ComboBox<PunishmentType> typeBox;
    @FXML
    ComboBox<PunishmentStatus> statusField;
    @FXML
    DatePicker startField;
    @FXML
    DatePicker endField;
    @FXML
    CheckBox oneDayCheck;
    @FXML
    ComboBox<DateSearch> optionTake;
    @FXML
    ComboBox<DateSearch> optionReturn;

    @FXML
    public void dropFilter(){
        typeBox.setValue(null);
        optionReturn.setValue(DateSearch.РАВНО);
        optionTake.setValue(DateSearch.РАВНО);
        endField.setValue(null);
        statusField.setValue(null);
        statusField.setValue(null);
        typeBox.setValue(null);
        paymentField.clear();
        readerIdField.clear();
        idField.clear();
    }

    public void initController() {
        optionTake.getItems().setAll(DateSearch.БОЛЬШЕ,DateSearch.МЕНЬШЕ,DateSearch.РАВНО);
        optionReturn.getItems().setAll(DateSearch.БОЛЬШЕ,DateSearch.МЕНЬШЕ,DateSearch.РАВНО);
        typeBox.getItems().setAll(PunishmentType.ШТРАФ,PunishmentType.БЛОКИРОВКА);
        statusField.getItems().setAll(PunishmentStatus.ОПЛАЧЕНО,PunishmentStatus.ЗАВЕРШЕНО,PunishmentStatus.ЗАБЛОКИРОВАН,PunishmentStatus.НУЖНО_ОПЛАТИТЬ);
        optionReturn.setValue(DateSearch.РАВНО);
        optionTake.setValue(DateSearch.РАВНО);
        getPunishments();
    }

    @FXML
    public void getPunishments() {
        List<Punishment> punishments;
        if (!idField.getText().equals("")) {
            punishments = new LinkedList<>();
            Punishment punishment = dbRequests.getPunishment(Integer.parseInt(idField.getText()));
            punishments.add(punishment);
        } else {
            Punishment filter = new Punishment(null, readerIdField.getText(), typeBox.getValue(), statusField.getValue(),
                    startField.getValue(), endField.getValue(),paymentField.getText(), null);
            punishments = dbRequests.getPunishmentsWithFilter(filter, optionTake.getValue(),optionReturn.getValue());

        }

        punishmentIdColumn.setCellValueFactory(new PropertyValueFactory<Punishment, Integer>("punishmentId"));
        readerIdColumn.setCellValueFactory(new PropertyValueFactory<Punishment, Integer>("readerId"));
        punishTypeColumn.setCellValueFactory(new PropertyValueFactory<Punishment, PunishmentType>("punishmentType"));
        startBlockingColumn.setCellValueFactory(new PropertyValueFactory<Punishment, LocalDate>("startDate"));
        endBlockingColumn.setCellValueFactory(new PropertyValueFactory<Punishment, LocalDate>("endDate"));
        paymentColumn.setCellValueFactory(new PropertyValueFactory<Punishment, Integer>("payment"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Punishment, PunishmentStatus>("status"));
        ObservableList<Punishment> punishmentsO = FXCollections.observableArrayList(punishments);

        punishmentsTable.setItems(punishmentsO);

    }

    public void enableIdField() {
        if (idCheck.isSelected())
            idField.setDisable(false);
        else
            idField.setDisable(true);

    }
}
