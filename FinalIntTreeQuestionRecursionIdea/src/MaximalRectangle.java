
public class MaximalRectangle {
	
	 public static int maximalRectangle(char[][] matrix) {
	        if(matrix == null || matrix.length == 0)
	          return 0;
	          
	          if(matrix.length == 1 && matrix[0].length ==1)
	          {
	              if(matrix[0][0] == '1')
	                return 1;
	                
	              else
	                 return 0;
	          }
	        
	        
	          
	          int maxA = 0;
	          int m = matrix.length;
	          int n = matrix[0].length;
	          
	          int height[] = new int[n];
	          int left[] = new int[n];
	          int right[] = new int[n];
	          int cur_left;
	          int cur_right;
	          
	          for(int k = 0 ; k < n ;k++){
	              right[k] = n;
	          }
	          
	        for(int i=0; i<m; i++) {
	          cur_left=0; cur_right=n; 
	        
	        for(int j=0; j<n; j++) { // compute height (can do this from either side)
	            if(matrix[i][j]=='1') height[j]++; 
	            else height[j]=0;
	        }
	        
	        for(int j=0; j<n; j++) { // compute left (from left to right)
	            if(matrix[i][j]=='1') left[j]=Math.max(left[j],cur_left);
	            else {left[j]=0; cur_left=j+1;}
	        }
	        // compute right (from right to left)
	        for(int j=n-1; j>=0; j--) {
	            if(matrix[i][j]=='1') right[j]=Math.min(right[j],cur_right);
	            else {
	            	right[j]=n; cur_right=j;
	            	}    
	        }
	        // compute the area of rectangle (can do this from either side)
	        for(int j=0; j<n; j++)
	        {   
	        	//System.out.println("left" +j+" value is " +left[j]+ "  and right" + j +"value is "+right[j]);
	        	int breadth = (right[j]-left[j]);
	        	int height1 = height[j];
	        	//System.out.println("breadth value is " +breadth + "  and height value is "+height1);
	            maxA = Math.max(maxA,(breadth*height1));
	            //System.out.println();
	        }
	    }
	    return maxA;
	}

	public static void main(String[] args) {
		
		char[][] matrix = {{'0','1'}};
		int m = maximalRectangle(matrix);
	     
		System.out.println(" maximum area is "+m);
		

	}

}
