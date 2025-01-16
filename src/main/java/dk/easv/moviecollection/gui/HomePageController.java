package dk.easv.moviecollection.gui;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.be.Movie;
import dk.easv.moviecollection.bll.MovieService;
import dk.easv.moviecollection.gui.models.DataModel;
import dk.easv.moviecollection.utils.Debounce;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageController extends Page implements Initializable {

    private final DataModel dataModel = new DataModel();
    private final NodeBuilder nodeBuilder = new NodeBuilder();
    private List<Category> selectedCategories = new ArrayList<>();

    @FXML
    private FlowPane flowPaneMovies;

    @FXML
    private TextField searchBar;

    @FXML
    private TextField minRating;

    @FXML
    private TextField maxRating;

    @FXML
    private ComboBox<Category> comboBoxCategories;

    @FXML
    private Label lblSelectedCategories;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dataModel.loadMovies();
            setComboBox();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Debounce debouncer = new Debounce(200);
        searchBar.textProperty().addListener((observable,   oldValue, newValue) -> {
            try {
                search(newValue).forEach(movie -> flowPaneMovies.getChildren().add(nodeBuilder.movieToVBox(movie)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        minRating.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && !newValue.isEmpty()) {
                minRatingValue = newValue;
            }
            else{
                minRatingValue = "0";
            }
            try {
                search(searchBar.getText()).forEach(movie -> flowPaneMovies.getChildren().add(nodeBuilder.movieToVBox(movie)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        maxRating.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && !newValue.isEmpty()) {
                maxRatingValue = newValue;
            }
            else{
                maxRatingValue = "10";
            }
            try {
                search(searchBar.getText()).forEach(movie -> flowPaneMovies.getChildren().add(nodeBuilder.movieToVBox(movie)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        comboBoxCategories.valueProperty().addListener((observable, oldValue, newValue) -> debouncer.debounce(() -> Platform.runLater(() -> {
            try {
                searchByCategories(searchBar.getText()).forEach(movie -> flowPaneMovies.getChildren().add(nodeBuilder.movieToVBox(movie)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        })));
        dataModel.getMovies().forEach(movie -> {
            flowPaneMovies.getChildren().add(nodeBuilder.movieToVBox(movie));
        });
    }
    private ObservableList<Movie> search(String input) throws SQLException {
        clear();
        return dataModel.getMoviesByInput(input, minRatingValue, maxRatingValue);
    }
    private ObservableList<Movie> searchByCategories(String input) throws SQLException {
        clear();
        return (ObservableList<Movie>) dataModel.getMoviesByInputAndCategories(input, minRatingValue, maxRatingValue, selectedCategories);
    }
    private void clear(){
        flowPaneMovies.getChildren().clear();
    }

    private void setComboBox(){
        dataModel.getCategories().forEach(category -> {
            comboBoxCategories.getItems().add(category);
        });
        comboBoxCategories.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedCategories.contains(newValue)) {
                return;
            }
            selectedCategories.add(newValue);
            lblSelectedCategories.setText(lblSelectedCategories.getText() + newValue + ", ");
        });
    }
}
