import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class AnyPermutationPalindrome {
	
	
	

	  public boolean hasPalindromePermutation(String theString) {

	      // track characters we've seen an odd number of times
	      Set<Character> unpairedCharacters = new HashSet<Character>();

	      for (char c : theString.toCharArray()) {
	          if (unpairedCharacters.contains(c)) {
	              unpairedCharacters.remove(c);
	          } else {
	              unpairedCharacters.add(c);
	          }
	      }

	      // the string has a palindrome permutation if it
	      // has one or zero characters without a pair
	      return unpairedCharacters.size() <= 1;
	  }
	
	
	
	
	
	
	
	
	
	
	
	

	public boolean checkPalindrome(String str){
		
		boolean check = false;
		int character_odd_count = 0;
		int character_even_count = 0;
		int lengthVal = str.length();
		
		Map<Character,Integer> map1 = new HashMap<Character,Integer>();
		
		for(Character ch : str.toCharArray()){
			
			if(!map1.isEmpty() && map1.containsKey(ch)){
				
				int value = map1.get(ch);
				value = value + 1;
				map1.put(ch, value);
				
				if(value % 2 == 0){
					character_even_count = character_even_count +1;
					character_odd_count = character_odd_count -1;
					
				}
				else{
					character_odd_count = character_odd_count +1;
					character_even_count = character_even_count -1;
				}
				
			}
			
			
			else{
				map1.put(ch, 1);
				character_odd_count = character_odd_count  +1;
				}
			
			}
		
		
		System.out.println(" Odd count  "+character_odd_count);
		System.out.println( " Even count  " +character_even_count);
		
				
		if( lengthVal %2 == 1 && character_odd_count ==1){
			check = true;
			return(check);
			
		}
		
		if(lengthVal %2 == 0 && character_odd_count  == 0){
			
			check = true;
			return(check);
		}
		
		
	return check;
		
	}
	
	
	public static void main(String[] args){
		
		String str = "livci";
		AnyPermutationPalindrome obj = new AnyPermutationPalindrome();
		boolean isP = obj.checkPalindrome(str);
		System.out.println(isP);
		
		
	}
	
	
}
