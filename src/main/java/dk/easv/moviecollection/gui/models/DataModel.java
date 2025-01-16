package dk.easv.moviecollection.gui.models;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.be.Movie;
import dk.easv.moviecollection.bll.CategoryService;
import dk.easv.moviecollection.bll.MovieService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class DataModel {

    private final CategoryService categoryService = new CategoryService();
    private final MovieService movieService = new MovieService();

    private static ObservableList<Category> categories = FXCollections.observableArrayList();;
    private ObservableList<Movie> movies = FXCollections.observableArrayList();
    private ObservableList<Movie> filteredMovies = FXCollections.observableArrayList();

    public void loadCategories() throws SQLException {
        categoryService.loadCategories();
        categories.setAll(categoryService.getCategories());
    }
    public void loadMoviesByCategory(Category category) throws SQLException {
        movies.setAll(movieService.getAllMoviesForCategory(category));
    }
    public void loadMovies() throws SQLException {
        movieService.loadMovies();
        movies.setAll(movieService.getAllMovies());
    }
    public ObservableList<Category> getCategories() {
        return categories;
    }

    public ObservableList<Movie> getMovies() {
        return movies;
    }

    public ObservableList<Movie> getMoviesByInput(String input, String minRating, String maxRating) {
        return filterMovies(input, minRating, maxRating, movies);
    }
    public ObservableList<Movie> filterMovies(String input, String minRating, String maxRating, List<Movie> movies){
        filteredMovies.clear();
        int minRatingInt = Integer.parseInt(minRating);
        int maxRatingInt = Integer.parseInt(maxRating);
        movies.forEach(movie -> {
            if(movie.getTitle().toLowerCase().contains(input.toLowerCase())) {
                if(movie.getRating() >= minRatingInt && movie.getRating() <= maxRatingInt) {
                    filteredMovies.add(movie);
                }
            }
            else if(movie.getDirector().toLowerCase().contains(input.toLowerCase())) {
                if(movie.getRating() >= minRatingInt && movie.getRating() <= maxRatingInt) {
                    filteredMovies.add(movie);
                }
            }
            else if(input.contains(movie.getReleaseDate().toString())) {
                if(movie.getRating() >= minRatingInt && movie.getRating() <= maxRatingInt) {
                    filteredMovies.add(movie);
                }
            }
        });
        return filteredMovies;
    }

    public void deleteCategory(Category category) {
        categories.remove(category);
        try {
            categoryService.remove(category);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Movie> getMoviesByInputAndCategories(String input, String minRatingValue, String maxRatingValue, List<Category> selectedCategories) throws SQLException {
        List<Movie> filteredMovies = movieService.getMoviesForMultipleCategories(selectedCategories);
        movies.setAll(filteredMovies);
        return filterMovies(input, minRatingValue, maxRatingValue, filteredMovies);
    }
}
