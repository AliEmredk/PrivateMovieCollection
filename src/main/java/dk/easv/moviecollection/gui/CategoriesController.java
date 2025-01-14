package dk.easv.moviecollection.gui;

import dk.easv.moviecollection.App;
import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.bll.CategoryService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CategoriesController implements Initializable
{

  private static final CategoryService categoryService = new CategoryService();
  private static final NodeBuilder nodeBuilder = new NodeBuilder();

  @FXML
  private Button addNewCategoryBtn;

  @FXML
  private FlowPane flowPaneCategories;
  
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

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    List<Category> categories = categoryService.getCategories();
    categories.forEach(category1 -> flowPaneCategories.getChildren().add(nodeBuilder.categoryToVBox(category1)));
  }
}
