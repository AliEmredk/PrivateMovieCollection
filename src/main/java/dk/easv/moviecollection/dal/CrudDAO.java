package dk.easv.moviecollection.dal;
import dk.easv.moviecollection.dal.connection.DBConnection;
import dk.easv.moviecollection.dal.mappers.IRowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrudDAO <T>
{
  private final DBConnection db;

  public CrudDAO()
  {
    db = new DBConnection();
  }

  //Generic method for SELECT
  protected List<T> select(String query, Object[] params, IRowMapper<T> rowMapper) throws
      SQLException
  {
    try (PreparedStatement stmt = db.getConnection().prepareStatement(query)) {
      for (int i = 0; i < params.length; i++) {
        stmt.setObject(i + 1, params[i]);
      }
      try (ResultSet rs = stmt.executeQuery()) {
        List<T> results = new ArrayList<>();
        while (rs.next()) {
          results.add(rowMapper.mapRow(rs));
        }
        return results;
      }
    }
  }

  protected T insertReturn(String insertQuery, Object[] params, IRowMapper<T> rowMapper, String returnQuery) throws SQLException{
    Connection connection = db.getConnection();
    T result = null;
    connection.setAutoCommit(false);
    try(PreparedStatement stmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)){
      for (int i = 0; i < params.length; i++) {
        stmt.setObject(i + 1, params[i]);
      }
      int affectedRows = stmt.executeUpdate();
      if(affectedRows == 0){
        throw new SQLException("No rows affected");
      }
      int generatedId;

      try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
        if(generatedKeys.next()){
          generatedId = generatedKeys.getInt(1);
        }else {
          throw new SQLException("Added but id not returned");
        }
      }

      try(PreparedStatement returnStmt = connection.prepareStatement(returnQuery)){
        returnStmt.setInt(1, generatedId);
        try(ResultSet returnSet = returnStmt.executeQuery()){
          if(returnSet.next()){
            result = rowMapper.mapRow(returnSet);
          }
        }
      }
      connection.commit();
    }catch (SQLException e){
      connection.rollback();
      throw e;
    }
    return result;
  }

  // Generic method for UPDATE
  protected void update(String query, Object[] params) throws SQLException {
    try (PreparedStatement stmt = db.getConnection().prepareStatement(query)) {
      for (int i = 0; i < params.length; i++) {
        stmt.setObject(i + 1, params[i]);
      }
      stmt.executeUpdate();
    }
  }

  // Generic method for DELETE
  protected void delete(String query, Object[] params) throws SQLException {
    try (PreparedStatement stmt = db.getConnection().prepareStatement(query)) {
      for (int i = 0; i < params.length; i++) {
        stmt.setObject(i + 1, params[i]);
      }
      stmt.executeUpdate();
    }
  }
}
