package HW7SnapFood;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionManager {
	private Connection con ;
	public Connection getConnection() {
		return con;
	}
	public ConnectionManager() throws Exception {
	Class.forName("com.mysql.jdbc.Driver");
	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/snapfood","root","13751375");
	Logger loger= LoggerFactory.getLogger(FactorEntityManager.class);
	loger.info("get connection");
	}
	public void close() throws SQLException {
		con.close();
		
	}
}
