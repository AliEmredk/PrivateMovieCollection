package dk.easv.moviecollection.dal.DAOentities;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.dal.CrudDAO;
import dk.easv.moviecollection.dal.mappers.CategoryRowMapper;
import dk.easv.moviecollection.dal.mappers.IRowMapper;

import java.sql.SQLException;
import java.util.List;

public class CategoryDAO extends CrudDAO<Category>
{
  private final IRowMapper<Category> rowMapper;

  public CategoryDAO() {
    rowMapper = new CategoryRowMapper();
  }

  public List<Category> getAll() throws SQLException
  {
    return this.select("select * from categories", new Object[]{}, rowMapper);
  }

  public Category createNew(Category category) throws SQLException
  {
    return this.insertReturn(
        "insert into categories (name) values (?)",
          new Object[]{category.getName()},
          rowMapper,
        "select * from categories where id = ?");
  }

  public void delete(Category category) throws SQLException
  {
    this.delete("delete from categories where id = ? ", new Object[]{category.getId()});
  }

  public void update(Category oldCategory, Category newCategory)
      throws SQLException
  {
    this.update("update categories set name = ? where id = ?", new Object[]{newCategory.getName(), oldCategory.getId()});
  }
}
