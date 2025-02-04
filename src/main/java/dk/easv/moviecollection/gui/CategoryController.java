package dk.easv.moviecollection.gui;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.be.Movie;
import dk.easv.moviecollection.gui.models.DataModel;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CategoryController extends Page implements Initializable{

    private Category category;
    private final DataModel dataModel = new DataModel();
    private final NodeBuilder nodeBuilder = new NodeBuilder();
    private static final String CATEGORIES_VIEW_PATH = "/dk/easv/moviecollection/views/categories-view.fxml";
    public static final String MOVIE_INFO_VIEW_PATH = "/dk/easv/moviecollection/views/movie-info.fxml";
    @FXML
    private Label lblCategoryName;

    @FXML
    private FlowPane flowPaneMovies;

    @FXML
    private TextField minRating;

    @FXML
    private TextField maxRating;

    @FXML
    private TextField searchBar;

    @FXML
    private ImageView categoryImage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            search(newValue, minRatingValue, maxRatingValue).forEach(movie -> flowPaneMovies.getChildren().add(nodeBuilder.movieToVBox(movie)));
        });
        minRating.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && !newValue.isEmpty()) {
                minRatingValue = newValue;
            }
            else{
                minRatingValue = "0";
            }
            search(searchBar.getText(), minRatingValue, maxRatingValue).forEach(movie -> flowPaneMovies.getChildren().add(nodeBuilder.movieToVBox(movie)));
        });
        maxRating.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && !newValue.isEmpty()) {
                maxRatingValue = newValue;
            }
            else{
                maxRatingValue = "10";
            }
            search(searchBar.getText(), minRatingValue, maxRatingValue).forEach(movie -> flowPaneMovies.getChildren().add(nodeBuilder.movieToVBox(movie)));
        });

    }
    
    public void setCategory(Category category) throws SQLException {
        this.category = category;
        setData();
    }

    private void setData() throws SQLException {
        lblCategoryName.setText(category.getName());
        if(category.getPath() != null && !category.getPath().isEmpty()) {
            File file = new File(category.getPath());
            Image image = new Image(file.toURI().toString());
            categoryImage.setImage(image);
        }
        dataModel.loadMoviesByCategory(category);
        dataModel.getMovies().forEach(movie -> {
            VBox node = nodeBuilder.movieToVBox(movie);
            flowPaneMovies.getChildren().add(node);

            node.setOnMouseClicked(event -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(MOVIE_INFO_VIEW_PATH));
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
        });
        dataModel.getMovies().addListener((ListChangeListener<Movie>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Movie movie : change.getAddedSubList()) {
                        VBox node = nodeBuilder.movieToVBox(movie);
                        flowPaneMovies.getChildren().add(node);
                    }
                }
            }
        });
    }
    private ObservableList<Movie> search(String input, String minRating, String maxRating) {
        clear();
        return dataModel.getMoviesByInput(input, minRating, maxRating);
    }
    private void clear(){
        flowPaneMovies.getChildren().clear();
    }

    @FXML
    private void deleteCategory(ActionEvent event) {
    dataModel.deleteCategory(category);
    goToCategoriesView(event);
    }
}
