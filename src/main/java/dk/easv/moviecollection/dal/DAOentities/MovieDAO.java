package dk.easv.moviecollection.dal.DAOentities;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.be.Movie;
import dk.easv.moviecollection.dal.CrudDAO;
import dk.easv.moviecollection.dal.mappers.IRowMapper;
import dk.easv.moviecollection.dal.mappers.MovieRowMapper;

import java.sql.SQLException;
import java.util.List;

public class MovieDAO extends CrudDAO<Movie>
{
  private final IRowMapper<Movie> rowMapper;

  public MovieDAO(){
    rowMapper = new MovieRowMapper();
  }

  public List<Movie> getAll() throws SQLException
  {
    return this.select("select * from movies", new Object[]{}, rowMapper);
  }

  public Movie createNew(Movie movie) throws SQLException{
    return this.insertReturn(
        "insert into movies (name, release_date, director, description, rating) values (?,?,?,?,?)",
        new Object[]{movie.getTitle(), movie.getReleaseDate(), movie.getDirector(), movie.getDescription(), movie.getRating()},
        rowMapper,
        "select * from movies where id=?");
  }

  public void delete(Movie movie) throws SQLException{
    this.delete("delete from movies where id=?", new Object[]{movie.getId()});
  }

  public void update(Movie newMovie, Movie oldMovie) throws SQLException
  {
    this.update(
        "update movies set name=?, release_date=?, director=?, description=?, rating=? where id=?",
        new Object[]{newMovie.getTitle(), newMovie.getReleaseDate(), newMovie.getDirector(), newMovie.getDescription(), newMovie.getRating(), oldMovie.getId()});
  }

  public List<Movie> getMoviesForCategory(Category category) throws SQLException
  {
    String query = "SELECT movies.id, movies.title, movies.director, movies.release_date, movies.description, movies.rating FROM categoryMovie cm INNER JOIN movies ON cm.movie_id = movies.id WHERE cm.category_id = ?;";
    return this.select(query, new Object[]{category.getId()}, rowMapper);
  }
}
