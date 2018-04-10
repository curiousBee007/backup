
public class LoveRectangle {
	
	public static class Rectangle {

	    // coordinates of bottom left corner
	    Integer leftX;
	    Integer bottomY;

	    // dimensions
	    Integer width;
	    Integer height;

	    public Rectangle(Integer leftX, Integer bottomY, Integer width, Integer height) {
	        this.leftX = leftX;
	        this.bottomY = bottomY;
	        this.width  = width;
	        this.height = height;
	    }

	    public Rectangle() {}

	    public String toString() {
	        return String.format("(%d, %d, %d, %d)", leftX, bottomY, width, height);
	    }
	}
	
	
	public static Rectangle changeObject(Rectangle rect1, Rectangle rect2){
		
		Rectangle tempRect1 = rect1;
		Rectangle tempRect2 = rect2;
		if(tempRect1.leftX > tempRect2.leftX){
			rect1 = tempRect2;
			rect2 = tempRect1;
		}
		
		
		System.out.println("First rectangle leftPoint "+  rect1.leftX);
		System.out.println("Second rectangle leftPoint "+  rect2.leftX);
		
		int leftmostPoint = rect2.leftX;
		int bottomMostPoint = rect2.bottomY;
		int width = Math.abs((rect1.leftX +rect1.width) - rect2.leftX);
		int height = Math.abs((rect1.bottomY +rect1.height) - rect2.bottomY);
		Rectangle obj = new Rectangle(leftmostPoint,bottomMostPoint,width,height);
		return obj;
		
		
		
	}


	public static void main(String[] args){
		
		Rectangle obj1 = new Rectangle(4,5,10,10);
		Rectangle obj2 = new Rectangle(2,5,10,10);
		//Desired value should be 2 and 4
		changeObject(obj1, obj2);
		
	}
	
}
