package dk.easv.moviecollection.gui;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.be.CategoryMovie;
import dk.easv.moviecollection.be.Movie;
import dk.easv.moviecollection.bll.MovieService;
import dk.easv.moviecollection.dal.DAOentities.CategoryDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MovieCreatorController extends Creator implements Initializable {

    private final MovieService movieService = new MovieService();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private static final String MOVIES_DIRECTORY_PATH = "src/main/resources/dk/easv/moviecollection/movies";

    private String moviePath;
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


    public void selectMovie() throws IOException {
        Stage stage = (Stage) nameTxt.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a movie");
        FileChooser.ExtensionFilter movieFilter = new FileChooser.ExtensionFilter("MP4, MPEG4 Files", "*.mp4");
        fileChooser.getExtensionFilters().add(movieFilter);
        // set initial directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (nameTxt.getText().isEmpty() || releaseDateTxt.getText().isEmpty() ||
                directorTxt.getText().isEmpty() || descriptionTxt.getText().isEmpty() ||
                ratingSlider.getValue() < 0 || ratingSlider.getValue() > 10) {
            showError("All fields must be filled, and a file must be selected.");
        }
        moviePath = setFilePath(selectedFile.getAbsolutePath());
    }
    public void addMovie() throws SQLException {

        Movie movie = new Movie();
        movie.setTitle(nameTxt.getText());
        movie.setDirector(directorTxt.getText());
        movie.setReleaseDate(releaseDateTxt.getText());
        movie.setDescription(descriptionTxt.getText());
        movie.setMoviePath(moviePath);

        int rating = (int) ratingSlider.getValue();
        movie.setRating(rating);

        movieService.createNew(movie);

        int movieId = movieService.getMovieWithHighestId().getId();
        List<Integer> categoryIds = new ArrayList<>();
        categoryListView.getItems().forEach(category -> categoryIds.add(category.getId()));

        categoryIds.forEach(categoryId -> {
            System.out.println(categoryId);
            CategoryMovie categoryMovie = new CategoryMovie(movieId, categoryId);
            try {
                movieService.createCategoryMovie(categoryMovie);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        if (categoryComboBox.getSelectionModel().isEmpty()) {
            showError("Please select a category.");
            return;
        }
        if(moviePath == null || moviePath.isEmpty()){
            showError("Please select a movie.");
            return;
        }

        Stage stage = (Stage) nameTxt.getScene().getWindow();
        stage.close();

    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void categoryComboBoxAct() {
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
