
public class Denomination {
	  static int noOfWays =0;
	 public static int noDenominations(int amount , int array[]) {
	     
		 if(amount == array[0])
			 return 1;
		 
		 if(amount < array[0])
	            return 0;
	        
	        int highestDenomination = -1;
	        
	        //System.out.println(array[array.length -1]);
	        for(int i = (array.length -1); i >= 0; i--){
	        	//System.out.println( "den value is  "+array[i] +"  Cal Val " +amount * 1.0/ array[i]);
	           
	            if((amount * 1.0/ array[i]) > 1.0){
	             highestDenomination = array[i];
	             System.out.println(" Highest den "+ highestDenomination + " amount is" +amount);
	            break;
	           }
	        }
	       
	        
	            if(highestDenomination > 0){
	           noOfWays= noOfWays + noDenominations(highestDenomination,array)
	                 + noDenominations(amount - highestDenomination, array);
	            }
	            
	            return noOfWays;
	            
	        
	        }
	 
	 
	    public static void main(String[] args) {
	        // run your function through some test cases here
	        // remember: debugging is half the battle!
	        String testInput = "test input";
	        int amount = 10;
	        int a[] = {2,3,5,6};
	        int x = noDenominations(amount,a);
	        		
	        System.out.println(x);
	    }
}
