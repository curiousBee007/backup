

public class ShellSort {

    // This class should not be instantiated.
    
    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static int[] sort(int a[]) {
        int N = a.length;

        // 3x+1 increment sequence:  1, 4, 13, 40, 121, 364, 1093, ... 
        int h = 1;
        
        while (h < N/3) h = 3*h + 1; 

        while (h >= 1) {
            // h-sort the array
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h ;j -= h) {
                	if(a[j] < a[j-h]){
                		int temp = a[j];
                		a[j] = a[j-h];
                		a[j-h] = temp;
                	}
                	
                   
                }
            }
            
            h /= 3;
        }
       return a;
    }
    
    
    public void printArray(int a[]){
		
		for(int i = 0; i < a.length;i++){
			System.out.print( " " + a[i]);
		}
		System.out.println();
	}
    
    public static void main(String[] args){
    	
    	
    	ShellSort obj = new ShellSort();
    	System.out.println("Array  before  sorting");
    	
    	int a[] = {82,7,3,4,6,9,14,56,12,45,23,45,68,72,26, 14, 32, 72};
    	obj.printArray(a);
    	
    	a = sort(a);
    	
    	System.out.println("Array after sorting");
    	obj.printArray(a);
    	
    }
    
}