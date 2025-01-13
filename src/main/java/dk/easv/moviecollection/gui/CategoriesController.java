package dk.easv.moviecollection.gui;

import dk.easv.moviecollection.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.io.IOException;

public class CategoriesController
{
  
  @FXML
  private Button addNewCategoryBtn;
  
  @FXML private Label welcomeText;

  @FXML protected void showCategoryCreator() throws IOException {
    // Show popup creator
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/dk/easv/moviecollection/categoryCreator.fxml"));

    if (loader.getLocation() == null) {
      throw new IOException("Fxml file not found");
    }

    Parent scene = loader.load();
    Stage stage = new Stage();
    stage.setScene(new Scene(scene));
    stage.setResizable(false);
    stage.setTitle("Category Creator");
    stage.centerOnScreen();
    stage.show();
  }
}