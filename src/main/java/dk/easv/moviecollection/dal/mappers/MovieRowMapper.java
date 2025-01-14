package dk.easv.moviecollection.dal.mappers;

import dk.easv.moviecollection.be.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRowMapper implements IRowMapper<Movie>
{
  @Override public Movie mapRow(ResultSet resultSet) throws SQLException
  {
    Movie movie = new Movie();
    movie.setId(resultSet.getInt("id"));
    movie.setTitle(resultSet.getString("name"));
    movie.setDescription(resultSet.getString("description"));
    movie.setDirector(resultSet.getString("director"));
    movie.setReleaseDate(resultSet.getDate("releaseDate"));
    movie.setRating(resultSet.getInt("rating"));
    return movie;
  }
}
