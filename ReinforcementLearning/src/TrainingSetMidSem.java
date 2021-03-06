import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class TrainingSetMidSem {
	
	ArrayList<Integer> arrList;
	HashMap<String, ArrayList<Integer>> hashMap = new HashMap<String, ArrayList<Integer>>();
	ArrayList<String> arrStringList = new ArrayList<String>();
	Map<Integer, ArrayList<String>> finalHashMap = new HashMap<Integer, ArrayList<String>>();
	
	
	
	public static int randomInteger(int min, int max) {

	    Random rand = new Random();

	    // nextInt excludes the top value so we have to add 1 to include the top value
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	
	public void generatingEpisode(){
		
		int sum = 0;
		int length = 0;
		int asciiVal = 68;
		char[] charArr;
		String str;
		int count =0;
		
		
		for(int i =0; i < 1000;i++){
			 sum = 0;
			 length = 0;
			 asciiVal = 68;
			 arrList = new ArrayList<Integer>();
			 charArr = new char[5];
			 charArr[0] = 'D';
			 str = "";
			 
			while ( (sum > -3 && sum < 3) &&  length < 4){
				
				int num = randomInteger(0,1);
				
				if(num == 0){
					sum = sum -1;
					length = length +1;
					asciiVal = asciiVal -1;
					//System.out.println("Ascii value " +asciiVal);
					char b = (char) (asciiVal);
					//System.out.println( " " +b);
					charArr[length] = b;
					
				}
				
				if(num == 1){
				 sum = sum +1;	
				 length = length +1;
				 asciiVal = asciiVal +1;
				 
				 char a = (char) (asciiVal);
		
				 charArr[length] = a;
				}
				
				
			}
			
			if(length == 4){
			str =  "D"+ charArr[1] + charArr[2] + charArr[3] +charArr[4] ;
			//System.out.println(str);
			count = count +1;
			if( !arrStringList.contains(str)){
				arrStringList.add(str);
			}
			
			}	
		}
		
		//System.out.println("Total count value " +count);
		//System.out.println("Distinct count value " +arrStringList.size());
		System.out.println("Distinct count value");
		for(int i = 0; i < arrStringList.size();i++){
			
			System.out.print( "   "+arrStringList.get(i));
		}
		System.out.println();
		System.out.println("Distinct count value---------------");
		
	}
	
	
	//Correct Hash Map
	public void creatingTrainingSet(){
		
		ArrayList<String> arrString;
		
		for(int i = 0; i < 100;i++){
			
			arrString = new ArrayList<String>();
			for(int j = 0; j < 10;j++){
				
				int index = randomInteger(0,11);
				
				System.out.print(" " +index);
				
				System.out.print(" " +arrStringList.get(index));
				
				
				arrString.add(arrStringList.get(index));
				}
			System.out.println();
			System.out.println();
			System.out.println("Key value " +i);
			System.out.println("value " +" "+arrStringList.get(0) +"  "+arrStringList.get(1) +"  "+arrStringList.get(2)+
					"  "+arrStringList.get(3) +"  "+ arrStringList.get(4) +"  "+arrStringList.get(5) + "   "+arrStringList.get(6)
					+"  "+arrStringList.get(7) +"  "+arrStringList.get(8) +"  "+arrStringList.get(9));
			
			System.out.println();
			finalHashMap.put(i, arrString);
		}
		
	}
	
	
	
	
	
//Printing 	finalHashMap
	
	public void printingFinalHashMap(){
		
		 Set<Integer> setOfKeys = finalHashMap.keySet();
		 Iterator<Integer> iterator = setOfKeys.iterator();
		 
		 while (iterator.hasNext()) {
			 /**
			  * next() method returns the next key from Iterator instance.
			  * return type of next() method is Object so we need to do DownCasting to String
			  */
			 int key = (Integer) iterator.next();
			  
			 /**
			  * once we know the 'key', we can get the value from the HashMap
			  * by calling get() method
			  */
			  ArrayList<String> value = finalHashMap.get(key);
			  
			 System.out.println("Key: "+ key);
			 
			 for(int i = 0; i < value.size();i++){
				 
				 System.out.print("  "+value.get(i));
				 
			 }
			 System.out.println();
			 
			  
			  }
		 
		
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args){
		
		TrainingSetMidSem obj = new TrainingSetMidSem();
		
		obj.generatingEpisode();
		
		obj.creatingTrainingSet();
		
		obj.printingFinalHashMap();
		
	}
	
	
}
