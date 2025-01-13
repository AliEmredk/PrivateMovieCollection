package dk.easv.moviecollection.gui;

import dk.easv.moviecollection.be.Category;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class NodeBuilder {

    private final static String DEFAULT_CATEGORY_PICTURE = "src/main/resources/images/defaultMoviePicture.png";

    public VBox categoryToVBox(Category category) {
        VBox container = new VBox();
        Image defaultImage = new Image(DEFAULT_CATEGORY_PICTURE);
        ImageView imageContainer = new ImageView(defaultImage);
        Label movieTitle = new Label(category.getName());
        container.getChildren().addAll(imageContainer, movieTitle);
        return container;
    }
}
