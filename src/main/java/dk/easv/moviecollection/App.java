package dk.easv.moviecollection;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.moviecollection.dal.connection.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application
{
  @Override public void start(Stage stage) throws IOException{
    FXMLLoader fxmlLoader = new FXMLLoader(
        App.class.getResource("categories-view.fxml"));
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