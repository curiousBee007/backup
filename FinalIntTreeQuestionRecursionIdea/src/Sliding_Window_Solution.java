import java.util.LinkedList;

public class Sliding_Window_Solution {
	
	 public int[] maxSlidingWindow(int[] nums, int k) {
	        
         int n = nums.length;
       if (n == 0) {
           return nums;
       }
       int[] result = new int[n - k + 1];
       LinkedList<Integer> dq = new LinkedList<>();
       
       for (int i = 0; i < n; i++) {
          //peek : Retrieves but does not remove head
    	   //poll : Retrieves and remove head
           if (!dq.isEmpty() && dq.peek() < i - k + 1) {
               dq.poll();
           }
           
           while (!dq.isEmpty() && nums[i] >= nums[dq.peekLast()]) {
               dq.pollLast();
           }
           //offer adds element at the end
           dq.offer(i);
           if (i - k + 1 >= 0) {
               result[i - k + 1] = nums[dq.peek()];
           }
       }
       return result;
   }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sliding_Window_Solution obj = new Sliding_Window_Solution();
		int nums[] = {1,3,-1,-3,5,3,6,7};
		int k = 3;
	    int answer[] = obj.maxSlidingWindow(nums, k);
	    for(int i : answer){
	    	System.out.print("   "+i);
	    }
	    
	
	}

}
