
public class Max_val_Q1 {
	
	/*In a given array A[] find the maximum value of (A[i] – i) - (A[j] – j) where i is not equal to j.*/
	
	//O(n * n) value
	public static int findMaxValue(int a[]){
		
		int lengthVal = a.length;
		for(int i = 0 ; i < lengthVal;i++){
			
			a[i] = a[i] - i;
		}
		
		int maxVal = a[0] - a[1];
		int sum =0;
		
		for(int i = 0; i < lengthVal;i++){
			
			for(int j = i+1; j < lengthVal;j++){
				
				int diff = a[i] - a[j];
				
				if(diff > maxVal){
					maxVal = diff;
					
				}
			}
		}
		
		
		return maxVal;
	}

	
	//O(n) time
	public static int findMaxValue1(int a[]){
		int lengthVal = a.length;
		if(lengthVal < 2){
			return 0;}
		
		int min_val = Integer.MAX_VALUE, max_val = Integer.MIN_VALUE;
	    for (int i=0; i< lengthVal; i++)
	    {
	        if ((a[i]-i) > max_val)
	            max_val = a[i] - i;
	        if ((a[i]-i) < min_val)
	            min_val = a[i]-i;
	    }
	 
	    return (max_val - min_val);
	}
	
	
	public static void main(String[] args){
		
		int a[] = {9 ,15, 4, 12, 13};
		int x = findMaxValue1(a);
		System.out.println(x);
		
	}
}
