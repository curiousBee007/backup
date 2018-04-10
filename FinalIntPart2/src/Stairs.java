
public class Stairs {
	
	public int noOfWays(int n){
		
		if (n < 0)
			return 0;
		
		if(n == 0)
			return 1;
		
		return noOfWays( n -1) + noOfWays(n-2);
		
		
	}

	
	public static void main(String[] args){
		
		Stairs obj = new Stairs();
		int x = obj.noOfWays(4);
		System.out.println(x);
		
	}
}
