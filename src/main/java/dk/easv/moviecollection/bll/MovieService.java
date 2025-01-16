package dk.easv.moviecollection.bll;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.be.CategoryMovie;
import dk.easv.moviecollection.be.Movie;
import dk.easv.moviecollection.dal.DAOentities.CategoryDAO;
import dk.easv.moviecollection.dal.DAOentities.CategoryMovieDAO;
import dk.easv.moviecollection.dal.DAOentities.MovieDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieService
{
  private final MovieDAO movieDAO;
  private final CategoryMovieDAO categoryMovieDAO;
  private static List<Movie> movies;

  public MovieService(){
    this.movieDAO = new MovieDAO();
    this.categoryMovieDAO = new CategoryMovieDAO();
    movies = new ArrayList<>();
  }
  public void loadMovies() throws SQLException {
    movies = movieDAO.getAll();
  }

  public List<Movie> getAllMovies(){
    return movies;
  }

  public Movie createNew(Movie movie) throws SQLException {
    return movieDAO.createNew(movie);
  }

  public List<Movie> getAllMoviesForCategory(Category category){
    try {
      return movieDAO.getMoviesForCategory(category);
    }catch (SQLException e){
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  public void fetchMovieByTitle(String title){
    movieDAO.fetchMovieByTitle(title);
  }

  public CategoryMovie createCategoryMovie(CategoryMovie categoryMovie) throws SQLException {
    return categoryMovieDAO.createCategoryMovie(categoryMovie);
  }
  public Movie getMovieWithHighestId() throws SQLException{
    return movieDAO.getMovieWithHighestId();
  }
  public List<Movie> getMoviesForMultipleCategories(List<Category> categories) throws SQLException {
    return movieDAO.getMoviesForMultipleCategories(categories);
  }
}
