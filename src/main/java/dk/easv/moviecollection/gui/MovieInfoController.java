package dk.easv.moviecollection.gui;

import dk.easv.moviecollection.be.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class MovieInfoController
{
  @FXML Label title;
  @FXML Label director;
  @FXML Label rating;
  @FXML MediaView mediaView;
  public static String TEST_MOVIE_FILE = "src/main/resources/dk/easv/moviecollection/movies/test.mp4";

  private Movie currentMovie;
  public void setMovie(Movie movie)
  {
    currentMovie = movie;
    title.setText("Title: " +currentMovie.getTitle());
    director.setText("Director: " + currentMovie.getDirector());
    rating.setText("Rating: " + currentMovie.getRating()+"");
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
}
