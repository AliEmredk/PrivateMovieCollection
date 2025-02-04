package dk.easv.moviecollection.gui;

import dk.easv.moviecollection.be.Movie;
import dk.easv.moviecollection.bll.MovieService;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;

public class MovieInfoController extends Page
{
  @FXML private Label title;
  @FXML private Label director;
  @FXML private Label rating;
  @FXML private MediaView mediaView;

  private final MovieService movieService = new MovieService();
  public static String TEST_MOVIE_FILE = "src/main/resources/dk/easv/moviecollection/movies/test.mp4";

  private Movie currentMovie;
  public void setMovie(Movie movie)
  {
    currentMovie = movie;
    title.setText("Title: " +currentMovie.getTitle());
    director.setText("Director: " + currentMovie.getDirector());
    rating.setText("Rating: " + currentMovie.getRating());
  }

  public void play(){
    File videoFile;
    if(currentMovie.getMoviePath() != null){
      videoFile = new File(currentMovie.getMoviePath());
    }else{
      videoFile = new File(TEST_MOVIE_FILE);
    }

    File testFile = new File(TEST_MOVIE_FILE);

    Media media ;
    if(videoFile.exists()){
       media = new Media(videoFile.toURI().toString());
    }else{
      media = new Media(testFile.toURI().toString());
    }
    // Create a Media object

    // Create a MediaPlayer object
    MediaPlayer mediaPlayer = new MediaPlayer(media);

    mediaView.setMediaPlayer(mediaPlayer);

    // Play the video
    mediaPlayer.play();
  }

  @FXML
  private void deleteMovie() throws SQLException {
    movieService.deleteMovie(currentMovie);
    Stage stage = (Stage) mediaView.getScene().getWindow();
    stage.close();
  }

  @FXML private void openChangeRating(){
    // Create a new Stage (popup window)
    Stage popupStage = new Stage();

    // Set title for the popup
    popupStage.setTitle("Popup Window");

    // Create a TextField
    TextField textField = new TextField();
    textField.setPromptText("Enter text here");

    // Create a Button to close the popup
    Button closeButton = new Button("Change");
    closeButton.setOnAction(event -> {
      movieService.updateRating(currentMovie, textField.getText());
      setMovie(movieService.getMovieForID(currentMovie.getId()));
      popupStage.close();
    });

    // Add the TextField and Button to a VBox layout
    VBox layout = new VBox(10); // 10 is the spacing between elements
    layout.getChildren().addAll(textField, closeButton);
    layout.setStyle("-fx-padding: 10; -fx-alignment: center;");

    // Set up the Scene
    Scene scene = new Scene(layout, 300, 200); // Width: 300, Height: 200
    popupStage.setScene(scene);

    // Show the popup
    popupStage.showAndWait();
  }
}
