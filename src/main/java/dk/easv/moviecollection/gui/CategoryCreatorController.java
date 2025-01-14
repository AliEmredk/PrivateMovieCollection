package dk.easv.moviecollection.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CategoryCreatorController implements Initializable {


    @FXML
    private TextField txtFieldCategoryName;
    @FXML
    private ImageView imgViewEditIcon;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set a default image
        Image defaultImage = new Image(getClass().getResource("/images/defaultCategoryPicture.png").toExternalForm());
        imgViewEditIcon.setImage(defaultImage);

        imgViewEditIcon.setOnMouseClicked(event -> {
            try {
                selectFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    private void selectFile() throws IOException {
        Stage stage = (Stage) txtFieldCategoryName.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a picture");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(imageFilter);
        // set initial directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedFile = fileChooser.showOpenDialog(stage);

        imgViewEditIcon.setImage(new Image(selectedFile.toURI().toString()));
    }


}
