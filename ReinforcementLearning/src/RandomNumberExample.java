import java.util.Random;

public class RandomNumberExample {
	
	
	public static int randomInteger(int min, int max) {

	    Random rand = new Random();

	    // nextInt excludes the top value so we have to add 1 to include the top value
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	
	
	
	
	public static void main(String[] args){
		
		for(int i = 0; i < 100;i++){
			int x = randomInteger(0,1);
			System.out.println(x);
			
			
			
		}
		
		//double random = new Random().nextDouble();
		//System.out.println(random);
	}
	
	
	

}
