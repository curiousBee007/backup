import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Minimum_Moves_Equal_Array {
	  public int minMoves(int[] nums) {
	      
	        if (nums.length == 0) return 0;
	        int min = nums[0];
	        for (int n : nums) min = Math.min(min, n);
	        int res = 0;
	        for (int n : nums) res += n - min;
	        return res;
	    
	    }
	  
	  
	  public  boolean containsDuplicate1(int[] nums) {
			 Set<Integer> set = new HashSet<Integer>();
			 for(int i : nums)
				 
				 if(!set.add(i))// if there is same
					 return true; 
			 return false;
		 }
	    
	  
	  
	  public boolean containsDuplicate(int[] nums) {
	        
	        if(nums == null)
	         return false;
	        
	      Map<Integer,Integer> hashmap = new HashMap<Integer,Integer>();
	        
	        for(int i = 0; i < nums.length; i++){
	            if(hashmap.containsKey(nums[i])){
	                return true;
	            }
	            
	            else{
	                hashmap.put(nums[i],1);
	            }
	        }
	        
	        return false;
	    }
}
