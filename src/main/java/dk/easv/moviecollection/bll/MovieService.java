package dk.easv.moviecollection.bll;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.be.CategoryMovie;
import dk.easv.moviecollection.be.Movie;
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

  public void createNew(Movie movie) throws SQLException {
    movieDAO.createNew(movie);
  }

  public List<Movie> getAllMoviesForCategory(Category category){
    try {
      return movieDAO.getMoviesForCategory(category);
    }catch (SQLException e){
      e.printStackTrace();
    }
    return new ArrayList<>();
  }


  public void createCategoryMovie(CategoryMovie categoryMovie) throws SQLException {
    categoryMovieDAO.createCategoryMovie(categoryMovie);
  }
  public Movie getMovieWithHighestId() throws SQLException{
    return movieDAO.getMovieWithHighestId();
  }
  public List<Movie> getMoviesForMultipleCategories(List<Category> categories) throws SQLException {
    return movieDAO.getMoviesForMultipleCategories(categories);
  }
  public void deleteMovie(Movie movie) throws SQLException {
    movieDAO.delete(movie);
  }
}
