package dk.easv.moviecollection.dal.mappers;

import dk.easv.moviecollection.be.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRowMapper implements IRowMapper<Movie>
{
  @Override public Movie mapRow(ResultSet resultSet) throws SQLException
  {
    return null;
  }
}
