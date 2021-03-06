import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopKFrequencyElement1 {
    
	public List<Integer> topKFrequent(int[] nums, int k){
		
	
    int maxFreq = 0;
    
    Map<Integer, Integer> hashmap = new HashMap<Integer,Integer>();
    
    for(int i = 0; i < nums.length;i++){
    	
        if (hashmap.containsKey(nums[i])){
        	
        	int x = hashmap.get(nums[i]);
        	x = x +1;
        	if(maxFreq < x){
        		maxFreq = x;
        	}
        	hashmap.put(nums[i], x);
        	}
        	
        else{
        	
        	if(maxFreq < 1){
        		maxFreq = 1;
        	}
        	hashmap.put(nums[i], 1);
        	
        	}
           
    }
    
 
    
    // ArrayList<Integer> arr=new ArrayList<Integer>(maxFreq);   
    List<ArrayList<Integer>> frequentList = new ArrayList<ArrayList<Integer>>(maxFreq);
    
    List<Integer> finalList = new ArrayList<Integer>();
    
    for (int i = 0; i <= maxFreq; i++){
       
    	ArrayList<Integer> tempList = new ArrayList<Integer>();
       
    	frequentList.add(tempList);
    }
    
  
    for (Map.Entry<Integer, Integer> entry : hashmap.entrySet()) {
    	Integer key = entry.getKey();
    	Integer value = entry.getValue();
    	
    	//System.out.println("Value of key is "+key);
    	//System.out.println("Value of values "+value);
  
      
    	if(frequentList.get(value).size() == 0){
    		//System.out.println("Inside first time created list ");
    	   ArrayList<Integer> tempList = new ArrayList<Integer>();
    	   tempList.add(key);
    	   
    	   frequentList.add(value, tempList);
    	   ArrayList<Integer> checkList = frequentList.get(value);
    	   for (Integer i : checkList){
    		   System.out.println(" Elements having frequency "+value+" is  " +i);
    	   }
    	   
       }
       else if(frequentList.get(value).size() > 0){
    	  // System.out.println("Inside when arrayList has already been created ");
    	   ArrayList<Integer> tempList1 =  frequentList.get(value);
    	   tempList1.add(key);
    	  
    	   frequentList.add(value, tempList1);
    	   ArrayList<Integer> checkList = frequentList.get(value);
    	   for (Integer i : checkList){
    		   System.out.println(" Elements having frequency "+value+" is  " +i);
    	   }
    	   
    	  
    	   
    	   }
    	
    	}
    
    
   int count = 0;
   for (int j = maxFreq +1; j >0 ; j--){
	   if(k <= 0)
		   break;
	   
	 
	    int size =frequentList.get(j).size();
	    //count = count +size;
    	//System.out.println("Size of list having frequency "+ j + "  is  " +frequentList.get(j).size());
    	
    	ArrayList<Integer> temp = frequentList.get(j);
    	for (Integer i : temp){
    		//System.out.println(i);
    		if(k>0){
    		finalList.add(i);
    		count = count +1;
    		k = k -count;
    		 }
    	}
    	
	    
     	
    }
    
 // System.out.println("Value of count is "+count +"and value of k is" +k);
  return finalList;
}

public static void main(String[] args){
		
		TopKFrequencyElement1 obj = new TopKFrequencyElement1();
		int nums[] = {1,1,1,2,2,3};
		int k = 2;
		List<Integer> list = obj.topKFrequent(nums, k);
		for(int i = 0; i <list.size(); i++){
			System.out.println(list.get(i));
			
		}
		
		
	}
	
	}
