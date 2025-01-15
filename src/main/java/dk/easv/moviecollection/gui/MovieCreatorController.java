package dk.easv.moviecollection.gui;

import dk.easv.moviecollection.be.Movie;
import dk.easv.moviecollection.bll.MovieService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieCreatorController {

    private final MovieService movieService = new MovieService();
    public ListView categoryListView;
    public ComboBox categoryComboBox;


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
        FileChooser.ExtensionFilter movieFilter= new FileChooser.ExtensionFilter("MP4 Files", "*.mp4");
        fileChooser.getExtensionFilters().add(movieFilter);
        // set initial directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedFile = fileChooser.showOpenDialog(stage);
        moviePath = selectedFile.toURI().toString();

        if (nameTxt.getText().isEmpty() || releaseDateTxt.getText().isEmpty() ||
                directorTxt.getText().isEmpty() || descriptionTxt.getText().isEmpty()){
            showError("All fields must be filled, and a file must be selected.");
        }

        Movie movie = new Movie();
        movie.setTitle(nameTxt.getText());
        movie.setDirector(directorTxt.getText());
        movie.setReleaseDate(releaseDateTxt.getText());
        movie.setDescription(descriptionTxt.getText());

        //movie.setRating(ratingSlider)
        movieService.createNew(movie);


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
}
