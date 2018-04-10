
public class longestPalindromicSubstring {
	
	public String longestPalindrome(String s) {
        
		
		if(s == null)
        return null;
        
		int length = s.length();
		
        if(length == 1){
            return s;
            }
        
        if (length == 2){
            if (s.charAt(0) == s.charAt(1))
            return s;
        }
        
        boolean value[][] = new boolean[length][length];
        
       /* for(int i = 0 ; i < length;i++){
            value[i][i] = true;
        }*/
        
        int maxLength = 0; int start = 0; int end = 0; int step = 0;
        
        for(int x = length -2; x >= 0; x--)
        
        {  
        	step = step +1;
        	
        for(int k = 0; k <= x ; k++){
        	
        	int i = k;
            int j = k+step;
            if (i+1 == j -1){
            	value[i][j] = true;
            }
            if (i+1 < j-1){
                boolean equalChar = (s.charAt(i) == s.charAt(j));
                if(value[i+1][j-1] && equalChar){
                    value[i][j] = true;
                    
                    int difference = (j - i +1);
                    if( difference > maxLength){
                        maxLength = difference;
                        start = i;
                        end = j;
                    }
                    }
                else
                {
                   value[i][j] = false; 
                }
            }
            else{
            	
            	char A = s.charAt(i);
            	char B = s.charAt(j);
            	boolean val1 = (s.charAt(i) == s.charAt(j));
            	value[i][j] =val1; 
              
                if(val1){
                int difference = (j - i +1);
                    if( difference > maxLength){
                        maxLength = difference;
                        start = i;
                        end = j;
                    }
                    
            }}
            
        }
        
        }
        
    
         String substr = s.substring(start,end +1);
         return substr;
         
       
        }
        
        public static void main(String[] args){
        	longestPalindromicSubstring obj = new longestPalindromicSubstring();
        	String s = "ddddddd";
        	String str = obj.longestPalindrome(s);
        	System.out.println(str);
        }

}
