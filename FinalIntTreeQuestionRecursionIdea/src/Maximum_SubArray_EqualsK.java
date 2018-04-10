import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Maximum_SubArray_EqualsK {
	
	 public int maxSubArrayLen(int[] nums, int k) {
	        if (nums == null || nums.length == 0)
	            return 0;
	        
	        int n = nums.length;
	        //You are actualy changing the initial array leaving the first intact
	        for (int i = 1; i < n; i++)
	            {nums[i] += nums[i - 1];
	            System.out.println("i value is "+i + "and nums value is "+nums[i]);
	            }
	        
	        for(Integer i : nums){
	        	System.out.print(" "+i);
	        	
	        }
	            
	        Map<Integer, Integer> map = new HashMap<>();
	        map.put(0, -1); // add this fake entry to make sum from 0 to j consistent
	        int max = 0;
	        
	        for (int i = 0; i < n; i++) {
	        	int key1 = nums[i] - k;
	            if (map.containsKey(key1)){
	            	//System.out.println("Inside and nums[i] value "+nums[i] +" and i value is" +i +" and key value is "+key1);
	                max = Math.max(max, i - map.get(nums[i] - k));}
	            if (!map.containsKey(nums[i])) // keep only 1st duplicate as we want first index as left as possible
	                map.put(nums[i], i);
	        }
	        return max;
	    }

	public static void main(String[] args) {
		
		Maximum_SubArray_EqualsK obj = new Maximum_SubArray_EqualsK();
		int n[] = {1,-1,5,-2,3};
		int x = obj.maxSubArrayLen(n, 3);
		System.out.println(" Answer is "+x);

	}

}
