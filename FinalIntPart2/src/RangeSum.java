
public class RangeSum {
	public static int[] valArray;
    public boolean isInvalid = false;
    public int lenArr;

    public RangeSum(int[] nums) {
        
        if (nums.length < 2)
         {isInvalid  = false;
         
         }
        
        else{
        lenArr = nums.length;
        valArray = new int[lenArr];
        valArray[0] = nums[0];
        
        for(int i = 1; i < lenArr;i++){
            valArray[i] = valArray[i -1] + nums[i]; 
            System.out.println("Value at array at " + i + "  index is  " +valArray[i] );
        }
        }
        
    }

    public int sumRange(int i, int j) {
        
        if(isInvalid || i > j || i < 0 || j > lenArr )
        {
            return -1;
        }
        int lowerLimit = i -1;
        if(i - 1 < 0){
            return valArray[j];
        }
        
        return valArray[j] - valArray[lowerLimit];
    }
    
    
    
    public static void main(String[] args){
    	
    	int N[] = {-2,0,3,-5,2,-1};
    	
    	RangeSum numArray = new RangeSum(N);
    	
    	
    	
    }
}

