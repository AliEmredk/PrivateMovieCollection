package dk.easv.moviecollection.gui;

import dk.easv.moviecollection.App;
import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.dal.QueryBuilder;
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
    NodeBuilder nodeBuilder = new NodeBuilder();
    Category category = new Category("Horror");
    Category category2 = new Category("Action");
    Category category3 = new Category("Comedy");
    Category category4 = new Category("Drama");
    Category category5 = new Category("Fantasy");
    Category category6 = new Category("Music");
    Category category7 = new Category("Romance");
    Category category8 = new Category("Science");
    Category category9 = new Category("Action");
    Category category10 = new Category("Action");
    Category category11 = new Category("Action");
    Category category12 = new Category("Action");
    Category category13 = new Category("Action");
    Category category14 = new Category("Action");
    List<Category> list = new ArrayList<>();
    list.add(category);
    list.add(category2);
    list.add(category3);
    list.add(category4);
    list.add(category5);
    list.add(category6);
    list.add(category7);
    list.add(category8);
    list.add(category9);
    list.add(category10);
    list.add(category11);
    list.add(category12);
    list.add(category13);
    list.add(category14);
    list.forEach(category1 -> flowPaneCategories.getChildren().add(nodeBuilder.categoryToVBox(category1)));
  }
}
