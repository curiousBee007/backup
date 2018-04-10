
public class MergingArrays {

	 public static String mergeArrays(int[] myArray , int[] alicesArray) {
	        // write the body of your function here
	        int x = myArray.length;
	        int y = alicesArray.length;
	        int totalLength = x + y;
	        int mergedArray[] = new int[totalLength];
	        
	        int index1 = 0;
	        int index2 = 0;
	        int i = 0;
	        
	        while(index1 < x && index2 < y ){
	                
	                if(myArray[index1] < alicesArray[index2]){
	                
	                    mergedArray[i] = myArray[index1];
	                    index1++;
	                    i ++;
	                }
	                else{
	                
	                     mergedArray[i] = alicesArray[index2];
	                     index2 ++ ;
	                    i++;
	                }
	                
	             }
	        
	        /*System.out.println(" Index 1: "+index1);
	        System.out.println(" Index 2: "+index2);
	        System.out.println(" i value : "+i);
	        System.out.println(" totalLength value : "+totalLength);*/
	           
	        if (i < totalLength ){
	        
	            if(index1 >= x){
	        
	                for(int k = index2; k < y ; k++){
	                
	                    mergedArray[i] = alicesArray[k];
	                        i++;
	                }
	        
	            }
	            
	            else
	            {
	                 for(int k = index1; k < x ; k++){
	                
	                    mergedArray[i] = myArray[k];
	                        i++;
	                }
	                
	            } 
	        }   
	          
	        
	        StringBuilder sb = new StringBuilder();
	        sb.append("[");
	        for(int j = 0 ; j < totalLength;j++){
	        	
	        	sb.append(String.valueOf(mergedArray[j]) + ",");
	        	
	        }
	        
	        sb.deleteCharAt(sb.length()-1);
	        sb.append("]");
	        //System.out.println( " " +sb);
	        return(sb.toString());
	        
	        
	    }
	    public static void main(String[] args) {
	        // run your function through some test cases here
	        // remember: debugging is half the battle!
	        //String testInput = "test input";
	        //System.out.println(myFunction(testInput));
	    	
	    	  int[] myArray     = new int[]{3, 4, 6, 10, 11, 15};
	    	  int[] alicesArray = new int[]{1, 5, 8, 12, 14, 19};
	    	  

	    	  System.out.println(mergeArrays(myArray, alicesArray));
	    	  // prints [1, 3, 4, 5, 6, 8, 10, 11, 12, 14, 15, 19]
	    	// prints [1, 3, 4, 5, 6, 8, 10, 11, 12, 14, 15, 19]
	    }
	
	
}
