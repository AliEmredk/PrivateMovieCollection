package dk.easv.moviecollection.gui;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.be.Movie;
import dk.easv.moviecollection.gui.models.DataModel;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CategoryController {

    private Category category;
    private final DataModel dataModel = new DataModel();
    private final NodeBuilder nodeBuilder = new NodeBuilder();
    private static final String CATEGORIES_VIEW_PATH = "/dk/easv/moviecollection/categories-view.fxml";
    @FXML
    private Label lblCategoryName;

    @FXML
    private FlowPane flowPaneMovies;

    @FXML
    private void showCategoriesWindow(){

        FXMLLoader loader = new FXMLLoader(getClass().getResource(CATEGORIES_VIEW_PATH));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) flowPaneMovies.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCategory(Category category) throws SQLException {
        this.category = category;
        setData();
    }

    private void setData() throws SQLException {
        lblCategoryName.setText(category.getName());
        dataModel.loadMoviesByCategory(category);
        dataModel.getMovies().forEach(movie -> flowPaneMovies.getChildren().add(nodeBuilder.movieToVBox(movie)));
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


}
