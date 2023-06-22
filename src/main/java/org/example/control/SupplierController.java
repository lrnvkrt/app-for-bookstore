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
import org.example.entity.Delivery;
import org.example.entity.DeliveryItem;
import org.example.entity.Product;
import org.example.entity.Supplier;
import org.example.repository.ProductRepository;

import java.net.URL;
import java.util.*;

public class SupplierController implements Initializable {
    @FXML
    private Button goBackBtn;

    @FXML
    private TableColumn<Product, String> nameCol;

    @FXML
    private Label selectedSuplier;

    @FXML
    private VBox suppliers;

    @FXML
    private TableView<Product> table;

    @FXML
    private TableColumn<Product, String> typeCol;

    private ProductRepository productRepository;

    @FXML
    void goBack() {
        SceneSwapper.changeScene(goBackBtn, "/scene/main_menu_scene.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productRepository = new ProductRepository();

        nameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Product,String>("typeOfProduct"));

        updateSuppliers();
    }

    private void updateSuppliers() {
        suppliers.getChildren().clear();
        List<Supplier> supplierList = productRepository.getSuppliers();
        for (Supplier supplier : supplierList){
            Label label = new Label();
            label.setText(supplier.getName());
            label.setOnMouseClicked(event -> {
                selectedSuplier.setText("Выбранный поставщик:\n" + supplier.getName());
                updateTable(supplier);
            });
            suppliers.getChildren().add(label);
        }
    }

    private void updateTable(Supplier supplier) {
        Set<Product> products = new HashSet<>();
        for (Delivery delivery : supplier.getDeliveries()){
            List<DeliveryItem> deliveryItems = delivery.getDeliveryItems();
            for (DeliveryItem deliveryItem : deliveryItems){
                products.add(deliveryItem.getProduct());
            }
        }
        table.setItems(FXCollections.observableList(new ArrayList<>(products)));
    }
}
