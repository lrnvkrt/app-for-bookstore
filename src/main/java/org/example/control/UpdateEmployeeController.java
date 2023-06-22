package org.example.control;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.example.FormValidator;
import org.example.SceneSwapper;
import org.example.entity.Employee;
import org.example.entity.EmployeeStatus;
import org.example.entity.Store;
import org.example.repository.EmployeeRepository;
import org.example.repository.StoreRepository;

import java.util.List;
import java.util.Set;

public class UpdateEmployeeController {
    @FXML
    private Button backBtn;

    @FXML
    private Label cardVal;

    @FXML
    private Label curStatusField;

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
    private VBox selectedShops;

    @FXML
    private VBox shops;

    @FXML
    private VBox statuses;

    @FXML
    private TextField surnameField;

    @FXML
    private Label surnameVal;
    private Employee employee;
    private EmployeeRepository employeeRepository;
    private StoreRepository storeRepository;
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void loadFields(){
        employeeRepository = new EmployeeRepository();
        storeRepository = new StoreRepository();
        nameField.setText(employee.getName());
        surnameField.setText(employee.getSurname());
        patronymicField.setText(employee.getPatronymic());
        medCardNumField.setText(employee.getMedicalBookNumber());
        idCardNumber.setText(employee.getEmployeeCardNumber());
        positionField.setText(employee.getEmploymentContract().getPosition());
        salaryField.setText(employee.getEmploymentContract().getSalary().toString());
        hoursField.setText(employee.getEmploymentContract().getHoursOfWork().toString());
        curStatusField.setText(employee.getEmployeeStatus().getName());
        updateStatuses();
        updateShops();
        updateSelectedShops();
    }

    private void updateStatuses(){
        statuses.getChildren().clear();
        List<EmployeeStatus> statusList = employeeRepository.getStatuses();
        for(EmployeeStatus status : statusList){
            Button item = new Button();
            item.setPrefWidth(500);
            item.setText(status.getName());
            item.setOnMouseClicked(event -> {
                employee.setEmployeeStatus(status);
                curStatusField.setText(status.getName());
            });
            statuses.getChildren().add(item);
        }
    }

    private void updateShops(){
        shops.getChildren().clear();
        List<Store> stores = storeRepository.getStores();
        for (Store store : stores){
            Label label = new Label();
            label.setText(store.getAddress());
            label.setOnMouseClicked(event -> {
                employee.getStores().add(store);
                System.out.println(store.getAddress());
                updateSelectedShops();
            });
            shops.getChildren().add(label);
        }
    }

    private void updateSelectedShops() {
        selectedShops.getChildren().clear();
        Set<Store> stores = employee.getStores();
        for (Store store : stores){
            Label label = new Label();
            label.setText(store.getAddress());
            label.setOnMouseClicked(event -> {
                employee.getStores().remove(store);
                updateSelectedShops();
            });
            selectedShops.getChildren().add(label);
        }
    }

    @FXML
    private void goBack(){
        SceneSwapper.changeScene(backBtn, "/scene/employees_scene.fxml");
    }

    @FXML
    private void saveEmployee(){
        boolean validated = true;

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
            employee.getEmploymentContract().setPosition(positionField.getText());
            posVal.setText("");
        } else {
            posVal.setText("*");
            validated = false;
        }
        if (FormValidator.validateDouble(salaryField)){
            employee.getEmploymentContract().setSalary(Double.parseDouble(salaryField.getText()));
            salaryVal.setText("");
        } else {
            salaryVal.setText("*");
            validated = false;
        }
        if (FormValidator.validateInteger(hoursField)){
            employee.getEmploymentContract().setHoursOfWork(Integer.parseInt(hoursField.getText()));
            hoursVal.setText("");
        } else {
            hoursVal.setText("*");
            validated = false;
        }

        if (validated){
            employeeRepository.saveEmployee(employee);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("Изменения сохранены");
            alert.show();
        }
    }
}
