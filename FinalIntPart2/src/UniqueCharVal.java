import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class UniqueCharVal {
	
	public int firstUniqChar(String s) {
        if(s.length() == 0)
        return -1;
        
        int countArray[] = new int[26];
        int index = -1;
        for(int i = 0; i < s.length();i++){
        	
        	char ch = s.charAt(i);
        	int val = ch - 97;
        	countArray[val] = countArray[val] +1;
        	
        	}
        
    for(int i = 0 ; i < s.length();i++){
        	char ch = s.charAt(i);
        	int val = ch - 97;
        	if(countArray[val] == 1){
        		return i;
        	}
        	
        }
           
        	
        return index;
    }
	
	public static void main(String [] args){
		UniqueCharVal obj = new UniqueCharVal();
		String s = "loveleetcode";
		int index = obj.firstUniqChar(s);
		System.out.println(index);
		
	}

}
