
public class KMP_Substring_Search {
	/**
     * Slow method of pattern matching
     * Worst time : 0(mn)
     */
    public boolean hasSubstring(char[] text, char[] pattern){
        int i=0;
        int j=0;
        int k = 0;
        while(i < text.length && j < pattern.length){
            if(text[i] == pattern[j]){
                i++;
                j++;
            }else{
                j=0;
                k++;
                i = k;
            }
        }
        if(j == pattern.length){
            return true;
        }
        return false;
    }
    
    
    /**
     * Compute temporary array to maintain size of suffix which is same as prefix
     * Time/space complexity is O(size of pattern)
     */
    private int[] computeTemporaryArray(char pattern[]){
        int [] lps = new int[pattern.length];
        int index =0;
        //First element is always zero
        for(int i=1; i < pattern.length;){
            if(pattern[i] == pattern[index]){
                lps[i] = index + 1;
                index++;
                i++;
            }else{
                if(index != 0){
                    index = lps[index-1];
                }else{
                    lps[i] =0;
                    i++;
                }
            }
        }
        return lps;
    }
    
    /**
     * KMP algorithm of pattern matching.it can do substring search in O(m+n) time
     * If suffix is prefix as well then magic happens
     */
    public boolean KMP(char []text, char []pattern){
        //Temporary array takes 0(patternlEngth) to run
        int lps[] = computeTemporaryArray(pattern);

        int i=0;
        int j=0;
        //Rest of the algorithm runs in O(textLength)
        while(i < text.length && j < pattern.length){
            if(text[i] == pattern[j]){
                i++;
                j++;
            }else{
                if(j!=0){
                    j = lps[j-1];
                }else{
                    i++;
                }
            }
        }
        if(j == pattern.length){
            return true;
        }
        return false;
    }
        
    public static void main(String args[]){
        
        String str = "abcxabcdabcdabcy";
        String subString = "abcdabcy";
        KMP_Substring_Search ss = new KMP_Substring_Search();
        boolean result = ss.KMP(str.toCharArray(), subString.toCharArray());
        System.out.print(result);
        
    }
}
