import java.util.*;

public class RecursivePermutation {

	public Set<String> getPermutations(String inputString) {

	    // base case
	    if (inputString.length() <= 1) {
	    	HashSet<String> baseSet = new HashSet<String>();
	    	baseSet.add(inputString);
	    	return baseSet;
	      // return new HashSet<String>(Arrays.asList(inputString));
	    }
        
	    int lastIndex = inputString.length() - 1;
	    String allCharsExceptLast = inputString.substring(0, lastIndex);
	    char lastChar = inputString.charAt(lastIndex);

	    // recursive call: get all possible permutations for all chars except last
	    Set<String> permutationsSet = getPermutations(allCharsExceptLast);

	    // put the last char in all possible positions for each of the above permutations
	    Set<String> permutations = new HashSet<String>();
	    for (String permutationOfAllCharsExceptLast : permutationsSet) {
	        for (int position = 0; position <= allCharsExceptLast.length(); position++) {
	            String permutation = permutationOfAllCharsExceptLast.substring(0, position) + lastChar + permutationOfAllCharsExceptLast.substring(position);
	            permutations.add(permutation);
	        }
	    }

	    return permutations;
	}
	
	public static void main(String[] args){
		
		RecursivePermutation obj = new RecursivePermutation();
		String inputString = "cat";
		
		HashSet<String> hashSetVal = (HashSet<String>) obj.getPermutations(inputString);
		for(String s : hashSetVal){
			System.out.println(s);
		}
		
	}
	
}
