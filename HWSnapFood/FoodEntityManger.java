package HW7SnapFood;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FoodEntityManger {
	public void show(Connection con,String restaurantName) throws SQLException{
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("select name,cost from food where restaurantname='"+restaurantName+"';");		
		while(rs.next())
		System.out.println(rs.getString(1)+" cost:"+rs.getLong(2));
		rs.close();
		stmt.close();
	}
	public String getFoodName(Connection con,String name,String restaurantName) throws Exception {
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("select name from food where restaurantName='"+restaurantName+"';");  
		
		while(rs.next())  {
			if(name.equals(rs.getString(1))) {
				return name;
			}
		}	
		rs.close();
		stmt.close();
		return "noch";
	}
	public void showFoodTypes(Connection con,String restaurantName) throws SQLException{
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("select DISTINCT type from food WHERE restaurantName='"+restaurantName+"';");  
		ArrayList<Food> foodArray = new ArrayList<Food>();
		System.out.println("food type:");
		while(rs.next())  {
		
		System.out.println(rs.getString(1));
		
		  }
		rs.close();
		stmt.close();
	}
	public Long showPrice(Connection con,String name,String restaurantName) throws Exception {
		Long price;
		
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("select cost from food where restaurantName='"+restaurantName+"' AND name='"+name+"';");  
		rs.next();
		price = rs.getLong(1);
		rs.close();
		stmt.close();
		
		return price;
	}
	public void add(Connection con, String[] datas,String restaurantName) throws Exception {
		Statement stmt=con.createStatement();  
		int rs=stmt.executeUpdate("insert into food (name,restaurantName,type,cost) values ('"+datas[0]
				+"','"+restaurantName+"','"+datas[2]+"',"+datas[1]+");") ;
		Logger loger= LoggerFactory.getLogger(FactorEntityManager.class);
		loger.info("insert into food '"+datas[0]
				+"','"+restaurantName+"','"+datas[2]+"',"+datas[1]);
		stmt.close();
	}
}
