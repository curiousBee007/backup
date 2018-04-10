import java.util.SortedSet;
import java.util.TreeSet;

//https://leetcode.com/problems/contains-duplicate-iii Editorial solution given /
/*Given an array of integers, find out whether there are two distinct indices i and 
 * j in the array such that the absolute difference between nums[i] and nums[j] is at most t and 
 * the absolute difference between i and j is at most k.
 */
/*
* This problem requires us to find ii and jj such that the following conditions are satisfied:

​∣i-j| <= k
|nums[i] - nums[j]| <= t
​∣
The naive approach is the same as Approach #1 in Contains Duplicate II solution, which keeps a virtual sliding window that holds the newest kk elements. In this way, Condition 1 above is always satisfied. We then check if Condition 2 is also satisfied by applying linear search.

Java

public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
    for (int i = 0; i < nums.length; ++i) {
        for (int j = Math.max(i - k, 0); j < i; ++j) {
            if (Math.abs(nums[i] - nums[j]) <= t) return true;
        }
    }
    return false;
}
// Time limit exceeded.
Complexity Analysis

Time complexity : O(n \min(k,n))O(nmin(k,n)). It costs O(\min(k, n))O(min(k,n)) time for each linear search. Note that we do at most nn comparisons in one search even if kk can be larger than nn.

Space complexity : O(1)O(1). We only used constant auxiliary space.
*
* */

public class Duplicates_three {

	public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
	    if(nums==null||nums.length<2||k<0||t<0)
	        return false;
	 
	    TreeSet<Long> set = new TreeSet<Long>();
	    for(int i=0; i<nums.length; i++){
	        long curr = (long) nums[i];
	 
	        long leftBoundary = (long) curr-t;
	        long rightBoundary = (long) curr+t+1; //right boundary is exclusive, so +1
	        
	        SortedSet<Long> sub = set.subSet(leftBoundary, rightBoundary);
	        if(sub.size()>0)
	            return true;
	 
	        set.add(curr);   
	 
	        if(i>=k){ // or if(set.size()>=k+1)
	            set.remove((long)nums[i-k]);
	        }
	    }
	 
	    return false;
	}



}
