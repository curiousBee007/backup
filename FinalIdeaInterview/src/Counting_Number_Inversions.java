
public class Counting_Number_Inversions {

	public int mergeFinal(int arr[], int array_size){
		int temp[] = new int[array_size];
		return mergeSort(arr,temp,0 ,array_size - 1);
		
	}
	
	int mergeSort(int arr[],int temp[], int left, int right){
		
		int mid, inv_count = 0;
		
		if(right > left){
			
			mid = (right + left) /2;
			inv_count =  mergeSort(arr,temp,left,mid);
			inv_count +=  mergeSort(arr,temp,mid + 1,right);
			inv_count +=  merge(arr,temp,left ,mid + 1,right);
			}
		
		return inv_count;
	}
	
	
	int merge(int arr[] , int temp[], int left, int mid, int right){
		
		int i , j ,k;
		int inv_count = 0;
		i = left;
		j = mid;
		k = left;
		
		
		/*How to get number of inversions in merge()?
				In merge process, let i is used for indexing left sub-array and j for right sub-array.
				 At any step in merge(), if a[i] is greater than a[j], then there are (mid – i) inversions. 
				 because left and right subarrays are sorted, so all the remaining elements in 
				left-subarray (a[i+1], a[i+2] … a[mid]) will be greater than a[j]*/
		
		
		
		
		while((i <= mid - 1 ) && (j <= right)){

			if(arr[i] <= arr[j]){
				temp[k++] = arr[i++];
				}
			else{
				temp[k++] = arr[j++];
				inv_count = inv_count + (mid - i);
			}
		}
		while(i <= mid -1)
			temp[k++] = arr[i++];
		
		while( j <= right)
			temp[k++] = arr[j++];
		
		for(i = left; i <= right;i++)
			arr[i] = temp[i];
		
		return inv_count;
		
	}
	

	public static void main(String[] args){
		Counting_Number_Inversions obj = new Counting_Number_Inversions();
		 //int arr[] = {1, 20, 6, 4, 5};
		 int arr[] = {4, 2, 3, 5, 1};
		 int noInversions = obj.mergeFinal(arr, 5);
		 System.out.println("Number of inversions are "+noInversions);
	
}
}
