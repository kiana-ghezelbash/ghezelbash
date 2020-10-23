package HW7SnapFood;

public class Restaurant {

	String name;
	Food[] food;
	int sectionId;//shomare mantaghe
	long hazinePeyk;
	FoodType[] foodTypes= new FoodType[4];
	
	public Restaurant(String name,int sectionId,long hazinePeyk) {
		this.name=name;
		this.sectionId=sectionId;
		this.hazinePeyk=hazinePeyk;
	}
	public String toString() {
		return this.name+" \nhazine peyk:"+Long.toString(hazinePeyk);
	}
		
	void foodMenu() {
		//TODO
		//chape esme ghazaha ba tozihateshan
	}
}
