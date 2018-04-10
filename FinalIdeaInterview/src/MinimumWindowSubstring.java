import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

public class MinimumWindowSubstring {

	
	public String minWindow(String s, String t) {
	       
	       if(t.length()>s.length()) 
	        return "";
	    String result = "";
	 
	    //character counter for t
	    HashMap<Character, Integer> target = new HashMap<Character, Integer>();
	    for(int i=0; i<t.length(); i++){
	        char c = t.charAt(i);    
	        
	        if(target.containsKey(c)){
	            target.put(c,target.get(c)+1);
	        }else{
	            target.put(c,1);  
	        }
	    }
	 
	    // character counter for s
	    HashMap<Character, Integer> map = new HashMap<Character, Integer>();
	    int left = 0;
	    int minLen = s.length()+1;
	 
	    Stack<String> stack = new Stack<String>();
	    int count = 0; // the total of mapped characters
	 
	    for(int i=0; i<s.length(); i++){
	        
	        char c = s.charAt(i);
	 
	        if(target.containsKey(c)){
	            
	            if(map.containsKey(c)){
	                if(map.get(c)<target.get(c)){
	                    count++;
	                }
	                map.put(c,map.get(c)+1);
	            }else{
	                map.put(c,1);
	                count++;
	            }
	        }
	 
	        if(count == t.length()){
	            char sc = s.charAt(left);
	            
	            while (!map.containsKey(sc) || map.get(sc) > target.get(sc)) {
	                if (map.containsKey(sc) && map.get(sc) > target.get(sc))
	                    map.put(sc, map.get(sc) - 1);
	                left++;
	                sc = s.charAt(left);
	            }
	 
	            if (i - left + 1 < minLen) {
	                result = s.substring(left, i + 1);
	                minLen = i - left + 1;
	            }
	        }
	    }
	 
	    return result; 
}
	
	public static String simplifyPath(String path) {
	     
		
		Deque<String> stack = new LinkedList<>();
	    Set<String> skip = new HashSet<>(Arrays.asList("..",".",""));
	    String str[] = path.split("/");
	    for (String dir : str) {
	        if (dir.equals("..") && !stack.isEmpty()) stack.pop();
	        else if (!skip.contains(dir)) stack.push(dir);
	    }
	    String res = "";
	    for (String dir : stack) res = "/" + dir + res;
	    return res.isEmpty() ? "/" : res;
	}
	
	public static void main(String[] args){
		String path = "/a/./b/../../c/";
		String r = simplifyPath(path);
		System.out.println(r);
	}
	
}