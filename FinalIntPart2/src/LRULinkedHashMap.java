import java.util.Iterator;
import java.util.LinkedHashMap;
//LinkedHashMap : whatever entries we are using will go to the end of the list
public class LRULinkedHashMap {

	private int capacity;
    private LinkedHashMap<Integer,Integer> map;
   

    public LRULinkedHashMap(int capacity) {
        this.capacity = capacity;
        this.map = new LinkedHashMap<>();
    }

    public int get(int key) {
        Integer value = this.map.get(key);
        if (value == null) {
            value = -1;
        } 
        
        else {
            this.set(key, value);
        }
        return value;
    }

    public void set(int key, int value) {
        if (this.map.containsKey(key)) {
            
        	//Remove function remove mapping for the key
        	this.map.remove(key);
            
        } 
        
        else if (this.map.size() == this.capacity) {
            Iterator<Integer> it = this.map.keySet().iterator();
            it.next();
            it.remove();
        }
        map.put(key, value);
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
