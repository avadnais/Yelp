import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    final static String business_path = "/Users/andyvadnais/Desktop/sp18/csc365/dataset/business.json";
    BusinessHashtable bt;
    private YelpProfile[] profiles;
    final int MAX_SIZE = 10000;
    @FXML
    TableView<YelpProfile> enteredTable;
    @FXML
    TableColumn<YelpProfile, String> enteredName;
    @FXML
    TableColumn<YelpProfile, String> enteredCity;
    @FXML
    TableColumn<YelpProfile, String> enteredStars;
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchField;
    @FXML
    TableView<YelpProfile> allBusinessTable;
    @FXML
    TableColumn<YelpProfile, String> businessName;
    @FXML
    TableView<YelpProfile> similarTable;
    @FXML
    TableColumn<YelpProfile, String> similarName;
    @FXML
    TableColumn<YelpProfile, String> similarCity;
    @FXML
    TableColumn<YelpProfile, String> similarStars;

    public Controller() throws IOException, JSONException {
        makeBusinessTable();
    }

    public void searchButton(ActionEvent e) {
        for (int i = 0; i < similarTable.getItems().size(); i++) {
            similarTable.getItems().clear();
        }

        enteredTable.getItems().clear();
        YelpProfile prof = bt.get(searchField.getCharacters().toString());
        YelpProfile[] similarBusinesses;

        System.out.println((bt.get(searchField.getCharacters().toString())));
        if (prof != null) {
            ObservableList<YelpProfile> enteredData = FXCollections.observableArrayList(prof);
            enteredName.setCellValueFactory(new PropertyValueFactory<>("name"));
            enteredCity.setCellValueFactory(new PropertyValueFactory<>("city"));
            enteredStars.setCellValueFactory(new PropertyValueFactory<>("stars"));
            enteredTable.getItems().addAll(enteredData);
            similarBusinesses = prof.getSimilarBusinesses(profiles);
            for (YelpProfile y : similarBusinesses) {
                System.out.println(y.toString());
            }

            if (similarBusinesses == null) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("No Matches Found");
                errorAlert.setContentText("There are no similar Businesses in the area");
                errorAlert.showAndWait();
            } else {
                ObservableList<YelpProfile> data = FXCollections.observableArrayList(similarBusinesses);
                similarName.setCellValueFactory(new PropertyValueFactory<>("name"));
                similarCity.setCellValueFactory(new PropertyValueFactory<>("city"));
                similarStars.setCellValueFactory(new PropertyValueFactory<>("stars"));
                similarTable.getItems().addAll(data);
            }

        } else {
            System.out.println("Business does not exist");

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Business does not exist");
            errorAlert.showAndWait();
        }
    }

    public void makeBusinessTable() throws IOException, JSONException {
        JSON2java j = new JSON2java();
        profiles = j.businessConvert(business_path);
        bt = new BusinessHashtable();
        for (int i = 0; i < MAX_SIZE; i++)
            bt.put(profiles[i].name, profiles[i]);
    }

    public void initialize() throws IOException, JSONException {

        profiles = JSON2java.businessConvert(business_path);

        ArrayList<YelpProfile> profilesList = new ArrayList<YelpProfile>();

        ObservableList<YelpProfile> data =
                FXCollections.observableArrayList(profiles);
        businessName.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        allBusinessTable.getItems().addAll(data);
    }
}

