import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SpiralMatrix {
	
	
public List<Integer> spiralOrder(int[][] matrix) {
        
        List<Integer> arrList = new ArrayList<Integer>();
        
        int noOfRows = matrix.length;
        if(noOfRows == 0){
            return arrList;
        }
        
        int noOfColumns = matrix[0].length;
        
        int count = noOfRows * noOfColumns;
       
        
        int topRow = 0;
        int bottomRow = noOfRows-1;
        int leftmostColumn = 0;
        int rightmostColumn = noOfColumns -1;
        
        
        
        
        if(noOfRows == 1 && noOfColumns == 1){
            arrList.add(matrix[0][0]);
            return arrList;
        }
        
        
        if(noOfRows == 1){
            for(int i = 0; i < noOfColumns;i++){
                arrList.add(matrix[0][i]);
            }
           return arrList; 
        }
        
        
         if(noOfColumns == 1){
            for(int i = 0; i < noOfRows;i++){
                arrList.add(matrix[i][0]);
            }
           return arrList; 
        }
        int added = 0;
        
        
        while(leftmostColumn <= rightmostColumn && topRow<= bottomRow && added <= count){
            
            
        	if(added != count){
            for(int i = leftmostColumn; i <= rightmostColumn;i++){
                arrList.add(matrix[topRow][i]);
                added = added+1;
               /* if(added == count)
                	break;*/
                 }
            topRow = topRow +1;
        	}
            
            
        	if(added != count){	
          for(int j = topRow; j <= bottomRow;j++){
                arrList.add(matrix[j][rightmostColumn]);
                added = added+1;
                /*if(added == count)
                	break;*/
              }
                rightmostColumn = rightmostColumn -1;
        	} 
        	
        	
        	if(added != count){	
           for(int k = rightmostColumn; k >= leftmostColumn; k--){
                arrList.add(matrix[bottomRow][k]);
                added = added+1;
                /*if(added == count)
                	break;*/
               }
                bottomRow = bottomRow -1;
        	}
        	
        	
        	if(added != count){	
            for(int l = bottomRow; l >= topRow ;l--){
                 arrList.add(matrix[l][leftmostColumn]);
                 added = added+1;
                 /*if(added == count)
                 	break;*/
                }
            leftmostColumn = leftmostColumn +1;
        	}
            
        }
        
        return arrList;
     }



  public int[][] generateMatrix(int n) {
	  
	  //Queue<Integer> queue = new Queue<Integer>();
	  
	  int matrix[][] = new int[n][n];
	  int count = n * n +1;
      int topRow = 0;
      int bottomRow = n-1;
      int leftmostColumn = 0;
      int rightmostColumn = n -1;
      int added = 0;
	  
	  
	  while(leftmostColumn <= rightmostColumn && topRow<= bottomRow && added <= count){
          
          
      	if(added != count){
          for(int i = leftmostColumn; i <= rightmostColumn;i++){
        	  matrix[topRow][i] = added +1;
            // #arrList.add(matrix[topRow][i]);
              added = added+1;
             /* if(added == count)
              	break;*/
               }
          topRow = topRow +1;
      	}
          
          
      	if(added != count){	
        for(int j = topRow; j <= bottomRow;j++){
              //arrList.add(matrix[j][rightmostColumn]);
        	 matrix[j][rightmostColumn] = added+1;
              added = added+1;
              /*if(added == count)
              	break;*/
            }
              rightmostColumn = rightmostColumn -1;
      	} 
      	
      	
      	if(added != count){	
         for(int k = rightmostColumn; k >= leftmostColumn; k--){
        	  matrix[bottomRow][k] = added +1;
              //arrList.add(matrix[bottomRow][k]);
              added = added+1;
              /*if(added == count)
              	break;*/
             }
              bottomRow = bottomRow -1;
      	}
      	
      	
      	if(added != count){	
          for(int l = bottomRow; l >= topRow ;l--){
              // arrList.add(matrix[l][leftmostColumn]);
        	  matrix[l][leftmostColumn] = added +1;
               added = added+1;
               /*if(added == count)
               	break;*/
              }
          leftmostColumn = leftmostColumn +1;
      	}
          
      }
	  
	  return matrix;
    
}


     public static void main(String[] args){
    	 
    	 SpiralMatrix obj = new SpiralMatrix();
    	/* int[][] matrix = {{1,2,3,4},{5,6,7,8},{9,10,11,12}};
    	 ArrayList<Integer> arrList = (ArrayList<Integer>) obj.spiralOrder(matrix);
    	 for(Integer e : arrList){
    		 System.out.print(" "+e);
    	 }*/
    	 int N = 3;
    	 int matrix[][] = obj.generateMatrix(N);
    	
    	 for(int i = 0 ; i < N;i++){
    		 for(int j = 0 ; j < N;j++){
    			 System.out.print(" "+matrix[i][j]);
    		 }
    		 System.out.println();
    	 }
    	 
     }

}
