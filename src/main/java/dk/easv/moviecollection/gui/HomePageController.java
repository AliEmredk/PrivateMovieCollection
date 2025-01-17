package dk.easv.moviecollection.gui;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.be.Movie;
import dk.easv.moviecollection.gui.models.DataModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
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
    private ComboBox comboBoxSorting;

    @FXML
    private Label lblSelectedCategories;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setComboBoxSorting();
        try {
            dataModel.loadMovies();
            setComboBox();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        searchBar.textProperty().addListener((observable,   oldValue, newValue) -> {
            search(newValue).forEach(this::loadMovieNode);
        });
        minRating.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && !newValue.isEmpty()) {
                minRatingValue = newValue;
            }
            else{
                minRatingValue = "0";
            }
            search(searchBar.getText()).forEach(movie -> loadMovieNode(movie));
        });
        maxRating.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && !newValue.isEmpty()) {
                maxRatingValue = newValue;
            }
            else{
                maxRatingValue = "10";
            }
            search(searchBar.getText()).forEach(movie -> loadMovieNode(movie));
        });
        comboBoxCategories.valueProperty().addListener((observable, oldValue, newValue)-> {
            try {
                searchByCategories(searchBar.getText()).forEach(movie -> loadMovieNode(movie));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });
        dataModel.getMovies().forEach(movie -> {
            loadMovieNode(movie);
        });
        comboBoxSorting.valueProperty().addListener((observable, oldValue, newValue) -> {
        });
    }
    private ObservableList<Movie> search(String input){
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
    private void setComboBoxSorting(){
        comboBoxSorting.getItems().addAll(dataModel.getSortingMethods());
        comboBoxSorting.valueProperty().addListener((observable, oldValue, newValue) -> {
            dataModel.setCurrentSortingMethod(newValue.toString());
            search(searchBar.getText()).forEach(movie -> loadMovieNode(movie));

        });
    }

    private void loadMovieNode(Movie movie){
        VBox node = nodeBuilder.movieToVBox(movie);
        flowPaneMovies.getChildren().add(node);

        node.setOnMouseClicked(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(CategoryController.MOVIE_INFO_VIEW_PATH));
            try {
                Parent root = loader.load();
                Scene scene = new Scene(root);
                MovieInfoController movieInfoController = loader.getController();
                movieInfoController.setMovie(movie);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
