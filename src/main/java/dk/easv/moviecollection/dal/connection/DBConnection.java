package dk.easv.moviecollection.dal.connection;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection implements AutoCloseable {
    public Connection getConnection() throws SQLServerException {
        SQLServerDataSource ds;
        ds = new SQLServerDataSource();
        ds.setDatabaseName("PrivateMovieCollectionPEKI");
        ds.setUser("CSe2024b_e_23");
        ds.setPassword("CSe2024bE23!24");
        ds.setServerName("EASV-DB4");
        ds.setPortNumber(1433);
        ds.setTrustServerCertificate(true);
        return ds.getConnection();
    }

    public boolean testConnection() throws SQLException {
        try (Connection conn = getConnection()) {
            return true;
        }
    }

    @Override
    public void close() throws Exception {

    }
}
