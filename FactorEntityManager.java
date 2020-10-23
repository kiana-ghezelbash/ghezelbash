package HW7SnapFood;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FactorEntityManager {

	public void inserInto(User user, Map<String, Integer> basket, Connection con) throws Exception {
		Statement stmt = con.createStatement();
		Set<String> foodNames = basket.keySet();
		Random random = new Random();
		int code = random.nextInt();
		Logger loger= LoggerFactory.getLogger(FactorEntityManager.class);
		for (String food : foodNames) {
			stmt.executeUpdate("insert into factor (name,restaurantName,foodName,amount,code) values ('" + user.name
					+ "','" + user.restaurantNameForBuy + "','" + food + "'," + basket.get(food) + "," + code + ");");
			
			loger.info("insert into factor '" + user.name+ "','" + user.restaurantNameForBuy + "','" 
			+ food + "'," + basket.get(food) + "," + code );
		}
		stmt.close();

	}

	public Collection<Factor> clientDiagramInformation(Connection connection) throws Exception {
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("select * from factor ;");
		Map<String, Factor> information = new HashMap<>();
		FoodEntityManger foodmanager = new FoodEntityManger();
		while (rs.next()) {

			String userName = rs.getString(1);
			String restaurantName = rs.getString(2);
			String foodName = rs.getString(3);
			int amount = rs.getInt(4);
			long foodCost = foodmanager.showPrice(connection, foodName, restaurantName);
			foodCost *= amount;
			UserEntityManager usermanager = new UserEntityManager();
			if (information.containsKey(userName)) {
				long oldCost = information.get(userName).cost;
				Factor factor = new Factor(information.get(userName).enteringMonth,
						information.get(userName).telephoneNumber, oldCost + foodCost,userName);
				information.put(userName, factor);

			} else {

				Factor factor = new Factor(usermanager.getMonth(userName, connection),
						usermanager.getTelephoneNumber(userName, connection), foodCost,userName);
				information.put(userName, factor);
			}

		}

		rs.close();
		stmt.close();
		return information.values();
	}

	public ArrayList<RestaurantInfotmationDiagram> restautantDiagramInformation(Connection connection) throws Exception {
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt
				.executeQuery("select count(distinct code),restaurantname from factor group by restaurantname;");
		ArrayList<RestaurantInfotmationDiagram> information = new ArrayList<>();
		RestaurantEntityManager restaurantmanager = new RestaurantEntityManager();
		while (rs.next()) {
			RestaurantInfotmationDiagram restaurantinformation = new RestaurantInfotmationDiagram();
			restaurantinformation.restaurantName = rs.getString(2);
			int count = rs.getInt(1);
			long peykCost = restaurantmanager.getPeykCost(restaurantinformation.restaurantName, connection);
			restaurantinformation.peykIncom = peykCost * count;
			restaurantinformation.section = restaurantmanager.getSection(restaurantinformation.restaurantName,
					connection);
			Statement stmt2 = connection.createStatement();
			ResultSet rs2 = stmt2.executeQuery("select sum(amount),foodname from factor  "
					+ "where restaurantname='"+restaurantinformation.restaurantName+"'group by foodname order by "
							+ "sum(amount) desc limit 1;");
			rs2.next();
			restaurantinformation.mostSellFood=rs2.getString(2);
			information.add(restaurantinformation);
			rs2.close();
			stmt2.close();

		}
		rs.close();
		stmt.close();
		return information;
	}

}
