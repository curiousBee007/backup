import java.util.ArrayList;

public class Subset {

	public static ArrayList<ArrayList<Integer>> getSubset(ArrayList<Integer> set, int index){
		
		ArrayList<ArrayList<Integer>> allsubsets;
		
		if( set.size() == index){
			allsubsets = new ArrayList<ArrayList<Integer>>();
			allsubsets.add(new ArrayList<Integer>());
		}
			
		else{
			allsubsets = getSubset(set,index + 1);
			int item = set.get(index);
			ArrayList<ArrayList<Integer>> moreSubsets = new ArrayList<ArrayList<Integer>>();
			
			for( ArrayList<Integer> subset : allsubsets){
				
				ArrayList<Integer> newSubset = new ArrayList<Integer>();
			    newSubset.addAll(subset);
			    
				newSubset.add(item);
				moreSubsets.add(newSubset);
				
			}
			allsubsets.addAll(moreSubsets);
			
		}
		return allsubsets;
		
		
	}
	
	public static void main(String[] args){
		
		ArrayList<Integer> arrList = new ArrayList<Integer>();
		arrList.add(1);
		arrList.add(2);
		arrList.add(3);
		ArrayList<ArrayList<Integer>> returnSubset = getSubset(arrList,0);
		
		for(ArrayList<Integer> arrList1: returnSubset){
			
			for(Integer i :arrList1 ){
				System.out.print(" " +i);
			}
			System.out.println();
		}
		
		
	}
	
}
