
public class SearchRange {
	
	public int[] searchRange(int[] nums, int target) {
        int[] answerArray = {-1,-1};
        
        String s = "Anu";
        System.out.println(s.startsWith("A", 0));
        
        if(nums.length == 0)
          return answerArray;
        
        int start = 0;
        int end = nums.length-1;
        int startIndex =-1;
        int endIndex = -1;
        int foundIndex = -1;
        
        while(start <= end){
            int mid = (start) +(end - start)/2;
            if(nums[mid] == target)
              {foundIndex = mid;
              break;}
              
            else{
                if(target > nums[mid])
                {
                    start = mid +1;
                }
                else{
                    end = mid -1;
                }
            }      
         }
         if(foundIndex == -1)
           { return answerArray;
           }
            
            else
           {
               int actualIndex = foundIndex;
               while(foundIndex >0 && nums[foundIndex] == target){
                     foundIndex = foundIndex -1;
               }
               if(foundIndex == 0 && nums[0] == target)
                 {
                     startIndex = 0;
                 }
               else{
                   startIndex = foundIndex +1;
               }
               
               while(actualIndex < nums.length && nums[actualIndex] == target){
                     actualIndex = actualIndex + 1;
               }
               if(actualIndex == nums.length -1  && nums[nums.length -1] == target)
                 {
                     endIndex =  nums.length -1;
                 }
               else{
                    endIndex = actualIndex -1;
               }
               
           }
            
                
        answerArray[0] = startIndex;
        answerArray[1] = endIndex;
        return answerArray;
        
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = {1,3};
		int target = 1;
		SearchRange obj = new SearchRange();
		int n[] = obj.searchRange(a, target);
		System.out.println(n[0]);
		System.out.println(n[1]);

	}

}
