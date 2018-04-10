import java.util.HashSet;
import java.util.Set;

//Longest consecutive sequence which can be found in array
public class Longest_Consecutive_Sequence {
	
	public static int longestConsecutive(int[] num) {
		// if array is empty, return 0
		if (num.length == 0) {
			return 0;
		}
	 
		Set<Integer> set = new HashSet<Integer>();
		int max = 1;
	 
		for (int e : num)
			set.add(e);
	 
		for (int e : num) {
			int left = e - 1;
			int right = e + 1;
			int count = 1;
			
			//System.out.println(" Initial left value "+left);
			//System.out.println(" Initial right value "+right);
	 
			while (set.contains(left)) {
				count++;
				
				//Removes the element if present
				if (set.remove(left))
				  left--;
				}
	 
			while (set.contains(right)) {
				count++;
				
				if(set.remove(right))
				right++;
			}
	 
			max = Math.max(count, max);
		}
	 
		return max;
	}

	
	public static void main(String[] args){
		
		Longest_Consecutive_Sequence obj = new Longest_Consecutive_Sequence();
		int []num = {100, 4, 200, 1, 3, 2};
		int max = longestConsecutive(num);
		System.out.println(max);
		
	}
}