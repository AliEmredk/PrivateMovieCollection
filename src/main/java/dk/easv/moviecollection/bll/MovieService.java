package dk.easv.moviecollection.bll;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.be.Movie;
import dk.easv.moviecollection.dal.DAOentities.MovieDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieService
{
  private final MovieDAO movieDAO;

  public MovieService(){
    this.movieDAO = new MovieDAO();
  }

  public List<Movie> getAllMovies(){
    try{
      return movieDAO.getAll();
    }catch (SQLException e){
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  public List<Movie> getAllMoviesForCategory(Category category){
    try {
      return movieDAO.getMoviesForCategory(category);
    }catch (SQLException e){
      e.printStackTrace();
    }
    return new ArrayList<>();
  }
}
