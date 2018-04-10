
public class IslandProblemLeetcodeSoln {
	
	
	 public int numIslands(char[][] grid) {
		    if(grid==null || grid.length==0||grid[0].length==0)
		        return 0;
		 
		    int m = grid.length;
		    int n = grid[0].length;
		 
		    int count=0;
		    for(int i=0; i<m; i++){
		        for(int j=0; j<n; j++){
		            if(grid[i][j]=='1'){
		                count++;
		                
		                merge(grid, i, j);
		                for(int i1 = 0; i1 < m;i1++){
		                	for(int j1 = 0 ; j1 < n; j1++){
		                		System.out.print(""+grid[i1][j1]);
		                	}
		                	System.out.println();
		                }
		                
		            }
		            System.out.println();
		        }
		    }
		 
		    return count;
		}
		 
		public void merge(char[][] grid, int i, int j){
		    int m=grid.length;
		    int n=grid[0].length;
		 
		    if(i<0||i>=m||j<0||j>=n||grid[i][j]!='1')
		        return;
		 
		    grid[i][j]='X';
		 
		    merge(grid, i-1, j);
		    merge(grid, i+1, j);
		    merge(grid, i, j-1);
		    merge(grid, i, j+1);
		}

		
		public static void main(String[] args){
			
			IslandProblemLeetcodeSoln obj = new IslandProblemLeetcodeSoln();
			char grid[][] = {{'1','1','0','0','0'},{'1','1','0','0','0'},{'0','0','1','0','0'},{'0','0','0','1','1'}};
			int x = obj.numIslands(grid);
			System.out.println(x);
			
		}
		
}
