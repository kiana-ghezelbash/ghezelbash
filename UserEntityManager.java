package HW7SnapFood;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class UserEntityManager {

	boolean contain(String phonNumber, Connection con) throws Exception {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("Select * from User;");
		while (rs.next()) {
			if (rs.getString(2).equals(phonNumber))
				return true;
		}
		rs.close();
		stmt.close();
		return false;
	}

	public void add(User user, Connection con,int month) throws SQLException {
		Statement stmt = con.createStatement();
		int rs = stmt.executeUpdate("insert into user (name,telephonNumber,postID,enterMonth) " + "values ('" + user.name + "','"
				+ user.telephonNumber + "','" + user.postId + "'," + month + ");");
		Logger loger= LoggerFactory.getLogger(FactorEntityManager.class);
		loger.info("insert into user '" + user.name + "','"
				+ user.telephonNumber + "','" + user.postId + "'," + month);
		stmt.close();
	}

	public String getName(String telephonNumber,Connection con) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("Select name from User where telephonNumber='"+telephonNumber+"';");
		rs.next();
		String returnValue= rs.getString(1);
		rs.close();
		stmt.close();
		return returnValue;
	}
	
	public int getMonth(String name,Connection con) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("Select enterMonth from User where name='"+name+"';");
		rs.next();
		int returnValue= rs.getInt(1);
		rs.close();
		stmt.close();
		return returnValue;
	}
	
	public String getTelephoneNumber(String name,Connection con) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("Select telephonNumber from User where name='"+name+"';");
		rs.next();
		String returnValue= rs.getString(1);
		rs.close();
		stmt.close();
		return returnValue;
	}

	public String getPotID(String telephonNumber, Connection con) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("Select postID from User where telephonNumber='"+telephonNumber+"';");
		rs.next();
		String returnValue= rs.getString(1);
		rs.close();
		stmt.close();
		return returnValue;
	}
}
