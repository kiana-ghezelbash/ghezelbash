package HW7SnapFood;

public class Location {
	int x;
	int y;
	
	public Location(int x,int y) {//x and y ke null nemishan pas :error managed.
		this.x=x;
		this.y=y;
	}
	
	int distance(Location ending) throws NullPointerException{//error managed.
		if(ending!=null) {
			int xDiff=this.x-ending.x;
			int yDiff=this.y-ending.y;
			xDiff*=xDiff;
			yDiff*=yDiff;
			double diff=xDiff+yDiff;
			diff=java.lang.Math.sqrt(diff);
			return (int) diff;
		}throw new NullPointerException("ending location is null");
		
	}
	
	public String toString() {//error managed.
		String temp="";
		temp=" x: "+x+" y: "+y;
		return temp;
	}
}
