
public class NextLargerElement {

	
	public static int[] nextLargerElement(int a[]){
		
		//1 3 2 4 
		//1 5 3
		// 5  5 -1
		//Output
		//3 4 4 -1
		
		int answerArray[] = new int[a.length];
		answerArray[a.length -1] = -1;
		int maxLastElement = a[a.length -1];
		
		for(int j = a.length -2; j >= 0 ; j--){
			
			if(a[j+1] > a[j]){
				maxLastElement = a[j+1];
			}
			
			if (maxLastElement >= a[j]){
			answerArray[j] = maxLastElement;}
			
			if( maxLastElement < a[j]){
				answerArray[j] = a[j];
			}
			
		}
		for(int x = 0 ; x < a.length; x++){
			System.out.print(" "+ answerArray[x]);
		}

		return answerArray;
		
	}
	
	
	
	public static void main(String[] args){
		
		//int a[] = {1,5,3};
		int a[] = {1,3,2,4};
		nextLargerElement(a);
		
	}
}
