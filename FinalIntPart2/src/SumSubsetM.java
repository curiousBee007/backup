import java.util.ArrayList;
/*Assumptions for this algorithm value is 
 * that it is a non increasing order in array 
 * the first element value is less than value of m 
 * total sum value should be greater than m
 * 
 * 
 * 
 * */



public class SumSubsetM {
	
	int a[] ;
	int targetVal;
	int x[];
	
	
	public SumSubsetM(int a[],int targetVal,int x[] ){
		this.a = a;
		this.targetVal = targetVal;
		this.x = x;
		
	}
	
	
	public void printingX(){
		
		int lengthVal = x.length;
		for(int i = 0; i < lengthVal;i++){
			if(x[i] == 1){
			System.out.print(" "+a[i]);
			}
		}
		
		System.out.println();
		
	}
	
	public void subsetSum(int sum,int k, int r ){
		
		//arr.add(k);
		x[k] = 1;
		
		if(sum + a[k] == targetVal)
		{   
			printingX();
		}
		
		else if ((sum + a[k] + a[k+1]) <= targetVal)
		{
			
			subsetSum(sum + a[k],k + 1, r - a[k]);
		}
	    
		x[k] = 0;
		
		if (((sum + r - a[k]) >= targetVal) && (sum + a[k+1] <= targetVal)){
			
			subsetSum(sum ,k + 1, r - a[k]);
		}
			
	}
	
	
	public static void main(String[] args){
		
		
		
		int a1[] = {1,2,3,4,5};
		int m1 = 8;
		int len1 = a1.length;
		int x[] = new int[len1];
		SumSubsetM obj = new SumSubsetM(a1,m1,x);
		//ArrayList<Integer> arr = new ArrayList<Integer>();
		int sum = 0;
		int k = 0;
		int r = 0;
		for(int i = 0 ; i < len1; i++){
			r = r + a1[i];
			}
		
		//r is the length of whole val
		System.out.println("Target value required " +m1);
		System.out.println("Sets whose sum is equal to target value ");
		
		obj.subsetSum(sum, k, r);
	}
	
	
}
