
/*In computer science, the maximum subarray problem is the task of finding the contiguous subarray
within a one-dimensional array of numbers which has the largest sum. For example, for the sequence
of values −2, 1, −3, 4, −1, 2, 1, −5, 4; the contiguous subarray with the largest sum is 4, −1, 2, 1,
with sum 6.*/

//Finding largest contiguous subarray
//O(n) algorithm
public class Kadane_algorithm {
	
	
	public static int sumArray(int a[]){
		
		int max_so_far = a[0];
		int max_ending_here = a[0];
		
		for(int i = 1 ; i < a.length;i++){

			max_ending_here = Math.max(a[i], max_ending_here + a[i]);
			max_so_far = Math.max(max_ending_here,max_so_far);

		}
		
		
		return max_so_far;
	}

	public static void main(String[] args){
		
		Kadane_algorithm obj = new Kadane_algorithm();
		int a[] = {-2,-3,4,-1,-2,6,5,-3};
		int sum = obj.sumArray(a);
		System.out.println(sum);
		
	}
}
