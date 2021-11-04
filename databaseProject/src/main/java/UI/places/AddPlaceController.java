package UI.places;

import Database.DBRequests;
import Database.places.models.Place;
import Database.places.models.PlaceType;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddPlaceController {
    @FXML
    TextField nameField;
    @FXML
    TextField addressField;
    @FXML
    ComboBox<PlaceType> typeBox;
    @FXML
    TextArea infoArea;
    String idL="Идентификатор: ";
    String infoStart = "Нужно ввести или выбрать:\n ";


    private final DBRequests dbRequests = DBRequests.getInstance();
    public void initController() {
        typeBox.getItems().addAll(
                PlaceType.АБОНЕМЕНТ,
                PlaceType.ЧИТАЛЬНЫЙ_ЗАЛ
        );
    }
    @FXML
    public void addPlace(){
        infoArea.clear();
        String info="";
        if(nameField.getText().equals("")||typeBox.getValue()==null||addressField.getText().equals("")){
            if(nameField.getText().equals(""))
                info+="Название места\n";
            if(typeBox.getValue()==null)
                info+="Тип места\n";
            if(addressField.getText().equals(""))
                info+="Адрес места\n";
            infoArea.setText(infoStart+info);
            return;
        }
        Place place=new Place();
        place.setPlaceName(nameField.getText());
        place.setPlaceType(typeBox.getValue());
        place.setPlaceAddress(addressField.getText());
//        try {
            Integer id = dbRequests.addPlace(place);
            infoArea.setText(idL+id);
//        }
//        catch (Exception ignore){
//
//        }
    }
}
