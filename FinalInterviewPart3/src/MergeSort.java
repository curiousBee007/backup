
public class Merge_Sort {

	private static void merge(int[] a, int[] aux, int lo, int mid, int hi) {
        // precondition: a[lo .. mid] and a[mid+1 .. hi] are sorted subarrays
        // copy to aux[]
		
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k]; 
        }

        // merge back to a[]
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            
        	// //Case when first subarray has run out and second array is still left
        	if (i > mid){
            	a[k] = aux[j++];
            	}
            
            //Case when second subarray has run out and first array is still left
            else if (j > hi){
            	
            	a[k] = aux[i++];
            	}
            //Case when element of second sub sorted array is smaller than first array
            else if (aux[j] < aux[i]){
            	a[k] = aux[j++];
            	}
            
          //Case when element of first sub sorted array is smaller than second sub sorted  array
            else                           
            	{
            	a[k] = aux[i++];
            	}
        }

       
    }

	
	// mergesort a[lo..hi] using auxiliary array aux[lo..hi]
    private static void sort(int [] a,int [] aux, int lo, int hi) {
        if (hi <= lo) {
        	//System.out.println(" Low Value is  "+lo +"hi value is  "+hi);
        	return;}
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }
	
   
    public void printArray(int a[]){
		
		for(int i = 0; i < a.length;i++){
			System.out.print( " " + a[i]);
		}
		System.out.println();
	}
    
    
    
    public static void main(String[] args){
		  
		  int a[] = {5,4,3,7,18,9};
		  
		  int aux[] = new int[6];
		  
		  Merge_Sort obj = new Merge_Sort();
		  
		  System.out.println("Array  before sorting");
		  obj.printArray(a);
		  
		 sort(a,aux,0,5);
		  
		  System.out.println("Array after  sorting");
		  obj.printArray(a);
	  }
    
	
	
}
