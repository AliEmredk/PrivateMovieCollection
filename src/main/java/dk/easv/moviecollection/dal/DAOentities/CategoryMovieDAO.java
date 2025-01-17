package dk.easv.moviecollection.dal.DAOentities;

import dk.easv.moviecollection.be.CategoryMovie;
import dk.easv.moviecollection.dal.CrudDAO;
import dk.easv.moviecollection.dal.mappers.CategoryMovieRowMapper;
import dk.easv.moviecollection.dal.mappers.IRowMapper;

import java.sql.SQLException;

public class CategoryMovieDAO extends CrudDAO<CategoryMovie> {
    private final IRowMapper<CategoryMovie> rowMapper;

    public CategoryMovieDAO() {
        this.rowMapper = new CategoryMovieRowMapper();;
    }

    public void createCategoryMovie(CategoryMovie categoryMovie) throws SQLException {
        this.insertReturn(
                "insert into categoryMovie (category_id, movie_id) values (?,?)",
                new Object[]{categoryMovie.getCategoryId(), categoryMovie.getMovieId()},
                rowMapper,
                "select * from categoryMovie where id=?");
    }

}
