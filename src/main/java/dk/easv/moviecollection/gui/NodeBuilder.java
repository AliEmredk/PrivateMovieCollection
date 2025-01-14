package dk.easv.moviecollection.gui;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.be.Movie;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class NodeBuilder {

    private final static String DEFAULT_CATEGORY_PICTURE = "/images/defaultCategoryPicture.png";
    private final static String DEFAULT_MOVIE_PICTURE = "/images/defaultMoviePicture.png";

    public VBox categoryToVBox(Category category) {
        VBox container = new VBox();
        Circle circle = new Circle(85);
        Image defaultImage = new Image(DEFAULT_CATEGORY_PICTURE);
        circle.setFill(new ImagePattern(defaultImage));
        Label categoryName = new Label(category.getName());
        container.getChildren().addAll(circle, categoryName);

        container.getStyleClass().add("category-container");
        circle.getStyleClass().add("image-container");
        return container;
    }

    public VBox movieToVBox(Movie movie) {
        VBox container = new VBox();
        Image defaultImage = new Image(DEFAULT_MOVIE_PICTURE);
        ImageView imageContainer = new ImageView(defaultImage);
        imageContainer.setFitWidth(90);
        imageContainer.setFitHeight(55);
        Label movieTitle = new Label(movie.getTitle());
        container.getChildren().addAll(imageContainer, movieTitle);

        container.getStyleClass().add("movie-container");
        return container;
    }
    
}
