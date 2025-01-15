package dk.easv.moviecollection.gui.models;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.be.Movie;
import dk.easv.moviecollection.bll.CategoryService;
import dk.easv.moviecollection.bll.MovieService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class DataModel {

    private final CategoryService categoryService = new CategoryService();
    private final MovieService movieService = new MovieService();

    private static ObservableList<Category> categories = FXCollections.observableArrayList();;
    private ObservableList<Movie> movies = FXCollections.observableArrayList();

    public void loadCategories() throws SQLException {
        categoryService.loadCategories();
        categories.setAll(categoryService.getCategories());
    }
    public void loadMoviesByCategory(Category category) throws SQLException {
        movies.setAll(movieService.getAllMoviesForCategory(category));
    }
    
    public ObservableList<Category> getCategories() {
        return categories;
    }

    public ObservableList<Movie> getMovies() {
        return movies;
    }

    public ObservableList<Movie> getMoviesByInput(String input) {
        ObservableList<Movie> filteredMovies = FXCollections.observableArrayList();
        movies.forEach(movie -> {
            if(movie.getTitle().toLowerCase().contains(input.toLowerCase())) {
                filteredMovies.add(movie);
            }
            if(movie.getDirector().toLowerCase().contains(input.toLowerCase())) {
                filteredMovies.add(movie);
            }
            if(input.contains(movie.getReleaseDate().toString())) {
                filteredMovies.add(movie);
            }
        });
        return filteredMovies;
    }
}
