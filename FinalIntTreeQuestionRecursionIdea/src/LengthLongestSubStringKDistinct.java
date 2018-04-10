import java.util.HashMap;
import java.util.Map;

public class LengthLongestSubStringKDistinct {
	
	
	public int lengthOfLongestSubstringKDistinct1(String s, int k) {
	    Map<Character, Integer> map = new HashMap<>();
	    
	    int left = 0;
	    int best = 0;
	   
	    for(int i = 0; i < s.length(); i++) {
	        // character at the right pointer
	        char c = s.charAt(i);
	        map.put(c, map.getOrDefault(c, 0) + 1);
	        // make sure map size is valid, no need to check left pointer less than s.length()
	        while (map.size() > k) {
	            char leftChar = s.charAt(left);
	            map.put(leftChar, map.get(leftChar) - 1);                     
	            if (map.get(leftChar) == 0) { 
	                map.remove(leftChar);
	            }
	            left++;
	        }
	        best = Math.max(best, i - left + 1);
	    }
	    return best;
	}

public static int lengthOfLongestSubstringKDistinct(String s, int k) {
        
        int[] count = new int[256];
        int num = 0, i = 0, res = 0;
        
        for (int j = 0; j < s.length(); j++) {
            
            if (count[s.charAt(j)]++ == 0) 
            	{
            	num++;
            	}
            
            
            if (num > k) {
                while (--count[s.charAt(i++)] > 0);
                num--;
            }
            res = Math.max(res, j - i + 1);
        }
        return res;
   
    }


   public static void main(String[] args){
	 
	   String s = "eceba";
	   int k = 2;
	   int longestLength = lengthOfLongestSubstringKDistinct(s,k);
	   System.out.println( " Longest path value "+longestLength);
	 
	   
 }
  
}
