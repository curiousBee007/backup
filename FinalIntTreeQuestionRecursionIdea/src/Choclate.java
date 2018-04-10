
public class Choclate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] squares = new int[n];
        for(int squares_i=0; squares_i < n; squares_i++){
            squares[squares_i] = in.nextInt();
        }
        int d = in.nextInt();
        int m = in.nextInt();*/
		
		
		/*int n = 1;
		int[] squares = {4};
		int m = 1;
		int d = 4;*/
		
		int n = 5;
		int[] squares = {1,2,1,3,2};
		int m = 2;
		int d = 3;
        
        if (n<m){
            System.out.println(0);
            return;
        } 
        
        int sum = 0;
        int count = 0;
        int prevFirst = 0;
        int indexFirst = 0;
        for(int i = 0 ; i < m ;i++){
            sum = sum + squares[i];
        }
        //indexFirst = indexFirst +1;
        if(sum == d)
            count = count + 1;
        
        for(int j = m ; j < n;j++){
            prevFirst = indexFirst;
            indexFirst = indexFirst +1;
            sum = (sum + squares[j]) - squares[prevFirst];
            if(sum == d)
               count = count + 1; 
            }
        
        System.out.println(count);
        //return count;
        

	}

}
