package HW7SnapFood;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SnapFood {
	Restaurant[] restaurants;

	public SnapFood() throws Exception {
		/*
		 * FileReader fileReader = new FileReader("E:\\Works\\restaurant.txt");
		 * BufferedReader bufferedReader = new BufferedReader(fileReader); int resNum =
		 * Integer.parseInt(bufferedReader.readLine()); for (int i = 0; i < resNum; i++)
		 * { String firstLine = bufferedReader.readLine(); if (firstLine != null) {
		 * String[] firstlines = firstLine.split(", "); try { ConnectionManager c = new
		 * ConnectionManager(); RestaurantEntityManager r = new
		 * RestaurantEntityManager(); r.add(c.getConnection(), firstlines);
		 * FoodEntityManger f = new FoodEntityManger(); for (int j = 0; j <
		 * Integer.parseInt(firstlines[1]); j++) { String foodData =
		 * bufferedReader.readLine(); String[] foodDatas = foodData.split(", ");
		 * f.add(c.getConnection(), foodDatas, firstlines[0]); } } catch (Exception e) {
		 * System.out.println(e.getMessage()); } } } bufferedReader.close();
		 * fileReader.close();
		 */
	}

	public static void main(String[] args) throws Exception {
		do {
			SnapFood snapfood = new SnapFood();
			snapfood.entering();
		} while (true);
	}

	void entering() throws Exception {
		ConnectionManager c = new ConnectionManager();
		System.out.println("are you \n1.user\n2.manager");
		if (getIntegerRang(1, 2) == 1)
			this.userMneu(c);
		else
			this.manager(c);
	}

	void manager(ConnectionManager c) throws Exception {
		System.out.println("enter usename and password.");
		Scanner scan = new Scanner(System.in);
		if (scan.next().equals("manager") && scan.next().equals("1234")) {
			do {
				System.out.println("welcome\n1.show client diagram.\n2.show restaurant diagram.");
				if (getIntegerRang(1, 2) == 1)
					this.clientDiagram(c);
				else
					this.restaurantDiagram(c);
				System.out.println("if you want to exit enter 0 else enter any key");
				if (scan.next().equals("0"))
					break;
			} while (true);
		} else {
			System.out.println("wrong username or password.");
			try {
				this.entering();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void restaurantDiagram(ConnectionManager c) throws Exception {
		FactorEntityManager factor = new FactorEntityManager();
		ArrayList<RestaurantInfotmationDiagram> digramInformation = factor
				.restautantDiagramInformation(c.getConnection());
		int counter = 1;
		Function<Integer, Stream<RestaurantInfotmationDiagram>> sectionfilter = section -> digramInformation.stream()
				.filter(r -> r.section == section);
		Predicate<Integer> hasSection = section -> digramInformation.stream().anyMatch(r -> r.section == section);

		System.out.println("counter		section		peyk incom		restaurant name		" + "most sell food");
		boolean tabInsert = false;
		for (int i = 1; i < 4; i++) {
			if (hasSection.test(i)) {
				System.out.print(counter + "		" + i + "	");
				counter++;
				if (sectionfilter.apply(i).anyMatch(r -> r.peykIncom < 10000)) {
					System.out.print("	less than 10000	");
					sectionfilter.apply(i).filter(r -> r.peykIncom < 10000).forEach(user -> System.out
							.print("	" + user.restaurantName + "			" + user.mostSellFood + "\n\t\t\t\t\t\t"));
					tabInsert = true;
					System.out.println();
				}
				if (sectionfilter.apply(i).anyMatch(f -> f.peykIncom > 10000 && f.peykIncom < 50000)) {
					if (tabInsert)
						System.out.print("			");
					System.out.print("	between 10000 and 50000	");
					sectionfilter.apply(i).filter(f -> f.peykIncom > 10000 && f.peykIncom < 50000)
							.forEach(user -> System.out
									.print(user.restaurantName + "			" + user.mostSellFood + "\n\t\t\t\t\t\t"));
					tabInsert = true;
					System.out.println();
				}
				if (sectionfilter.apply(i).anyMatch(f -> f.peykIncom > 50000)) {
					if (tabInsert)
						System.out.print("			");
					System.out.print("	more than 50000	");
					sectionfilter.apply(i).filter(f -> f.peykIncom > 50000).forEach(user -> System.out
							.print("	" + user.restaurantName + "			" + user.mostSellFood + "\n\t\t\t\t\t\t"));
					System.out.println();
				}
				tabInsert = false;
				System.out.println();
			}
		}
	}

	private void clientDiagram(ConnectionManager c) throws Exception {
		FactorEntityManager factor = new FactorEntityManager();
		Collection<Factor> digramInformation = factor.clientDiagramInformation(c.getConnection());
		int counter = 1;
		Function<Integer, Stream<Factor>> monthfilter = month -> digramInformation.stream()
				.filter(f -> f.enteringMonth == month);
		Predicate<Integer> hasMonth = month -> digramInformation.stream().anyMatch(f -> f.enteringMonth == month);
		System.out.println("counter		enter month		avrage buy			username	" + "telephone number");
		boolean tabInsert = false;
		for (int i = 1; i < 13; i++) {
			if (hasMonth.test(i)) {
				System.out.print(counter + "		" + i + "	");
				counter++;
				if (monthfilter.apply(i).anyMatch(f -> f.cost < 100000)) {
					System.out.print("		less than 100000	");
					monthfilter.apply(i).filter(f -> f.cost < 100000).forEach(user -> System.out
							.print("	" + user.username + "		" + user.telephoneNumber + "\n\t\t\t\t\t\t\t\t"));
					tabInsert = true;
					System.out.println();
				}
				if (monthfilter.apply(i).anyMatch(f -> f.cost > 100000 && f.cost < 500000)) {
					if (tabInsert)
						System.out.print("			");
					System.out.print("		between 100000 and 500000	");
					monthfilter.apply(i).filter(f -> f.cost > 100000 && f.cost < 500000).forEach(user -> System.out
							.print(user.username + "		" + user.telephoneNumber + "\n\t\t\t\t\t\t\t\t"));
					tabInsert = true;
					System.out.println();
				}
				if (monthfilter.apply(i).anyMatch(f -> f.cost > 500000)) {
					if (tabInsert)
						System.out.print("			");
					System.out.print("		more than 500000	");
					monthfilter.apply(i).filter(f -> f.cost > 500000).forEach(user -> System.out
							.print("	" + user.username + "		" + user.telephoneNumber + "\n\t\t\t\t\t\t\t\t"));
					System.out.println();
				}
				tabInsert = false;
				System.out.println();
			}
		}

	}

	void userMneu(ConnectionManager c) throws Exception {
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to Snap Food \nplease enter your phone number.");
		User user = new User(this.getTelephonNumber(scan));
		System.out.println("please enter your section number.");
		int mantaghe = getIntegerRang(1, 3);
		Map<String, Integer> basket = new HashMap<String, Integer>();
		do {
			System.out.println("\nplease enter the number you want to do\n1.show this section restaurants.\n"
					+ "2.show this section restaurants whit type limit.\n3.show restaurant Menu."
					+ "\n4.buy food.\n5.show my basket.\n6.remove food from basket."
					+ "\n7.finishing shoping.\n8.edit section.");
			int n = getInteger();
			RestaurantEntityManager r = new RestaurantEntityManager();
			switch (n) {
			case 1:
				r.show(c.getConnection(), mantaghe);
				break;
			case 2:
				r.showWhitTypeLimit(c.getConnection(), mantaghe);
				break;
			case 3:
				this.showRestaurantMenu(mantaghe, c.getConnection(), r);
				break;
			case 4:
				this.buyFood(user, mantaghe, r, c.getConnection(), basket, scan);
				break;
			case 5:
				this.showBasket(basket, user, c.getConnection());
				break;
			case 6:
				System.out.println("\nwhich food?");
				String foodName = this.getFoodName(user.restaurantNameForBuy, c.getConnection());
				if (basket.containsKey(foodName))
					this.removeFromShopingBasket(basket, user.restaurantNameForBuy, foodName);
				break;
			case 7:
				this.finishShoping(user.telephonNumber, basket, c.getConnection(), user,
						this.payment(basket, user.restaurantNameForBuy, c.getConnection()));
				return;
			case 8:
				System.out.println("\nso what section?");
				mantaghe = this.getIntegerRang(1, 3);
				break;
			default:
				System.out.println("\nselect something from menu");
			}
		} while (true);
	}

	private void buyFood(User user, int mantaghe, RestaurantEntityManager r, Connection c, Map<String, Integer> basket,
			Scanner scan) throws Exception {
		System.out.println("\nenter restaurant name.");
		user.restaurantNameForBuy = this.getrestaurantName(mantaghe, r, c);
		do {
			System.out.println("\nenter food name you whant .");
			String foodName = this.getFoodName(user.restaurantNameForBuy, c);
			System.out.println("\nhow many?");
			Integer count = this.getInteger();
			if (basket.containsKey(foodName))
				basket.replace(foodName, basket.get(foodName), basket.get(foodName) + count);
			else
				basket.put(foodName, count);
			System.out.println("\nfinished? if yes press 0 if not press any key.");
			if (scan.next().equals("0")) {
				break;
			}
		} while (true);
	}

	private void showRestaurantMenu(int mantaghe, Connection c, RestaurantEntityManager r) throws Exception {
		System.out.println("\nenter restaurant name.");
		String restaurantName = this.getrestaurantName(mantaghe, r, c);
		FoodEntityManger foodmanager = new FoodEntityManger();
		foodmanager.show(c, restaurantName);
	}

	private String getTelephonNumber(Scanner scan) {
		do {
			String telNum = scan.next();
			if (telNum.matches("\\d{11}"))
				return telNum;
			System.out.println("this is not in a telephon number format. try again.");
		} while (true);
	}

	private int getInteger() {
		Scanner scan = new Scanner(System.in);
		int n;
		do {
			try {
				n = scan.nextInt();
				break;
			} catch (Exception e) {
				System.out.println("enter integer.");
				scan.nextLine();
			}

		} while (true);
		return n;
	}

	private int getIntegerRang(int begening, int ending) {
		int number;
		Scanner scan = new Scanner(System.in);
		do {
			number = this.getInteger();
			if (number <= ending && number >= begening)
				break;
			else
				System.out.println("input is between " + begening + " to " + ending + ".");
		} while (true);
		return number;
	}

	private String getrestaurantName(int mantaghe, RestaurantEntityManager r, Connection c) throws Exception {
		Scanner scan = new Scanner(System.in);
		do {
			String name = scan.nextLine();
			String names = r.getRestaurantName(c, mantaghe, name);
			if (names.equals("noch"))
				System.out.println("\nthis is not a restaurant name or this restaurant is not in your Section.");
			else
				return names;
		} while (true);
	}

	private String getFoodName(String restaurantName, Connection c) throws Exception {
		Scanner scan = new Scanner(System.in);
		FoodEntityManger foodmanag = new FoodEntityManger();
		do {
			String names = foodmanag.getFoodName(c, scan.nextLine(), restaurantName);
			if (names.equals("noch"))
				System.out.println("\nthis is not a food name or this food is not in the menu.");
			else
				return names;
		} while (true);
	}

	void removeFromShopingBasket(Map<String, Integer> shopingBasket, String restaurant, String food) {
		Scanner scan = new Scanner(System.in);
		System.out.println("\nremove all of them?");
		do {
			String answer = scan.next();
			if (answer.equals("yes")) {
				shopingBasket.remove(food);
				return;
			} else if (answer.equals("no")) {
				System.out.println("\nso haw many?");
				int n = this.getInteger();
				if (n > shopingBasket.get(food).byteValue())
					System.out.println("\nthis is more than what it is in the basket\nremove all of them?");
				else if (n < 1)
					System.out.println("invalid number");
				else {
					n = shopingBasket.get(food).byteValue() - n;
					shopingBasket.replace(food, n);
					break;
				}

			} else
				System.out.println("please say yes or no");
		} while (true);
	}

	Long payment(Map<String, Integer> shopingBasket, String restaurant, Connection c) throws Exception {
		Set<Entry<String, Integer>> shopSet = shopingBasket.entrySet();
		Iterator<Entry<String, Integer>> itrator = shopSet.iterator();
		Long payment = (long) 0;
		while (itrator.hasNext()) {
			Entry<String, Integer> e = itrator.next();
			String foodName = e.getKey();
			FoodEntityManger foodmanag = new FoodEntityManger();
			payment += foodmanag.showPrice(c, foodName, restaurant) * e.getValue();
		}
		RestaurantEntityManager rem = new RestaurantEntityManager();
		Long peykMoney = rem.getPeykCost(restaurant, c);
		return payment + peykMoney;
	}

	void showBasket(Map<String, Integer> basket, User user, Connection con) throws Exception {
		if (!basket.isEmpty()) {
			System.out.println(basket + "\npayment:");
			System.out.println(this.payment(basket, user.restaurantNameForBuy, con).toString());
		}
	}

	void finishShoping(String telephonNumber, Map<String, Integer> basket, Connection con, User user, Long payment)
			throws Exception {
		Scanner scan = new Scanner(System.in);
		UserEntityManager usermanager = new UserEntityManager();
		if (!usermanager.contain(telephonNumber, con)) {
			System.out.println("you must enter name and PostID.");
			user.name = scan.next();
			user.postId = scan.next();
			System.out.println("which month are we in?");
			int month = this.getIntegerRang(1, 12);
			usermanager.add(user, con, month);
		} else {
			user.name = usermanager.getName(telephonNumber, con);
			user.postId = usermanager.getPotID(telephonNumber, con);
		}
		System.out.println("thank for your shoping^^");
		user.payment = payment;
		System.out.println("foods:");
		this.showBasket(basket, user, con);
		System.out.println("from restaurant:" + user.restaurantNameForBuy);
		FileWriter fileWriter = new FileWriter("E:\\Works\\" + user.name + ".txt");
		fileWriter.write(user.name + "\n" + "phone number:" + user.telephonNumber + "from restaurant:"
				+ user.restaurantNameForBuy + "\nfinal cost:" + user.payment.toString() + "foods:");
		fileWriter.close();
		FactorEntityManager factor = new FactorEntityManager();
		factor.inserInto(user, basket, con);
		this.writeBasket(user, basket);
	}

	private void writeBasket(User user, Map<String, Integer> basket) throws Exception {
		FileWriter fileWriter = new FileWriter("E:\\Works\\" + user.name + ".txt", true);
		Set<Entry<String, Integer>> shopSet = basket.entrySet();
		Iterator<Entry<String, Integer>> itrator = shopSet.iterator();
		while (itrator.hasNext()) {
			Entry<String, Integer> e = itrator.next();
			fileWriter.write(e.getKey() + " :" + e.getValue());
		}
		fileWriter.close();

	}

}
