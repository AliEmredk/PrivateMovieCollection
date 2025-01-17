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
    private static final ObservableList<Category> categories = FXCollections.observableArrayList();
    private final ObservableList<Movie> movies = FXCollections.observableArrayList();
    private final ObservableList<Movie> filteredMovies = FXCollections.observableArrayList();

    private static final String DEFAULT = "Default";
    private static final String TITLE_ASCENDING = "Title (A-Z)";
    private static final String TITLE_DESCENDING = "Title (Z-A)";
    private static final String DIRECTOR_ASCENDING = "Director (A-Z)";
    private static final String DIRECTOR_DESCENDING = "Director (Z-A)";
    private static final String RATING_ASCENDING = "Rating (Low to High)";
    private static final String RATING_DESCENDING = "Rating (High to Low)";
    private final String[] sortingMethods = {TITLE_ASCENDING, TITLE_DESCENDING, DIRECTOR_ASCENDING, DIRECTOR_DESCENDING, RATING_ASCENDING, RATING_DESCENDING};

    private String currentSortingMethod = DEFAULT;


    public void loadCategories() throws SQLException {
        categoryService.loadCategories();
        categories.setAll(categoryService.getCategories());
    }
    public void loadMoviesByCategory(Category category) {
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
        return sort(filterMovies(input, minRating, maxRating, movies));
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
            else if(input.contains(movie.getReleaseDate())) {
                if(movie.getRating() >= minRatingInt && movie.getRating() <= maxRatingInt) {
                    filteredMovies.add(movie);
                }
            }
        });
        return sort(filteredMovies);
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
        return sort(filterMovies(input, minRatingValue, maxRatingValue, filteredMovies));
    }

    public ObservableList<Movie> sort(ObservableList<Movie> movies) {
        switch (currentSortingMethod) {
            case TITLE_ASCENDING:
                movies.sort((o1, o2) -> o1.getTitle().compareTo(o2.getTitle()));
                break;
            case TITLE_DESCENDING:
                movies.sort((o1, o2) -> o2.getTitle().compareTo(o1.getTitle()));
                break;
            case DIRECTOR_ASCENDING:
                movies.sort((o1, o2) -> o1.getDirector().compareTo(o2.getDirector()));
                break;
            case DIRECTOR_DESCENDING:
                movies.sort((o1, o2) -> o2.getDirector().compareTo(o1.getDirector()));
                break;
            case RATING_ASCENDING:
                movies.sort((o1, o2) -> o1.getRating() - o2.getRating());
                break;
            case RATING_DESCENDING:
                movies.sort((o1, o2) -> o2.getRating() - o1.getRating());
                break;
            default:
                break;
        }
        return movies;
    }
    public String[] getSortingMethods() {
        return sortingMethods;
    }
    public void setCurrentSortingMethod(String currentSortingMethod) {
        this.currentSortingMethod = currentSortingMethod;
    }
}
