package dk.easv.moviecollection.gui;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.be.Movie;
import dk.easv.moviecollection.bll.CategoryService;
import dk.easv.moviecollection.bll.MovieService;
import dk.easv.moviecollection.dal.DAOentities.CategoryDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MovieCreatorController implements Initializable {

    private final MovieService movieService = new MovieService();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @FXML
    private ListView<Category> categoryListView;
    @FXML
    private ComboBox<Category> categoryComboBox;


    @FXML
    private TextField nameTxt;
    @FXML
    private TextField releaseDateTxt;
    @FXML
    private TextField directorTxt;
    @FXML
    private Slider ratingSlider;
    @FXML
    private TextArea descriptionTxt;

    @FXML
    private String moviePath;


    public void addMovie(ActionEvent actionEvent) throws SQLException {
        Stage stage = (Stage) nameTxt.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a picture");
        FileChooser.ExtensionFilter movieFilter = new FileChooser.ExtensionFilter("MP4 Files", "*.mp4");
        fileChooser.getExtensionFilters().add(movieFilter);
        // set initial directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedFile = fileChooser.showOpenDialog(stage);
        moviePath = selectedFile.toURI().toString();

        if (nameTxt.getText().isEmpty() || releaseDateTxt.getText().isEmpty() ||
                directorTxt.getText().isEmpty() || descriptionTxt.getText().isEmpty() ||
                ratingSlider.getValue() < 0 || ratingSlider.getValue() > 10) {
            showError("All fields must be filled, and a file must be selected.");
        }

        Movie movie = new Movie();
        movie.setTitle(nameTxt.getText());
        movie.setDirector(directorTxt.getText());
        movie.setReleaseDate(releaseDateTxt.getText());
        movie.setDescription(descriptionTxt.getText());

        int rating = (int) ratingSlider.getValue();
        movie.setRating(rating);

        movieService.createNew(movie);

        //movie.setRating(ratingSlider)
        movieService.createNew(movie);

        if (categoryComboBox.getSelectionModel().isEmpty()) {
            showError("Please select a category.");
            return;
        }


    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void categoryComboBoxAct(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configure the slider
        ratingSlider.setMin(1);
        ratingSlider.setMax(10);
        ratingSlider.setValue(5); // Default value
        ratingSlider.setMajorTickUnit(1); // Distance between major ticks
        ratingSlider.setMinorTickCount(0); // No minor ticks
        ratingSlider.setSnapToTicks(true); // Snap to the nearest tick
        ratingSlider.setShowTickMarks(true); // Show tick marks
        ratingSlider.setShowTickLabels(true); // Show labels for tick marks

        // Add a listener to snap to integer values
        ratingSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            ratingSlider.setValue(Math.round(newValue.doubleValue()));
        });

        try {
            List<Category> categories = categoryDAO.getAll();
            for (Category category : categories) {
                categoryComboBox.getItems().add(category);
            }
        } catch (SQLException e) {
            showError("Error loading categories: " + e.getMessage());
        }

        categoryComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selected (Lambda): " + newValue);
            if (categoryListView.getItems().contains(newValue)) {
                showError("Category already exists");
                return;
            }
            categoryListView.getItems().add(newValue);
        });

    }
}
