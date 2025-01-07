module dk.easv.moviecollection {
  requires javafx.controls;
  requires javafx.fxml;

  opens dk.easv.moviecollection to javafx.fxml;
  exports dk.easv.moviecollection;
}