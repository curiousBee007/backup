import java.util.LinkedList;
import java.util.Queue;

public class FindProfession {
	String findProfession(int level, int pos) {

	    Queue<String> queue = new LinkedList<String>();
	    queue.add("Engineer");
	    //int level = 1;
	    if(level == 1 && pos == 1)
	        return "Engineer";
	    int count = 1;
	    while(count <= level){
	        int size = queue.size();
	        while(size != 0){
	            String str = queue.remove();
	            if(str.equals("Engineer")){
	                queue.add("Engineer");
	                queue.add("Doctor");
	                }
	            else{
	             queue.add("Doctor");
	             queue.add("Engineer");
	            }
	        size = size - 1;
	        }
	        count = count + 1;
	    }
	    int initialPos = 1;
	    String finalValue = "Engineer";
	    while(initialPos <= pos){
	        finalValue = queue.remove();
	        initialPos = initialPos + 1;
	    }
	    
	    return finalValue;
	    
	}
	public static void main(String[] args){
		FindProfession obj = new FindProfession();
		String finalValue = obj.findProfession(3, 3);
		System.out.println(finalValue);
	}

}
