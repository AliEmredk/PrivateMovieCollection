package dk.easv.moviecollection.dal.mappers;

import dk.easv.moviecollection.be.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper implements IRowMapper<Category>
{
  @Override public Category mapRow(ResultSet resultSet) throws SQLException
  {
    Category category = new Category();
    category.setId(resultSet.getInt("id"));
    category.setName(resultSet.getString("name"));
    return category;
  }
}
