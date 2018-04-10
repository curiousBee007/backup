
import java.util.*;
public class LFUCache {

     int capacity;
    
     int current_capacity = 0;
     Map<Integer, ArrayList<Integer>> hashmap = new HashMap<Integer, ArrayList<Integer>>();
	 public LFUCache(int capacity){
		 this.capacity = capacity;
	 }

	 public int get(int key) {
		 
		 int value = -1;
	        
		 if(!hashmap.isEmpty()){
			 
			 if(hashmap.containsKey(key)){
				 ArrayList<Integer> arrList = hashmap.get(key);
				 value = arrList.get(0);
				 int used = arrList.get(1);
				 
				 used = used + 1;
				 arrList.set(1, used);
				 hashmap.put(key, arrList);
				 
			 }
		 }
	   
		 return value;
	 }
	 
	 public void set(int key, int value) {
		 
		 if(current_capacity < this.capacity){
			 
			 if (hashmap.containsKey(key)){
				 
				 ArrayList<Integer> arrList = new ArrayList<Integer>();
				 //timestamp = timestamp + 1;
				 arrList.add(0,value);
				 arrList.add(1,0);
				 hashmap.put(key, arrList); 
			 
			 }
			 
			 
			 else{
			 ArrayList<Integer> arrList = new ArrayList<Integer>();
			 //timestamp = timestamp + 1;
			 arrList.add(0,value);
			 arrList.add(1,0);
			 hashmap.put(key, arrList);
			 current_capacity = current_capacity + 1;
			 }
		 
		 
		 }
		 
		 else{
			 int usedTimeStamp = Integer.MAX_VALUE;
			 int keyRemoved = -1;
			 Iterator it = hashmap.entrySet().iterator();
			  while (it.hasNext()) {
			        Map.Entry pair = (Map.Entry)it.next();
			        ArrayList<Integer> arrList1 = (ArrayList<Integer>) pair.getValue(); 
			        int usedVal = arrList1.get(1);
			        if(usedVal < usedTimeStamp){
			        	keyRemoved = (int) pair.getKey();
			        	usedTimeStamp = usedVal;
			        }
			        	
			        
			    }
			 
			 hashmap.remove(keyRemoved);
			 ArrayList<Integer> arrList = new ArrayList<Integer>();
			 //timestamp = timestamp + 1;
			 arrList.add(0,value);
			 arrList.add(1,0);
			 hashmap.put(key, arrList);
			  
			  }
	        
	    }
	 
	 
	 public static void main(String[] args){
		 
		 LFUCache obj = new LFUCache(2);
		 
		 obj.set(3,1);
		 obj.set(2,1);
		 obj.set(2,2);
		 obj.set(4,4);
		 
		 int val = obj.get(2);
		 System.out.println(val);
		 
		 /*int val1 = obj.get(1);
		 System.out.println(val1);
		 
		 int val2 = obj.get(2);
		 System.out.println(val2);
		 
		 obj.set(3,3);
		 obj.set(4,4);
		 
		
		 int val3 =  obj.get(3);
		 System.out.println(val3);
		 
		 int val4 = obj.get(2);
		 System.out.println(val4);
		 
		 int val5 = obj.get(1);
		 System.out.println(val5);
		 
		 int val6 = obj.get(4);
		 System.out.println(val6);*/
		 
		 }
	 
	 

}


