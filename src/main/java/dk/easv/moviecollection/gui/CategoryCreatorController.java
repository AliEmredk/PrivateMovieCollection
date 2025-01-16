package dk.easv.moviecollection.gui;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.bll.CategoryService;
import dk.easv.moviecollection.gui.models.DataModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CategoryCreatorController implements Initializable {

    private final static String IMAGES_DIRECTORY_PATH = "src/main/resources/dk/easv/moviecollection/images";
    private final static String DEFAULT_CATEGORY_PICTURE = "src/main/resources/dk/easv/moviecollection/images/defaultCategoryPicture.png";
    private final CategoryService categoryService = new CategoryService();
    private final DataModel dataModel = new DataModel();

    @FXML
    private TextField txtFieldCategoryName;
    @FXML
    private ImageView imgViewEditIcon;

    private String path = "src/main/resources/dk/easv/moviecollection/images";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set a default image
        Image defaultImage = new Image(String.valueOf(getClass().getResource(DEFAULT_CATEGORY_PICTURE)));
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
            setFilePath(selectedFile.getAbsolutePath());
            setImage(path);
        }

    }
    @FXML
    private void addNewCategory() throws SQLException {
        Category category = new Category();
        category.setName(txtFieldCategoryName.getText());
        if (category.getName().isEmpty()) {
            showError("You should enter a valid name");
        }
        category.setPath(path);
        categoryService.createNew(category);
        Window window = (Stage) txtFieldCategoryName.getScene().getWindow();
        Stage stage = (Stage) window;
        dataModel.loadCategories();
        stage.close();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setFilePath(String filePath) throws IOException {
        Path sourcePath = Paths.get(filePath);

        Path destinationDir = Paths.get(IMAGES_DIRECTORY_PATH);

        Path destinationPath = destinationDir.resolve(sourcePath.getFileName());

        Files.move(sourcePath, destinationPath);

        this.path = destinationPath.toString();
    }

    private void setImage(String path){
        File file = new File(path);
        imgViewEditIcon.setImage(new Image(file.toURI().toString()));
    }
}
