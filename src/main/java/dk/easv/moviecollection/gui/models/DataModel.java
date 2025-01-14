package dk.easv.moviecollection.gui.models;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.bll.CategoryService;
import dk.easv.moviecollection.exceptions.MovieException;
import dk.easv.moviecollection.gui.NodeBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class DataModel {

    private final CategoryService categoryService = new CategoryService();

    private static ObservableList<Category> categories = FXCollections.observableArrayList();;


    public void loadCategories() throws SQLException {
        categoryService.loadCategories();
        categories.setAll(categoryService.getCategories());
    }
    public ObservableList<Category> getCategories() {
        return categories;
    }
}
