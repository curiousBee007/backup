import java.util.ArrayList;

public class PrintingAllPermutationsString {
	
	String swap(String s,int l ,int r)
	 {
	
	char ch[] = s.toCharArray();
	char temp = ch[l];
	ch[l] = ch[r];
	ch[r] = temp;
	
	String b = new String(ch);
	 return(b);
	 }


void permute(String s , int l ,int r){

	if(l == r){
	System.out.println(s);
	 }

	else{
    for(int i = l ; i <=r ; i++){

	s = swap(s,l,i);
	permute(s, l+1,r);
	s = swap(s,l,i); //backtracking
	
	}

}

 }


	
	//Simple method of recursion
	
	public static void permutation(String str) { 
	    permutation("", str); 
	}

	private static void permutation(String prefix, String str) {
	    int n = str.length();
	    
	    if (n == 0) System.out.println(prefix);
	    else {
	        for (int i = 0; i < n; i++)
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
	    }
	}
	
	
	//Wrong function
	public static ArrayList<String> permutationList(String prefix, String str) {
	    int n = str.length();
	    ArrayList<String> arrList = new ArrayList<String>();
	    if (n == 0) 
	    	{//System.out.println(prefix);
	    	arrList.add(prefix);
	    	return arrList;
	    	}
	    else {
	        for (int i = 0; i < n; i++)
	        arrList = permutationList(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
	        //arrList.add(prefix);
	    }
	    return arrList;
	}
	
	
	
	public static void main(String[] args){
		PrintingAllPermutationsString obj = new PrintingAllPermutationsString();
		String s = "ABC";
		int lengthValLastIndex = s.length() -1;
		
		/*ArrayList<String> c = permutationList("",  s);
		for(String s1 : c){
			System.out.println(s1);
		}*/
			
		
		
		obj.permute(s,0,lengthValLastIndex);
		//obj.permutation(s);
		
		
		
	}
}
