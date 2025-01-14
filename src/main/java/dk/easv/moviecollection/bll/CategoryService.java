package dk.easv.moviecollection.bll;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.dal.DAOentities.CategoryDAO;

import java.sql.SQLException;
import java.util.List;

public class CategoryService {
    private final CategoryDAO categoryDAO = new CategoryDAO();

    private static List<Category> categories;

    public void loadCategories() throws SQLException {
        categories = categoryDAO.getAll();
    }

    public List<Category> getCategories() {
        return categories;
    }
}
