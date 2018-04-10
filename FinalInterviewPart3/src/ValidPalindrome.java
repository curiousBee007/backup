
public class ValidPalindrome {
	
	
	 public boolean isPalindrome(String s) {
		 
		 String x = s.replaceAll("[^a-zA-Z0-9]", "");
		 x = x.toLowerCase();
		 
		 if (x.length() == 0 || s == "")
			 return true;
	    
	    if (x.length() == 1)
	        return true;
	        
	   if(x.length() == 2)
	     return (x.charAt(0) == x.charAt(1));
		 
		 //System.out.println(x);
		 int lastIndex= x.length() -1;
		 int firstIndex = 0;
		 boolean isPalindrome = true;
		 
		 
		 while (firstIndex != lastIndex  && firstIndex <= s.length() -1 && lastIndex >=0 ){
			 
			 if(x.charAt(firstIndex) != x.charAt(lastIndex)){
				 isPalindrome =false;
				 break;
				 //return isPalindrome;
			 }
			 firstIndex ++;
			 lastIndex --;
				 
			 
		 }
		 
		 
		 
		 return isPalindrome;
		 
		 
	        
	    }
	 public static void main(String[] args){
		 ValidPalindrome obj = new ValidPalindrome();
		 String s1 = "A man, a plan, a canal: Panama";
		 String s2 = "race a car";
		 String s3 = "0P";
		 boolean bool = obj.isPalindrome(s3);
		 System.out.println(bool);
		 
	 }
	 
	

}
