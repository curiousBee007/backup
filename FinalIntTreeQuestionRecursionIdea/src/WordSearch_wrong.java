
import java.util.*;
public class WordSearch_wrong {
	
	public ArrayList<String> findingArrayValues(char a[][], int start_x,int start_y ,int i,int j,
			                                    ArrayList<String> arrList,Set<String> map,int count){
		
		int m = a.length;
		int n = a[0].length;
		
		if(i >= m || j >= n)
			return arrList;
		
		int max = m *n;
		while(count < max){
			
			if(i >=0 ){
				StringBuilder str = new StringBuilder();
				for(int x = start_x ; x <= i ; x++){
					str.append(a[x][j]);
				}
				String ver_word = str.toString();
				if(map.contains(ver_word)){
					arrList.add(ver_word);
				}
				findingArrayValues(a,start_x,start_y ,i+1,j,arrList,map,count);
			}
			

			
			if(j >=0 ){
				StringBuilder str1 = new StringBuilder();
				for(int x = start_y ; x <= i ; x++){
					str1.append(a[i][x]);
				}
				String hoz_word = str1.toString();
				if(map.contains(hoz_word)){
					arrList.add(hoz_word);
				}
				findingArrayValues(a,start_x,start_y,i,j+1,arrList,map,count);
			}
			
			
			StringBuilder str2 = new StringBuilder();
			int temp_x = start_x;
			int temp_y = start_y;
			while(temp_x < m && temp_y < n){
				str2.append(a[temp_x][temp_y]);
			}
			
		
			String diag_word = str2.toString();
			if(map.contains(diag_word)){
				arrList.add(diag_word);
			}
			
			findingArrayValues(a,start_x,start_y,i+1,j+1,arrList,map,count);
			start_x = (start_x +1)/n;
			start_y = (start_y +1)%n;
			findingArrayValues(a,start_x,start_y,i+1,j+1,arrList,map,count+1);
			
		}
		
		
		return arrList;
		}
	
	public static void main(String[] args){
		
		/*char a[][] = {{'H','E','N','D'},{'I','G','X','Y'}, {'S','G','O','Z'},{'T','H','A','D'}};
		Set<String> hashSet = new HashSet<String>();
		hashSet.add("END");
		hashSet.add("HAD");
		hashSet.add("GOD");
		ArrayList<String> arrList = new ArrayList<String>();
		WordSearch_wrong obj = new WordSearch_wrong();
		arrList = obj.findingArrayValues(a,0,0,0,0,arrList,hashSet,0); 
		for(String s : arrList){
			System.out.println(s);
		}*/
		String a = "NIKHIIL";
		String str = 'A'+a;
		
		String str1 = a.substring(0,a.length() -1);
		System.out.println(str1);
	}

}
