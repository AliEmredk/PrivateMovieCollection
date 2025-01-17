package dk.easv.moviecollection.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Page {

    private static final String CATEGORIES_VIEW_PATH = "/dk/easv/moviecollection/views/categories-view.fxml";
    private static final String HOMEPAGE_VIEW_PATH = "/dk/easv/moviecollection/views/homepage-view.fxml";
    protected String minRatingValue = "0";
    protected String maxRatingValue = "10";

    @FXML
    protected void goToCategoriesView(ActionEvent event) {
        goToView(event, CATEGORIES_VIEW_PATH);
    }

    @FXML
    protected void goToHomePageView(ActionEvent event) {
        goToView(event, HOMEPAGE_VIEW_PATH);
    }

    private void goToView(ActionEvent event, String homepageViewPath) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(homepageViewPath));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML protected void showCategoryCreator() throws IOException {
        // Show popup creator
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/dk/easv/moviecollection/views/categoryCreator.fxml"));

        if (loader.getLocation() == null) {
            throw new IOException("Fxml file not found");
        }

        Parent scene = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(scene));
        stage.setResizable(false);
        stage.setTitle("Category Creator");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    protected void showMovieCreator() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/dk/easv/moviecollection/views/movieCreator.fxml"));

        if (loader.getLocation() == null) {
            throw new IOException("Fxml file not found");
        }

        Parent scene = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(scene));
        stage.setResizable(false);
        stage.setTitle("Movie Creator");
        stage.centerOnScreen();
        stage.show();
    }
}

