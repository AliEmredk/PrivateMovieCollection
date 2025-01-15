package dk.easv.moviecollection.bll;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.dal.DAOentities.CategoryDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    private final CategoryDAO categoryDAO;
    private static List<Category> categories;

    public CategoryService() {
        this.categoryDAO = new CategoryDAO();
        categories = new ArrayList<>();
    }

    public void loadCategories() throws SQLException {
        categories = categoryDAO.getAll();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public Category createNew(Category category) throws SQLException {
        return categoryDAO.createNew(category);
    }


    public void remove(Category category) throws SQLException {
        categoryDAO.delete(category);
    }
}