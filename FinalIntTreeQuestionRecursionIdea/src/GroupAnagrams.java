import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class GroupAnagrams {
	
	 public String generatingCodeAnagrams(String s){
	        int a[] = new int[26];
	        for(int i = 0 ; i < s.length();i++){
	            char x = s.charAt(i);
	            int n = x-97;
	            a[n] = a[n] +1;
	        }
	        StringBuilder sb = new StringBuilder();
	        for(int i = 0 ; i < 26;i++){
	        	if(a[i] >0){
	        	sb.append(a[i]);
	        	sb.append(i);
	        	sb.append('_');
	        
	        	}
	        }
	        
	        sb.setLength(sb.length() -1);
	        return sb.toString();
	    } 
	    
	    
	    public List<List<String>> groupAnagrams(String[] strs) {
	    	
	    	if(strs == null || strs.length == 0 )
	    		return null;
	    		
	    	if(strs.length == 1 && strs[0] == ""){
	    	  	List<List<String>> finalList = new ArrayList<List<String>>(); 
	    	    ArrayList<String> value  = new ArrayList<String>(); 
	    		value.add("");
	    		finalList.add(value);
	    		return finalList;
	    		
	    	
	    	}
	    	
	    	Map<String,ArrayList<String>> hashmap = new HashMap<String,ArrayList<String>>();
	    	for(int i = 0 ; i < strs.length;i++){
	    		
	    		String s1 = generatingCodeAnagrams(strs[i]);
	    		if(!hashmap.isEmpty()){
	    			if(hashmap.containsKey(s1)){
	    				ArrayList<String> temp = hashmap.get(s1);
	    				temp.add(strs[i]);
	    				hashmap.put(s1, temp);
	    			}
	    			else{
	    				ArrayList<String> temp1 = new ArrayList<String>();
	    				temp1.add(strs[i]);
	    				hashmap.put(s1, temp1);
	    			}
	    			
	    		}
	    		else{
	    			ArrayList<String> temp2 = new ArrayList<String>();
    				temp2.add(strs[i]);
    				hashmap.put(s1, temp2);
	    		}
	    	}
	    	Queue<Integer> queue = new LinkedList<Integer>();
	    	int [] A = {1,2,3};
	        for(Integer i : A){
	            queue.offer(i);
	           // queue.poll();
	           
	        }
	        
	    	
	    	List<List<String>> finalList = new ArrayList<List<String>>(); 
	    	//finalList = hashmap.values();
	    	for (ArrayList<String> value : hashmap.values()) {
	    		finalList.add(value);
	    	}

	     return finalList;
	    }

	public static void main(String[] args) {
		GroupAnagrams obj = new GroupAnagrams();
		String s = "tan";
		String s1 = obj.generatingCodeAnagrams(s);
		//System.out.println(s1);
		// TODO Auto-generated method stub
		String strs1[] = {""}; 
		//String strs[] =  {"eat", "tea", "tan", "ate", "nat", "bat"};
		List<List<String>> finalList = obj.groupAnagrams(strs1);
		for (List<String> value : finalList) {
    		System.out.println(value);
    	}

	}

}
