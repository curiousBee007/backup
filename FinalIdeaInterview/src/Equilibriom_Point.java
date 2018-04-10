import java.util.Scanner;

public class Equilibriom_Point {

	/*Equilibrium index of an array is an index such that the sum of elements at lower indexes is equal to the sum of elements at higher indexes. For example, in an arrya A:

	A[0] = -7, A[1] = 1, A[2] = 5, A[3] = 2, A[4] = -4, A[5] = 3, A[6]=0

			3 is an equilibrium index, because:
	A[0] + A[1] + A[2] = A[4] + A[5] + A[6]

			6 is also an equilibrium index, because sum of zero elements is zero, i.e., A[0] + A[1] + A[2] + A[3] + A[4] + A[5]=0*/
	
	//O(n) time
		public static int equilibriumPoint(int a[]){
			int lengthVal = a.length;
			int index = -1;
			
			if(lengthVal < 2){
				return a[0];
				}
			int remVal = 0;
			for(int i = 0; i < lengthVal;i++){
				remVal = remVal + a[i];
			}
			
			int firstVal = 0;
			for(int j = 0 ; j < lengthVal;j++){
				
				if( firstVal == (remVal - a[j])){
					int index1 = j + 1;
					return index1;
				}
				
				firstVal = firstVal + a[j];
				remVal = remVal - a[j];
			}
			return index;
		}
		
		public static void main(String[] args){
			int a[] = {1,3,5,2,2};
			int index = equilibriumPoint(a);
			System.out.println(index);
			
			//Scanner sc = new Scanner(System.in);
			//int t = sc.nextInt();
			
			
		}
		
}
