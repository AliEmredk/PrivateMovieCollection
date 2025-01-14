package dk.easv.moviecollection.gui;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.be.Movie;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class NodeBuilder {

    private final static String DEFAULT_CATEGORY_PICTURE = "/images/defaultCategoryPicture.png";
    private final static String DEFAULT_MOVIE_PICTURE = "/images/defaultMoviePicture.png";
    private static final String CATEGORY_VIEW_PATH = "/dk/easv/moviecollection/category-view.fxml";

    public VBox categoryToVBox(Category category) {
        VBox container = new VBox();
        Circle circle = new Circle(85);
        Image defaultImage = new Image(DEFAULT_CATEGORY_PICTURE);
        circle.setFill(new ImagePattern(defaultImage));
        Label categoryName = new Label(category.getName());
        container.getChildren().addAll(circle, categoryName);

        container.getStyleClass().add("category-container");
        circle.getStyleClass().add("image-container");

        container.setOnMouseClicked(event ->{
        if (event.getButton() == MouseButton.PRIMARY) {
            showCategoryView(category, container);
        }});

        return container;
    }

    public VBox movieToVBox(Movie movie) {
        VBox container = new VBox();
        Image defaultImage = new Image(DEFAULT_MOVIE_PICTURE);
        ImageView imageContainer = new ImageView(defaultImage);
        imageContainer.setFitWidth(200);
        imageContainer.setFitHeight(140);
        Label movieTitle = new Label(movie.getTitle());
        container.getChildren().addAll(imageContainer, movieTitle);

        container.getStyleClass().add("movie-container");
        return container;
    }

    private void showCategoryView(Category category, Node node) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(CATEGORY_VIEW_PATH));
        try {
            Parent root = loader.load();
            CategoryController controller = loader.getController();
            controller.setCategory(category);
            Scene scene = new Scene(root);
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
