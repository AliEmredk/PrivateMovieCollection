package dk.easv.moviecollection.dal.mappers;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.be.CategoryMovie;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMovieRowMapper implements IRowMapper<CategoryMovie>{
    @Override public CategoryMovie mapRow(ResultSet resultSet) throws SQLException
    {
        CategoryMovie categoryMovie = new CategoryMovie();
        categoryMovie.setId(resultSet.getInt("id"));
        categoryMovie.setCategoryId(resultSet.getInt("category_id"));
        categoryMovie.setMovieId(resultSet.getInt("movie_id"));
        return categoryMovie;
    }
}
