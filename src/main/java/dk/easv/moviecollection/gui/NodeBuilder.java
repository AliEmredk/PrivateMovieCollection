package dk.easv.moviecollection.gui;

import dk.easv.moviecollection.be.Category;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class NodeBuilder {

    private final static String DEFAULT_CATEGORY_PICTURE = "/images/defaultMoviePicture.png";

    public VBox categoryToVBox(Category category) {
        VBox container = new VBox();
        Circle circle = new Circle(85);
        Image defaultImage = new Image(DEFAULT_CATEGORY_PICTURE);
        circle.setFill(new ImagePattern(defaultImage));
        Label movieTitle = new Label(category.getName());
        container.getChildren().addAll(circle, movieTitle);

        container.getStyleClass().add("category-container");
        circle.getStyleClass().add("image-container");
        return container;
    }
    
}
