
public class KnuthMP {
	
	public static int[] computePrefixFunction(String p){
		if (p == null)
			return null;
		
		int m = p.length();
		int phiArray[] = new int[m];
		phiArray[0] = -1;
		phiArray[1] = 0;
		int k = 0;
		
		
		return phiArray;
		
		
	}

	
	//Power for both x and y 
	public double findPower(int x, int y){
		
		if(y == 1)
			return x;
		
		if(y == -1)
			return 1.0/x;
		
		if(y == 0)
			return 1;
		
		double term = findPower(x,y/2);
		
		if(y %2 == 0)
			return term * term;
		else
			{
			if (x >0)
			return term * term *x;
			else
				return term * term * 1.0/x;
			}
		
		}

	public static void main(String[] args){
		KnuthMP obj = new KnuthMP();
		double expVal = obj.findPower(2,-4); 
		System.out.println("Value of exponent is  "+expVal);
		
	}
	
	
}
