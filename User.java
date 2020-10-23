package HW7SnapFood;

public class User {
	public User(String telephonNumber) {
		this.telephonNumber=telephonNumber;
	}
	public User() {}
	String telephonNumber;//primary key
	int sectionId;//shomare mantaghe
	String name;
	String postId;
	String restaurantNameForBuy;//for this shoping
	Long payment;//for this shoping
	int enteringMonth;
}
