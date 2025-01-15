package dk.easv.moviecollection.gui;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.bll.CategoryService;
import dk.easv.moviecollection.gui.models.DataModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CategoryCreatorController implements Initializable {


    private final CategoryService categoryService = new CategoryService();
    private final DataModel dataModel = new DataModel();

    @FXML
    private TextField txtFieldCategoryName;
    @FXML
    private ImageView imgViewEditIcon;

    private String path = "";

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
        if (selectedFile != null) {
            path = selectedFile.toURI().toString();
        }
        imgViewEditIcon.setImage(new Image(selectedFile.toURI().toString()));
    }
    @FXML
    private void addNewCategory() throws SQLException {
        Category category = new Category();
        category.setName(txtFieldCategoryName.getText());
        category.setPath(path);
        categoryService.createNew(category);
        Window window = (Stage) txtFieldCategoryName.getScene().getWindow();
        Stage stage = (Stage) window;
        dataModel.loadCategories();
        stage.close();
    }
}

