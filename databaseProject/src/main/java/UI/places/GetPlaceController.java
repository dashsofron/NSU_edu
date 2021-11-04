package UI.places;

import Database.DBRequests;
import Database.places.models.Place;
import Database.places.models.PlaceType;
import Database.readers.models.ReaderEntity;
import Database.readers.models.ReaderType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class GetPlaceController {
    private final DBRequests dbRequests = DBRequests.getInstance();
    @FXML
    TextField idField;
    @FXML
    TableView<Place> placesTable;
    @FXML
    TableColumn<Place,Integer> idColumn;
    @FXML
    TableColumn<Place,String> nameColumn;
    @FXML
    TableColumn<Place,String> addressColumn;
    @FXML
    TableColumn<Place, PlaceType> typeColumn;
    @FXML
    CheckBox idCheck;
    @FXML
    TextField nameField;
    @FXML
    TextField addressField;
    @FXML
    ComboBox<PlaceType> typeBox;

    public void initController(){
        typeBox.getItems().setAll(PlaceType.АБОНЕМЕНТ,PlaceType.ЧИТАЛЬНЫЙ_ЗАЛ);
        getPlaces();
    }
    @FXML
    public void getPlaces(){
        List<Place> places;
        if(!idField.getText().equals("")){
            places=new LinkedList<>();
            Place place=dbRequests.getPlace(Integer.parseInt(idField.getText()));
            places.add(place);
        }
        else {
            Place filter=new Place(null,nameField.getText(),addressField.getText(),typeBox.getValue());
            places=dbRequests.getPlacesWithFilter(filter);

        }
        idColumn.setCellValueFactory(new PropertyValueFactory<Place, Integer>("placeId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Place, String>("placeName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Place, String>("placeAddress"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Place, PlaceType>("placeType"));

        ObservableList<Place> placesO = FXCollections.observableArrayList(places);

        placesTable.setItems(placesO);


    }
    public void showIdField(){
        if(idCheck.isSelected())
            idField.setDisable(false);
        else
            idField.setDisable(true);
    }


    @FXML
    public void dropFilter(){
        typeBox.setValue(null);
        addressField.clear();
        nameField.clear();
        idField.clear();
    }
}
