
public class Buildings_Receive_Sunlight {

	//Finding buildings which will receive sunlight . First building will always receive sunlight .
	//Buildings which are higher than the highest val seen so far will receive sunlight
	public static int findingSunlight(int a[]){
		
		int lengthVal = a.length;
		if(lengthVal == 1){
			return 1;
		}
		
		int firstElem = a[0];
		int count = 1;
		int minVal = firstElem;
		
		for(int i = 1 ; i < lengthVal;i++){
			
			if(a[i] < minVal )
				continue;
			
			else{
				System.out.println(a[i]+" index = "+i);
				minVal = a[i];
				count = count+1;
			}
			
		}
		
		return count;
	}
	
	
	public static void main(String[] args){
		
		
		//int a[] = {6,2,8 ,4 ,11, 13};
		//int a[] = {2 ,5, 1, 8, 3};
		int a[] = {3, 4, 1, 0, 6, 2, 3};
		int count1 = findingSunlight(a);
		System.out.println(count1);
	}
	
}
