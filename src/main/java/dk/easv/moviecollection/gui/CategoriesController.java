package dk.easv.moviecollection.gui;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.gui.models.DataModel;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;


import java.net.URL;
import java.util.ResourceBundle;

public class CategoriesController extends Page implements Initializable
{

  private static final DataModel dataModel = new DataModel();
  private static final NodeBuilder nodeBuilder = new NodeBuilder();

  @FXML
  private FlowPane flowPaneCategories;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    dataModel.getCategories().forEach(category -> flowPaneCategories.getChildren().add(nodeBuilder.categoryToVBox(category)));
    dataModel.getCategories().addListener((ListChangeListener<Category>) change -> {
      flowPaneCategories.getChildren().clear();
      while (change.next()) {
        if (change.wasAdded()) {
          for (Category category : change.getAddedSubList()) {
            VBox node = nodeBuilder.categoryToVBox(category);
            flowPaneCategories.getChildren().add(node);
          }
        }
      }
    });
  }
}
