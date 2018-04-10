//Given a string s, find the longest palindromic subsequence's length in s. You may assume that the maximum length of s is 1000.
public class checkPalindrome {
	 
	/*public static boolean checkPalindrome1(String s){
	        
	       
	        if(s.length() == 0)
	          return true;
	        //boolean check = false;
	        StringBuilder sb = new StringBuilder(s);
	        String reverse = sb.reverse().toString();
		    return (s.equals(reverse));
	    }
	
	  //In a given string what is the length of longest palindromic sequence
	 public int longestPalindromeSubseq(String s) {
	        if(s == null || s.length() == 0)
	           return 0;
	           
	         int length1 = s.length();
	         int max = 0;
	         boolean boolArray[][] = new boolean[length1][length1];
	         for(int i = 0 ; i < length1;i++){
	             boolArray[i][i] = true;
	         }
	         
	         for(int k = 0 ; k < length1 -1; k++){
	             
	             for(int j = k+1; j < length1 ; j++){
	                 
	                 String s1 = "";

	                 for(int n = k ; n <= j;n++){
	                	 

	                    if(boolArray[k][n] == true){
	                         s1 = s1 + s.charAt(n);
	                     }
	                }

	              boolean val1 = checkPalindrome1(s1);
	             
	              boolArray[k][j] = val1;

	              if (val1){
	                  int length2 = s1.length();
	                  max = Math.max(length2,max);
	              }
	                 
	             }
	             
	            // System.out.println("**********************************");
	         }
	         
	         
	       return max; 
	    }*/

	//Brute force solution : O(2^n)

    //O(2^n) Brute force. If the two ends of a string are the same, then they must be included in the longest
	// palindrome subsequence. Otherwise, both ends cannot be included in the longest palindrome subsequence.

    public int longestPalindromeSubseqB(String s) {
        return longestPalindromeSubseq1(0,s.length()-1,s);
    }
    public int longestPalindromeSubseq1(int l, int r, String s) {
        if(l==r) return 1;
        if(l>r) return 0;  //happens after "aa"
        return s.charAt(l) == s.charAt(r) ? 2 + longestPalindromeSubseq1(l+1,r-1, s) :
            Math.max(longestPalindromeSubseq1(l+1,r, s),longestPalindromeSubseq1(l,r-1, s));
    }

	public int longestPalindromeSubseq(String s) {

		int n = s.length();
		int[][] mem = new int[s.length()][s.length()];
		return longestPalindromeSubseq1(0,n-1, s,mem);
	}

    //Memoisation solution  O(n^2)
	public int longestPalindromeSubseq1(int l, int r, String s, int[][] mem) {
		if(l==r) return 1;
		if(l>r) return 0;

		if(mem[l][r] > 0)
			return mem[l][r];

		return mem[l][r] = s.charAt(l) == s.charAt(r) ? 2 + longestPalindromeSubseq1(l+1,r-1, s,mem) :
				Math.max(longestPalindromeSubseq1(l+1,r, s,mem),longestPalindromeSubseq1(l,r-1, s,mem));

	}



	//Dp solution
	public int longestPalindromeSubseqBetter(String s) {
		int[][] dp = new int[s.length()][s.length()];

		for (int i = s.length() - 1; i >= 0; i--) {
			dp[i][i] = 1;
			for (int j = i+1; j < s.length(); j++) {
				if (s.charAt(i) == s.charAt(j)) {
					//System.out.println("Index i val1 is "+i+" and index j val1 is "+j);
					dp[i][j] = dp[i+1][j-1] + 2;
				} else {
					//System.out.println("Index i val2 is "+i+" and index j val2 is "+j);
					dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
				}
			}
		}
		return dp[0][s.length()-1];
	}


	public static void main(String[] args){
		String s1 = "anu";
		String s2 = "baab";
		String s3 = "abbba";
		
		String s4 = "bbbab";
		System.out.println(" Original string is: "+s1);
		System.out.println("**********************************");
		checkPalindrome obj = new checkPalindrome();
		int x = obj.longestPalindromeSubseqBetter(s1);
		System.out.println(x);
		
		
		/*System.out.println(checkPalindrome1(s1));
		System.out.println(checkPalindrome1(s2));
		System.out.println(checkPalindrome1(s3));*/
		
	}
}
