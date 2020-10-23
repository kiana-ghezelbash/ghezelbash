package HW7SnapFood;

public class Factor {

	public Factor(int enteringMonth, String telephoneNumber, long cost,String username) {
		this.enteringMonth=enteringMonth;
		this.telephoneNumber=telephoneNumber;
		this.cost=cost;
		this.username=username;
	}
	int enteringMonth;
	long cost;
	String telephoneNumber;
	String username;
	
	public String toString() {
		return this.telephoneNumber+"/"+this.enteringMonth+"/"+this.cost;
	}
	
}
