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
import org.example.entity.Genre;
import org.example.entity.PostgresBook;
import org.example.repository.PostgresRepository;
import org.example.repository.ProductRepository;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FindBookController implements Initializable {
    @FXML
    private TableColumn<PostgresBook, String> authorCol;

    @FXML
    private VBox genre;

    @FXML
    private Button goBackBtn;

    @FXML
    private TableColumn<PostgresBook, String> nameCol;

    @FXML
    private Label selectedGenre;

    @FXML
    private TableView<PostgresBook> table;
    private ProductRepository productRepository;
    private PostgresRepository postgresRepository;

    @FXML
    void goBack() {
        SceneSwapper.changeScene(goBackBtn, "/scene/main_menu_scene.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        authorCol.setCellValueFactory(new PropertyValueFactory<PostgresBook,String>("author"));
        nameCol.setCellValueFactory(new PropertyValueFactory<PostgresBook, String>("name"));

        productRepository = new ProductRepository();
        postgresRepository = PostgresRepository.getInstance();
        updateGenres();
    }

    private void updateGenres() {
        genre.getChildren().clear();
        List<Genre> genreList = productRepository.getGenres();
        for (Genre cur_genre : genreList){
            Label label = new Label();
            label.setText(cur_genre.getName());
            label.setOnMouseClicked(event -> {
                selectedGenre.setText("Выбраный жанр:\n" + cur_genre.getName());
                updateTable(cur_genre);
            });
            genre.getChildren().add(label);
        }
    }

    private void updateTable(Genre genre) {
        List<PostgresBook> postgresBooks = postgresRepository.finBooks(genre.getName());
        table.setItems(FXCollections.observableList(postgresBooks));
    }
}
