package dk.easv.moviecollection.dal.DAOentities;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.dal.CrudDAO;
import dk.easv.moviecollection.dal.mappers.CategoryRowMapper;

import java.sql.SQLException;

public class CategoryDAO extends CrudDAO<Category>
{
  CategoryRowMapper rowMapper = new CategoryRowMapper();

  public CategoryDAO(){
    super(Category.class);
  }
//  public CategoryDAO(Class<Category> entityClass)
//  {
//    super(entityClass);
//  }

  public Category createReturn(Category newCategory) throws SQLException
  {
    return this.insertReturn("insert into category (name) values ?", "return name from category where id = ?", new Object[]{newCategory.getName()}, rowMapper);
  }
}
