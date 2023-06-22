package org.example.control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.SceneSwapper;

public class MainSceneController {
    @FXML
    private Button employeeButton;

    @FXML
    private void openEmployeeTable(){
        System.out.println("Open employee table!");
        SceneSwapper.changeScene(employeeButton, "/scene/employees_scene.fxml");
    }

    @FXML
    void openProductQuery() {
        System.out.println("Open product scene!");
        SceneSwapper.changeScene(employeeButton, "/scene/find_book_scene.fxml");
    }

    @FXML
    void openSaleQuery() {
        System.out.println("Open sale table!");
        SceneSwapper.changeScene(employeeButton, "/scene/find_sales_scene.fxml");
    }

    @FXML
    void openDeliveryQuery() {
        System.out.println("Open product scene");
        SceneSwapper.changeScene(employeeButton ,"/scene/find_delivery_scene.fxml");
    }

    @FXML
    void openSupplierQuery(){
        System.out.println("Open supplier query!");
        SceneSwapper.changeScene(employeeButton, "/scene/supplier_scene.fxml");
    }

    @FXML
    void openShopEmployeeQuery(){
        System.out.println("Open shop employee query!");
        SceneSwapper.changeScene(employeeButton, "/scene/store_employee_scene.fxml");
    }
}
