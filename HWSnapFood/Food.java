package HW7SnapFood;

public class Food {
	String name;
	long cost;
	//FoodType foodType;//yektast
	String restaurantName;
	String foodType;
	
	public Food(String name, String restaurntName,String foodType,long cost) {
		this.name=name;
		this.restaurantName=restaurntName;
	    //this.foodType=(FoodType)foodType;
		this.cost=cost;
		this.foodType=foodType;		
	}
	public String toString() {
		return name+" cost:"+cost;
	}
	
}
