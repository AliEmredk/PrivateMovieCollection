package dk.easv.moviecollection.dal.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IRowMapper <T>
{
  T mapRow(ResultSet resultSet) throws SQLException;
}