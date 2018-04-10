import java.util.ArrayList;
import java.util.List;

public class jumpList {
	
 public static int jump_over_numbers(List<Integer> list) {
        
        if(list == null || list.size() == 0)
        return -1;
        //long number = 2L;
        
        //long number1 = Math.abs(number);
        //System.out.println(number1);
        
        int i = 0;
        int index = 0;
        int noOfJumps = 0;
        int length = list.size();
        // Write your code here
        while(i < length){
            
            if(list.get(i) == 0)
             return -1;
             
            index = index + list.get(i);
             noOfJumps = noOfJumps + 1;
            i = index;
           
            
        }
        return noOfJumps;
    }
 
 public static int digit_sum(long number) {
     // Write your code here
     long number1 = Math.abs(number);
     int sum = 0;
     while(number1 != 0){
         int x = (int)(number1 % 10);
         sum = sum + x;
         number1 = number1/10;
     }
     return sum;
 }
 
 public static boolean is_numeric_palindrome(long number) {
     long number1 = Math.abs(number);
     if (number1 < 10)
      return true;
      
      long newNo = 0;
      int noOfDigits = 0;
      
      long actualNumber = number1;
      long actualNumber2 = number1;
      
      while(number1 != 0){
          noOfDigits = noOfDigits + 1;
          number1 = number1 / 10;
          
      }
      
      noOfDigits = noOfDigits -1;
      while(actualNumber != 0){
          int digit = (int)actualNumber % 10;
          newNo = newNo + digit * (long)(Math.pow(10,noOfDigits));
          actualNumber = actualNumber/10;
          noOfDigits = noOfDigits -1;
      }
          
      System.out.println(actualNumber2);
      System.out.println(newNo);
      return (newNo == actualNumber2) ;
          
      
     
   // Write your code here
 }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> a = new ArrayList<Integer>();
		a.add(3);
		a.add(4);
		a.add(1);
		a.add(2);
		a.add(5);
		a.add(6);
		a.add(9);
		a.add(0);
		a.add(1);
		a.add(2);
		a.add(3);
		a.add(1);
		int n = jump_over_numbers(a);
		//System.out.println(n);
		long N = 1325132435356L;
		int sum = digit_sum(-3456);
		//System.out.println(sum);
		boolean val = is_numeric_palindrome(42);
		System.out.println(val);

	}

}
