
public class BaseQues {
	
  public static String convertTo7(int num) {
        StringBuilder sb = new StringBuilder();
        int x = 1;
        if(num == 0)
            return "0";
        
        if(num < 0)
        	x = -1;
        
        num = Math.abs(num);
        while(num != 0){
        	int rem = num % 7;
        	sb.append(rem);
        	num = num/7;
        	 }
        if(x == -1)
        	sb.append("-");
        
        return sb.reverse().toString();
    
  
  }
  public static void main(String[] args){
	  
	  String s = convertTo7(0);
	  System.out.println(s);
  }

}
