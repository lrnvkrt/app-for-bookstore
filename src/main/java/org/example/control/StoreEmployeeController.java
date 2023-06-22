package org.example.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.example.SceneSwapper;
import org.example.entity.Employee;
import org.example.entity.Store;
import org.example.repository.StoreRepository;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class StoreEmployeeController implements Initializable {
    @FXML
    private Button goBackBtn;

    @FXML
    private TableColumn<Employee, String> positionCol;

    @FXML
    private Label selectedGenre;

    @FXML
    private VBox shops;

    @FXML
    private TableColumn<Employee, String> statusCol;

    @FXML
    private TableColumn<Employee, String> surnameCol;

    @FXML
    private TableView<Employee> table;

    private StoreRepository storeRepository;

    @FXML
    void goBack() {
        SceneSwapper.changeScene(goBackBtn, "/scene/main_menu_scene.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.storeRepository = new StoreRepository();

        surnameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("surname"));
        positionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmploymentContract().getPosition()));
        statusCol.setCellValueFactory(celData -> new SimpleStringProperty(celData.getValue().getEmployeeStatus().getName()));

        updateStores();
    }

    private void updateStores() {
        shops.getChildren().clear();
        List<Store> storeList = storeRepository.getStores();
        for (Store store : storeList){
            Label label = new Label();
            label.setText(store.getAddress());
            label.setOnMouseClicked(event -> {
                updateTable(store);
                selectedGenre.setText("Выбранный магазин:\n" + store.getAddress());
            });
            shops.getChildren().add(label);
        }
    }

    private void updateTable(Store store) {
        Set<Employee> employees = store.getEmployees();
        table.setItems(FXCollections.observableList(new ArrayList<>(employees)));
    }
}
