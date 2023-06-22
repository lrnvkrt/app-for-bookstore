package org.example.control;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.example.FormValidator;
import org.example.SceneSwapper;
import org.example.entity.Employee;
import org.example.entity.EmploymentContract;
import org.example.entity.Store;
import org.example.repository.EmployeeRepository;
import org.example.repository.StoreRepository;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class AddEmployeeSceneController implements Initializable {
    @FXML
    private Button backBtn;

    @FXML
    private Label cardVal;

    @FXML
    private TextField hoursField;

    @FXML
    private Label hoursVal;

    @FXML
    private TextField idCardNumber;

    @FXML
    private TextField medCardNumField;

    @FXML
    private Label medVal;

    @FXML
    private TextField nameField;

    @FXML
    private Label nameVal;

    @FXML
    private Label patroVal;

    @FXML
    private TextField patronymicField;

    @FXML
    private Label posVal;

    @FXML
    private TextField positionField;

    @FXML
    private TextField salaryField;

    @FXML
    private Label salaryVal;

    @FXML
    private VBox selectedStores;

    @FXML
    private VBox stores;

    @FXML
    private TextField surnameField;

    @FXML
    private Label surnameVal;
    private StoreRepository storeRepository;
    private EmployeeRepository employeeRepository;
    private Employee employee;


    @FXML
    void goBack() {
        SceneSwapper.changeScene(backBtn, "/scene/employees_scene.fxml");
    }

    @FXML
    void saveEmployee() {
        boolean validated = true;
        EmploymentContract employmentContract = new EmploymentContract();
        if (FormValidator.validateIfEmpty(nameField) && FormValidator.validateLessLength(nameField, 50)){
            employee.setName(nameField.getText());
            nameVal.setText("");
        } else {
            nameVal.setText("*");
            validated = false;
        }
        if (FormValidator.validateIfEmpty(surnameField) && FormValidator.validateLessLength(surnameField, 50)){
            employee.setSurname(surnameField.getText());
            surnameVal.setText("");
        } else {
            surnameVal.setText("*");
            validated = false;
        }
        if (FormValidator.validateIfEmpty(patronymicField) && FormValidator.validateLessLength(patronymicField, 50)){
            employee.setPatronymic(patronymicField.getText());
            patroVal.setText("");
        } else {
            patroVal.setText("*");
            validated = false;
        }
        if (FormValidator.validateLength(medCardNumField, 7)){
            employee.setMedicalBookNumber(medCardNumField.getText());
            medVal.setText("");
        } else {
            medVal.setText("*");
            validated = false;
        }
        if (FormValidator.validateLength(idCardNumber, 6)){
            employee.setEmployeeCardNumber(idCardNumber.getText());
            cardVal.setText("");
        } else {
            cardVal.setText("*");
            validated = false;
        }
        if (FormValidator.validateIfEmpty(positionField)){
            employmentContract.setPosition(positionField.getText());
            posVal.setText("");
        } else {
            posVal.setText("*");
            validated = false;
        }
        if (FormValidator.validateDouble(salaryField)){
            employmentContract.setSalary(Double.parseDouble(salaryField.getText()));
            salaryVal.setText("");
        } else {
            salaryVal.setText("*");
            validated = false;
        }
        if (FormValidator.validateInteger(hoursField)){
            employmentContract.setHoursOfWork(Integer.parseInt(hoursField.getText()));
            hoursVal.setText("");
        } else {
            hoursVal.setText("*");
            validated = false;
        }

        if (validated){
            employeeRepository.saveEmploymentContract(employmentContract);
            employee.setEmploymentContract(employmentContract);
            employeeRepository.saveEmployee(employee);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("Сотрудник добавлен");
            alert.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.employeeRepository = new EmployeeRepository();
        this.storeRepository = new StoreRepository();
        this.employee = new Employee();
        employee.setStores(new HashSet<>());
        employee.setEmployeeStatus(employeeRepository.getStatusWorking());
        updateStores();
    }

    private void updateStores(){
        stores.getChildren().clear();
        List<Store> storeList = storeRepository.getStores();
        for(Store store : storeList){
            Label label = new Label();
            label.setText(store.getAddress());
            label.setOnMouseClicked(event -> {
                employee.getStores().add(store);
                updateSelectedStores();
            });
            stores.getChildren().add(label);
        }
    }

    private void updateSelectedStores() {
        selectedStores.getChildren().clear();
        Set<Store> storeSet = employee.getStores();
        for (Store store : storeSet){
            Label label = new Label();
            label.setText(store.getAddress());
            label.setOnMouseClicked(event -> {
                employee.getStores().remove(store);
                updateSelectedStores();
            });
            selectedStores.getChildren().add(label);
        }
    }
}
