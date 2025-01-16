package dk.easv.moviecollection.dal.DAOentities;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.be.CategoryMovie;
import dk.easv.moviecollection.be.Movie;
import dk.easv.moviecollection.dal.CrudDAO;
import dk.easv.moviecollection.dal.HttpClientProvider;
import dk.easv.moviecollection.dal.mappers.IRowMapper;
import dk.easv.moviecollection.dal.mappers.MovieRowMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.sql.SQLException;
import java.util.List;

public class MovieDAO extends CrudDAO<Movie>
{
  private final IRowMapper<Movie> rowMapper;
  private final HttpClient client;

  public MovieDAO(){
    this.client = HttpClientProvider.getClient();
    this.rowMapper = new MovieRowMapper();
  }

  public List<Movie> getAll() throws SQLException
  {
    return this.select("select * from movies", new Object[]{}, rowMapper);
  }

  public Movie createNew(Movie movie) throws SQLException{
    return this.insertReturn(
        "insert into movies (title, release_date, director, description, rating, movie_path) values (?,?,?,?,?,?)",
        new Object[]{movie.getTitle(), movie.getReleaseDate(), movie.getDirector(), movie.getDescription(), movie.getRating(), movie.getMoviePath()},
        rowMapper,
        "select * from movies where id=?");
  }

  public void delete(Movie movie) throws SQLException{
    this.delete("delete from movies where id=?", new Object[]{movie.getId()});
  }

  public void update(Movie newMovie, Movie oldMovie) throws SQLException
  {
    this.update(
        "update movies set title=?, release_date=?, director=?, description=?, rating=? where id=?",
        new Object[]{newMovie.getTitle(), newMovie.getReleaseDate(), newMovie.getDirector(), newMovie.getDescription(), newMovie.getRating(), oldMovie.getId()});
  }

  public List<Movie> getMoviesForCategory(Category category) throws SQLException
  {
    String query = "SELECT movies.id, movies.title, movies.director, movies.release_date, movies.description, movies.rating FROM categoryMovie cm INNER JOIN movies ON cm.movie_id = movies.id WHERE cm.category_id = ?;";
    return this.select(query, new Object[]{category.getId()}, rowMapper);
  }
  public List<Movie> getMoviesForMultipleCategories(List<Category> categories) throws SQLException {
    // Build the SQL query dynamically
    StringBuilder queryBuilder = new StringBuilder(
            "SELECT m.id, m.title, m.director, m.release_date, m.description, m.rating " +
                    "FROM categoryMovie cm " +
                    "INNER JOIN movies m ON cm.movie_id = m.id " +
                    "WHERE cm.category_id IN ("
    );

    // Add placeholders for the category IDs
    queryBuilder.append("?,".repeat(categories.size()));
    queryBuilder.setLength(queryBuilder.length() - 1); // Remove trailing comma
    queryBuilder.append(") GROUP BY m.id, m.title, m.director, m.release_date, m.description, m.rating " +
            "HAVING COUNT(DISTINCT cm.category_id) = ?;");

    String query = queryBuilder.toString();

    // Prepare query parameters
    Object[] params = new Object[categories.size() + 1];
    for (int i = 0; i < categories.size(); i++) {
      params[i] = categories.get(i).getId();
    }
    params[categories.size()] = categories.size(); // Number of input categories

    // Execute query and map results
    return this.select(query, params, rowMapper);
  }

  public Movie fetchMovieByTitle(String title){
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(HttpClientProvider.apiMovieSearchUrl + title + HttpClientProvider.apiMovieAttributes))
        .header("Authorization", "Bearer "+HttpClientProvider.apiKeyPleaseDoNotSteal)
        .GET()
        .build();

    System.out.println(request);
    return null;
  }
  public Movie getMovieWithHighestId() throws SQLException {
    String query = "SELECT * FROM movies WHERE id = (SELECT MAX(id) FROM movies)";
    List<Movie> result = this.select(query, new Object[]{}, rowMapper);
    return result.isEmpty() ? null : result.getFirst();
  }
}
