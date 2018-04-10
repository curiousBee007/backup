
public class Denomination1 {

	
	public static int count(int a[] , int denom , int amount){
		
		if(amount < 0)
			return 0;
		
		if(amount == 0)
			return 1;
		
		//No denomination left for that amount 
		if(denom <= 0 && amount >=1 )
			return 0;
		
		return count(a, denom -1,amount) + count(a,denom, amount - a[denom -1]);
		
		}
	
	 public static void main(String[] args) {
	        // run your function through some test cases here
	        // remember: debugging is half the battle!
	        String testInput = "test input";
	        int amount = 10;
	        int a[] = {2,3,5,6};
	        int len = a.length;
	        int x = count(a,len,amount);
	        		
	        System.out.println(x);
	    }
}
