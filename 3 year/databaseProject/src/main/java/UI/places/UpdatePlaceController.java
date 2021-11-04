package UI.places;

import Database.DBRequests;
import Database.places.models.Place;
import Database.places.models.PlaceType;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class UpdatePlaceController {
    private final DBRequests dbRequests = DBRequests.getInstance();
    @FXML
    TextField addressField;
    @FXML
    ComboBox<PlaceType> typeBox;
    @FXML
    TextField idField;
    @FXML
    TextArea infoArea;
    @FXML
    CheckBox getOldInfoCheck;
    @FXML
    TextField nameField;
    String infoStart = "Нужно ввести или выбрать:\n ";

    public void initController() {
        typeBox.getItems().addAll(
                PlaceType.АБОНЕМЕНТ,
                PlaceType.ЧИТАЛЬНЫЙ_ЗАЛ
        );
    }

    @FXML
    public void getOldInfo() {
        cleanFields();
        if (getOldInfoCheck.isSelected() && idField.getText() != null&&!idField.getText().equals("")) {
            Place place = dbRequests.getPlace(Integer.parseInt(idField.getText()));
            if(place!=null) {
                typeBox.setValue(place.getPlaceType());
                addressField.setText(place.getPlaceAddress());
                nameField.setText(place.getPlaceName());
            }
        }
    }
    public void cleanFields(){
        nameField.clear();
        typeBox.setValue(null);
        addressField.clear();
    }


    @FXML
    public void updatePlace() {
        infoArea.clear();
        String info="";
        if(nameField.getText().equals("")||typeBox.getValue()==null||idField.getText().equals("")||
                idField.getText() != null){
            if(idField.getText().equals(""))
                info+="Идентификатор места\n";
            if(nameField.getText().equals(""))
                info+="Название места\n";
            if(typeBox.getValue()==null)
                info+="Тип места\n";
            if(addressField.getText().equals(""))
                info+="Адрес места\n";
            if(idField.getText().equals(""))
                info+="Идентификатор места\n";
            infoArea.setText(infoStart+info);
            return;
        }
        Place place = new Place();
        place.setPlaceId(Integer.parseInt(idField.getText()));
        place.setPlaceType(typeBox.getValue());
        place.setPlaceAddress(addressField.getText());
//        try {
            dbRequests.updatePlace(place);
            infoArea.setText("Выполнено");
//        }
//        catch (Exception ignore){
//        }
    }
}
