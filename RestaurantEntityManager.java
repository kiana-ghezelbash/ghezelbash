package HW7SnapFood;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RestaurantEntityManager {
	public void add(Connection con, String[] datas) throws Exception {
		Logger loger= LoggerFactory.getLogger(RestaurantEntityManager.class);
		Statement stmt = con.createStatement();
		stmt.executeUpdate("insert into restaurant (name,sectionId,hazinePeyk) values ('" + datas[0] + "'," + datas[2]
				+ "," + datas[3] + ");");
		loger.info("insert into restaurant '" + datas[0] + "'," + datas[2]+ "," + datas[3] );
		stmt.close();
	}

	public void show(Connection con, int mantaghe) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from restaurant where sectionID=" + mantaghe + ";");
		ArrayList<Restaurant> a = new ArrayList<Restaurant>();

		while (rs.next()) {
			a.add(new Restaurant(rs.getString(1), rs.getInt(2), rs.getLong(3)));
			System.out.println(a.get(a.size() - 1));
			FoodEntityManger foodmanager = new FoodEntityManger();
			foodmanager.showFoodTypes(con, a.get(a.size() - 1).name);
		}

		rs.close();
		stmt.close();
	}

	private int getType() {
		Scanner scan = new Scanner(System.in);
		int n;
		do {
			try {
				n = scan.nextInt();
				if (n >= 0 && n < 5)
					break;
				else
					System.out.println("invalid number.");
			} catch (Exception e) {
				System.out.println("enter integer.");
				scan.nextLine();
			}

		} while (true);
		return n;
	}

	public void showWhitTypeLimit(Connection con, int mantaghe) throws SQLException {
		Statement stmt = con.createStatement();
		Scanner scan = new Scanner(System.in);
		System.out.println("which types do you want,type 0 when you finish choosing?\n1.SEAFOOD,\r\n"
				+ "2.TREDITIONALL,\r\n" + "3.FASTFOOD,\r\n" + "4.INTERNATIONALL;");
		int[] foodTypes = new int[4];
		for (int i = 0; i < 4; i++) {
			int n = this.getType();
			if (n == 0)
				break;
			foodTypes[i] = n;
		}
		String[] a = new String[4];
		for (int i = 0; i < foodTypes.length; i++) {
			switch (foodTypes[i]) {
			case 1:
				a[i] = "SEAFOOD";
				break;
			case 2:
				a[i] = "TREDITIONALL";
				break;
			case 3:
				a[i] = "FASTFOOD";
				break;
			case 4:
				a[i] = "INTERNATIONALL";
				break;
			}
		}

		for (int i = 0; i < a.length; i++) {
			ResultSet rs = stmt.executeQuery("select restaurant.name from food inner join restaurant"
					+ " on restaurant.name=food.restaurantName AND restaurant.sectionId=" + mantaghe
					+ " AND food.type='" + a[i] + "' group by restaurant.name;");
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
			rs.close();
		}
		stmt.close();
	}

	public String getRestaurantName(Connection con, int mantaghe, String name) throws Exception {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from restaurant where sectionID=" + mantaghe + ";");

		while (rs.next()) {
			if (name.equals(rs.getString(1))) {
				return name;
			}
		}

		rs.close();
		stmt.close();
		return "noch";
	}

	public Long getPeykCost(String name, Connection con) throws Exception {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select hazinePeyk from restaurant where name='" + name + "';");
		rs.next();
		Long returnValue = rs.getLong(1);
		rs.close();
		stmt.close();
		return returnValue;
	}
	
	public int getSection(String name, Connection con) throws Exception {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select sectionId from restaurant where name='" + name + "';");
		rs.next();
		int returnValue = rs.getInt(1);
		rs.close();
		stmt.close();
		return returnValue;
	}
}
