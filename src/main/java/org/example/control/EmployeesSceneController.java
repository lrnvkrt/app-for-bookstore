package org.example.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.Main;
import org.example.SceneSwapper;
import org.example.entity.Employee;
import org.example.repository.EmployeeRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeesSceneController implements Initializable {
    private EmployeeRepository employeeRepository;

    @FXML
    private TableColumn<Employee, String> cardIdCol;

    @FXML
    private TableColumn<Employee, String> employeeNameCol;

    @FXML
    private TableColumn<Employee, String> employeePatronymicCol;

    @FXML
    private TableColumn<Employee, String> employeeSirNameCol;

    @FXML
    private TableColumn<Employee, String> employeeStatusCol;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> employementNumberCol;

    @FXML
    private TableColumn<Employee, String> medCardIdCol;

    @FXML
    private Button goBackBtn;

    private Employee selectedEmployee;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        employeeRepository = new EmployeeRepository();

        employeeTable.setRowFactory(tv ->
                {
                    TableRow<Employee> row = new TableRow<>();
                    row.setOnMouseClicked(event ->{
                        this.selectedEmployee = row.getItem();
                        if (event.getClickCount() == 2 && ! row.isEmpty()) {
                            Employee selectedEmployee = row.getItem();
                            System.out.println(selectedEmployee.getMedicalBookNumber());
                            openChangeEmployeeScene(selectedEmployee);
                        }
                    });
                    return row;
                }
        );

        cardIdCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeCardNumber"));
        employeeNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        employeePatronymicCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("patronymic"));
        employeeSirNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("surname"));
        employeeStatusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeStatus().getName()));
        employementNumberCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmploymentContract().getIdEmploymentContract().toString()));
        medCardIdCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("medicalBookNumber"));

        ObservableList<Employee> employeeObservableList = FXCollections.observableList(employeeRepository.getEmployees());
        employeeTable.setItems(employeeObservableList);
    }

    @FXML
    private void goBack(){
        SceneSwapper.changeScene(goBackBtn, "/scene/main_menu_scene.fxml");
    }

    @FXML
    private void addEmployee(){
        SceneSwapper.changeScene(goBackBtn, "/scene/adding_employee_scene.fxml");
    }

    @FXML
    private void deleteEmployee(){
        if (this.selectedEmployee == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
            alert.setHeaderText("Выберите сотрудников!");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.YES, ButtonType.NO);
            alert.setHeaderText("Удаление необратимо!");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES){
                employeeRepository.deleteEmployee(selectedEmployee);
                this.selectedEmployee = null;
                employeeTable.setItems(FXCollections.observableList(employeeRepository.getEmployees()));
            }
        }
    }

    private void openChangeEmployeeScene(Employee employee){
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/scene/update_employee_scene.fxml"));
        try {
            Parent root = fxmlLoader.load();

            UpdateEmployeeController employeeController = fxmlLoader.getController();
            System.out.println(employee.getEmployeeCardNumber());
            employeeController.setEmployee(employee);
            employeeController.loadFields();

            Stage stage = (Stage) goBackBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
