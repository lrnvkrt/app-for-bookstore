package org.example.control;

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
import org.example.entity.MethodOfPayment;
import org.example.entity.PostgresSale;
import org.example.repository.MethodOfPaymentRepository;
import org.example.repository.PostgresRepository;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class SaleSumController implements Initializable {
    @FXML
    private TableColumn<PostgresSale, Integer> IdCol;

    @FXML
    private TableColumn<PostgresSale, String> dateCol;

    @FXML
    private Button goBackBtn;

    @FXML
    private Label selectedProduct;

    @FXML
    private VBox types;

    @FXML
    private TableColumn<PostgresSale, Double> sumCol;

    @FXML
    private TableView<PostgresSale> table;

    private MethodOfPaymentRepository methodOfPaymentRepository;
    private PostgresRepository postgresRepository;
    @FXML
    void goBack() {
        SceneSwapper.changeScene(goBackBtn, "/scene/main_menu_scene.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IdCol.setCellValueFactory(new PropertyValueFactory<PostgresSale, Integer>("id"));
        dateCol.setCellValueFactory(new PropertyValueFactory<PostgresSale, String>("date"));
        sumCol.setCellValueFactory(new PropertyValueFactory<PostgresSale, Double>("sum"));
        methodOfPaymentRepository = new MethodOfPaymentRepository();
        postgresRepository = PostgresRepository.getInstance();
        updateTypes();
    }

    private void updateTypes() {
        types.getChildren().clear();
        List<MethodOfPayment> methodOfPayments = methodOfPaymentRepository.getMethods();
        for (MethodOfPayment method : methodOfPayments){
            Label label = new Label();
            label.setText(method.getName());
            label.setOnMouseClicked(event -> {
                selectedProduct.setText("Выбранный метод:\n" + method.getName());
                updateTable(method);
            });
            types.getChildren().add(label);
        }
    }

    private void updateTable(MethodOfPayment method) {
        String methodName = method.getName();
        List<PostgresSale> postgresSales = postgresRepository.findSum(methodName);
        table.setItems(FXCollections.observableList(postgresSales));
    }
}
