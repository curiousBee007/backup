
public class IslandProblem {
	 public boolean checkVisited(int i ,int j, int row,int column,int visited[][]){
	        boolean right = false;
	        boolean left = false;
	        boolean top = false;
	        boolean bottom = false;
	        
	      if(i == 0)
	       top = true;
	       
	       if(i == row -1)
	       bottom = true;
	       
	       if(j == 0)
	       left = true;
	       
	       if(j == column -1)
	        right = true;
	        
	        if(top == true){
	            if(left == true){
	                if(visited[i+1][j] == 1 || visited[i][j+1] == 1){
	                   //visited[i][j] = 1; 
	                   return true;
	                }
	            }
	            else if(right == true){
	                if(visited[i][j-1] == 1 || visited[i+1][j] == 1){
	                   return true;
	                }
	            }
	          else{
	              if(visited[i][j+1] == 1 || visited[i][j-1] == 1|| visited[i+1][j] == 1  ){
	                   return true;
	              }
	          }
	        
	        
	    }
	    
	     else if(bottom == true){
	            if(left == true){
	                if(visited[i-1][j] == 1 || visited[i][j+1] == 1){
	                   //visited[i][j] = 1; 
	                   return true;
	                }
	            }
	            else if(right == true){
	                if(visited[i][j-1] == 1 || visited[i-1][j] == 1){
	                   return true;
	                }
	            }
	          else{
	              if(visited[i][j+1] == 1 || visited[i][j-1] == 1|| visited[i-1][j] == 1  ){
	                   return true;
	              }
	          }
	        
	        
	    }
	    
	    
	    else if(left == true){
	            if(visited[i-1][j] == 1 || visited[i+1][j] == 1 || visited[i][j+1] == 1)
	                   return true;
	                
	           }
	    
	    else if(right == true){
	            if(visited[i][j-1] == 1 || visited[i+1][j] == 1 || visited[i-1][j] == 1)
	                  return true;
	                
	           }
	    else
	     {
	      if(visited[i][j-1] == 1 || visited[i][j+1] == 1 || visited[i-1][j] == 1 || visited[i+1][j] == 1 )
	        return true;
	      else
	        return false;
	     }
	    
	    
	    }
	    
	    
	    public int numIslands(char[][] grid) {
	        int row = grid.length;
	        if(row == 0)
	          return 0;
	        int column = grid[0].length;
	        if(column == 0)
	           return 0;
	        int[][] visited = new int[row][column];
	        int count = 0;
	        int noOfVisited = 0;
	        
	        for(int i = 0 ; i < row ; i++){
	            
	            for(int j =0; j < column;j++){
	                if(grid[i][j] == '1'){
	                    visited[i][j] =1;
	                    if(!checkVisited(i,j,row,column,visited))
	                    count = count +1;
	                    }
	                
	                
	            }
	            
	            
	        }
	        
	       return count; 
	        
	    }
	}
	
	
	


