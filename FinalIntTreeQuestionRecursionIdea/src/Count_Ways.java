
public class Count_Ways {

	//How many ways you can count some stairs
	public int countWays(int n){
		
		if(n < 0){
			return 0;
			}
		
		else if(n == 0){
			return 1;
		}
		else{
			return countWays(n - 1) + countWays(n -2) + countWays(n-3);
		}
		
	}
	
	public int countwaysDP(int n , int[] map){
		if(n < 0){
			return 0;
		}
		else if(n == 0){
			return 1;
		}
		
		else if (map[n] > 0){
			return map[n];
		}
		else{
			map[n] = countwaysDP(n-1, map) + countwaysDP(n-2,map) + countwaysDP(n-3,map);
			return map[n];
		}
		
	}
	
	
	public static void main(String[] args){
		Count_Ways obj = new Count_Ways();
		int n = 3;
		int map[] = new int[n+1]; 
		int x = obj.countWays(n);
		System.out.println(x);
		
		int y = obj.countwaysDP(n,map);
		System.out.println(y);
	}
	
	
	
}
