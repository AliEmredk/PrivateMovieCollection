package dk.easv.moviecollection.dal.DAOentities;

import dk.easv.moviecollection.be.Category;
import dk.easv.moviecollection.be.CategoryMovie;
import dk.easv.moviecollection.be.Movie;
import dk.easv.moviecollection.dal.CrudDAO;
import dk.easv.moviecollection.dal.mappers.CategoryMovieRowMapper;
import dk.easv.moviecollection.dal.mappers.CategoryRowMapper;
import dk.easv.moviecollection.dal.mappers.IRowMapper;

import java.sql.SQLException;

public class CategoryMovieDAO extends CrudDAO<CategoryMovie> {
    private final IRowMapper<CategoryMovie> rowMapper;

    public CategoryMovieDAO() {
        this.rowMapper = new CategoryMovieRowMapper();;
    }

    public CategoryMovie createCategoryMovie(CategoryMovie categoryMovie) throws SQLException {
        return this.insertReturn(
                "insert into categoryMovie (category_id, movie_id) values (?,?)",
                new Object[]{categoryMovie.getCategoryId(), categoryMovie.getMovieId()},
                rowMapper,
                "select * from categoryMovie where id=?");
    }

}
