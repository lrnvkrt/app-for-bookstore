package org.example.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import org.example.SceneSwapper;
import org.example.entity.DeliveryItem;
import org.example.entity.Product;
import org.example.repository.ProductRepository;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FindDeliveryController implements Initializable {
    @FXML
    private TableColumn<DeliveryItem, String> addressCol;

    @FXML
    private TableColumn<DeliveryItem, String> dateCol;

    @FXML
    private Button goBackBtn;

    @FXML
    private VBox products;

    @FXML
    private TableColumn<DeliveryItem, String> quantityCol;

    @FXML
    private Label selectedProduct;

    @FXML
    private TableView<DeliveryItem> table;

    private ProductRepository productRepository;


    @FXML
    void goBack() {
        SceneSwapper.changeScene(goBackBtn, "/scene/main_menu_scene.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addressCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData
                .getValue()
                .getDelivery()
                .getStore()
                .getAddress()));
        dateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData
                .getValue()
                .getDelivery()
                .getDate().toString()));
        quantityCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData
                .getValue()
                .getQuantity()
                .toString()));


        productRepository = new ProductRepository();
        List<Product> productList = productRepository.getProducts();
        updateProducts(productList);
    }

    private void updateProducts(List<Product> productList) {
        products.getChildren().clear();
        for (Product product : productList){
            Label label = new Label();
            label.setText(product.getName());
            label.setOnMouseClicked(event -> {
                selectedProduct.setText("Выбранный продукт: \n" + product.getName());
                updateTableContent(product);
            });
            products.getChildren().add(label);
        }
    }

    private void updateTableContent(Product product) {
        List<DeliveryItem> deliveryItems = product.getDeliveryItemList();
        ObservableList<DeliveryItem> deliveryItemObservableList = FXCollections.observableList(deliveryItems);
        table.setItems(deliveryItemObservableList);
    }
}
