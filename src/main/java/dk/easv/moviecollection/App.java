package dk.easv.moviecollection;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.moviecollection.bll.CategoryService;
import dk.easv.moviecollection.dal.connection.DBConnection;
import dk.easv.moviecollection.gui.models.DataModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;

public class App extends Application
{
  private final DataModel dataModel = new DataModel();
  @Override public void start(Stage stage) throws IOException, SQLException {
    dataModel.loadCategories();
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("categories-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("Private Movie Collection");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args)
  {
    launch();
  }
}