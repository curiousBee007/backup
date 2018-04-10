import java.util.HashMap;

//Find whether there are two numbers that sum to t
public class Two_Sum_Val {
	public static int[] twoSum(int[] numbers, int target) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		int[] result = new int[2];
	 
		for (int i = 0; i < numbers.length; i++) {
			
			if (map.containsKey(numbers[i])) {
				int index = map.get(numbers[i]);
				result[0] = index ;
				result[1] = i;
				break;
			} else {
				map.put(target - numbers[i], i);
			}
		}
	 
		return result;
	    }
	
	public int[] twoSumSorted(int[] numbers, int target) {
		if (numbers == null || numbers.length == 0)
			return null;
	 
		int i = 0;
		int j = numbers.length - 1;
	 
		while (i < j) {
			int x = numbers[i] + numbers[j];
			if (x < target) {
				++i;
			} else if (x > target) {
				j--;
			} else {
				return new int[] { i + 1, j + 1 };
			}
		}
	 
		return null;
	}
	public static void main(String[] args){
		
		//Two_Sum_Val obj = new Two_Sum_Val();
		int numbers[] = {2,7,11,15};
		int target = 9;
		int[] result = twoSum(numbers,target);
		System.out.print( " " +result[0] + "  "+result[1]);
		
	}
}
