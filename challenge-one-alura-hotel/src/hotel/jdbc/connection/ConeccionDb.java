package hotel.jdbc.connection;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConeccionDb {
	

    private final DataSource dataSource;
    
    public ConeccionDb() {

        var comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost/hotel?useTimeZone=true&serverTimeZone=UTC");
        comboPooledDataSource.setUser("root");
        comboPooledDataSource.setPassword("5taffSoport3");
        comboPooledDataSource.setMaxPoolSize(10);

        this.dataSource = comboPooledDataSource;
    }

    public Connection getConexion() {
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

	
}
