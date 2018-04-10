
public class DistinctSubsequence {
	
	public int foo(String S, String T) {
        //if C[i][j] i means # chars. so C[?][0] === 1
		
		 if(S == null )
		 return 0;
		        
		 if(T == null)
		 return 1;
		        
		 if(T.length() > S.length())
		 return 0;
		 
        int m = S.length(), n = T.length();
        int[][] C = new int[m+1][n+1];
        for(int i=0;i<=m;i++) C[i][0] = 1;
        for(int i=1;i<=m;i++) {
            for(int j=1;j<=n;j++) {
                C[i][j] = C[i-1][j];
                if(S.charAt(i-1)==T.charAt(j-1)) C[i][j]+=C[i-1][j-1];
            }
        }
        
        
        for(int i = 0; i < m;i++){
        	for(int j = 0; j < n;j++){
        		System.out.print(" " +C[i][j]);
        	}
        	System.out.println();
        }
        
        
        
        return C[m][n];
    } 
	
	
public int numDistinct(String s, String t) {
        
        if(s == null )
        return 0;
        
        if(t == null)
        return 1;
        
        if(t.length() > s.length())
        return 0;
        
        int m = s.length(), n = t.length();
        int[] C = new int[n+1];
        C[0] = 1;
        for(int i=1;i<=m;i++) {
            int lastval=C[0];
            for(int j=1;j<=n;j++) {
                int thisval = C[j];
                if(s.charAt(i-1)==t.charAt(j-1)) C[j]+=lastval;
                lastval = thisval;
            }
        }
        return C[n];
        
    }

	public static void main(String[] args){
		
		DistinctSubsequence obj = new DistinctSubsequence();
		int x = obj.foo("rabbbit","rabbit");
		System.out.println(x);
		
		
	}

}
