import java.util.HashMap;
import java.util.*;

public class PhoneNumber {

	 public List<String> letterCombinations(String digits) {
	        
		 HashMap<Integer, String> hashmap = new HashMap<Integer, String>();
	        hashmap.put(2,"abc");
	        hashmap.put(3,"def");
	        hashmap.put(4,"ghi");
	        hashmap.put(5,"jkl");
	        hashmap.put(6,"mno");
	        hashmap.put(7,"pqrs");
	        hashmap.put(8,"tuv");
	        hashmap.put(9,"wxyz");
	        
	        List<String> letterComb = new ArrayList<String>();
	        
	        if(digits == null)
	        return null;
	        
	        if(digits.length() == 1){
	            
	            char s1 = digits.charAt(0);
	            int x = s1 - '0';
	            
	            if(x == 0 || x == 1)
	            return null;
	            
	            String s = hashmap.get(x);
	            
	            for (int j = 0 ; j< s.length(); j++){
	            	char s2 = s.charAt(j);
	                String s3 = "" +s2;
	                letterComb.add(j, s3);
	             }
	           
	            return letterComb;
	        }
	      
	        
	        for(int i = digits.length() -1; i >=0 ; i--){
	            
	            char s1 = digits.charAt(i);
	            int x = s1 - '0';
	            
	            if(x == 0 || x == 1)
	            continue;
	            
	            String s = hashmap.get(x);
	              
	                if (letterComb.isEmpty()){
	                    for (int j = 0 ; j< s.length(); j++){
	                    	char s2 = s.charAt(j);
	    	                String s3 = "" +s2;
	    	                letterComb.add(j, s3);
	                    }
	                    
	                }
	                    
	                else{
	                   List<String> letterComb1 = new ArrayList<String>();
	                    for(int k = 0; k < s.length(); k++)
	                      {
	                    for(int j = 0; j < letterComb.size();j++){
	                        String str = s.charAt(k) +letterComb.get(j);
	                        letterComb1.add(str);
	                     }
	                    
	                }    
	                    letterComb = letterComb1;
	                }
	                
	                
	            
	            
	            
	        }
	        
	        return letterComb;
	        
	    }
	 
	 
	 
	 public static void main(String[] args){
		 PhoneNumber obj = new PhoneNumber();
		 String str = "2";
		 List<String> list1 = obj.letterCombinations(str);
		 
		 for(int i = 0; i < list1.size();i++){
			 System.out.print( "  " + list1.get(i));
		 }
		 
		 
	   }
	}