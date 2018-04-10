import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class PerfectSquare {
	
	public int numSquares(int n) {
	     if(n <3)
	     return n;
	     
	     Queue<Integer> queue = new LinkedList<Integer>();
	     ArrayList<Integer> arrList = new ArrayList<Integer>();
	     for(int i = 1; i <= n; i++){
	         int temp = i * i;
	         
	         if(temp > n)
	         break;
	         
	         arrList.add(temp);
	        }
	     
	     for(int i = 0; i < arrList.size();i++){
	         int x = n - arrList.get(i);
	         if(x == 0){
	             return 1;
	         }
	         queue.add(x);
	      }
	    
	     int tempCount = 1;
	     int prevQueueSize = queue.size();
	     
	     while(prevQueueSize > 0){
	    	 
	     for(int i = 0; i < prevQueueSize;i++){
	    	 int val = queue.poll();
	    	 for(int j = 0; j < arrList.size();j++){
	    		 int arr1 = arrList.get(j);
	    		 int diff = val - arr1;
	    		 if(diff == 0){
	    			 tempCount = tempCount + 1;
	    			 return tempCount;
	    		 }
	    		 if(diff >0 && diff < 3)
	    		 {
	    			 tempCount = tempCount + diff;
	    			 return tempCount;
	    		 }
	    		 if(diff > 0){
	    			 queue.add(diff);
	    		 }
	    		 
	    	 }
	     }
	     prevQueueSize =  queue.size();
	     tempCount = tempCount + 1;
	     
	     }
	     
	     return tempCount;
	     }
	     
	public int numSquares1(int n) {
	     if(n <=3)
		     return n;
		     
		 int max = (int)Math.sqrt(n);
		 int[] dp = new int[n+1];
		 Arrays.fill(dp, Integer.MAX_VALUE);
		 
		 for(int i = 1; i <=n ; i++){
		     
		     for(int j = 1; j <= max; j++){
		         if(i == j * j)
		         { dp[i] = 1;
		             
		         }
		         
		         else if (i > j*j){
		             dp[i] = Math.min(dp[i], dp[i - j*j] +1);
		         }
		         
		     }
		     
		 }
		 
		 return dp[n];
		 
	    }
	
	         
	  public static void main(String[] args){
		  
		  PerfectSquare obj = new PerfectSquare();
		  int x = obj.numSquares1(7168);
		  System.out.println(x);
		  
	  }   
		
	

}
