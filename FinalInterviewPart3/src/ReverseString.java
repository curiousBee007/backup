
public class ReverseString {
	
	public boolean isVowel(char chr){
        boolean isVowel = false;
        if (chr == 'a' || chr == 'e' || chr == 'o' ||chr == 'u' || chr == 'i'
            || chr == 'A' || chr == 'E' || chr == 'I' || chr == 'O' || chr == 'U')
            isVowel = true;
            
        return isVowel;
    }
    
    
    public String reverseVowels(String s) {
        if (s == null)
        return null;
        int i = 0;
        
        int j = s.length() -1;
        char[] myNameChars = s.toCharArray();
        //int l = myNameChars. 
        boolean vowelFoundAtI = false;
        boolean vowelFoundAtJ = false;
        int valueI = -1;
        int valueJ = -1;
        
        //for(int x = 0; x < s.length(); i++){
            
           while(i < j){
               if (!isVowel(s.charAt(i)) && !vowelFoundAtI){
                   i++;
                   continue;
               }
              
               if (!isVowel(s.charAt(j)) && !vowelFoundAtJ){
                   j--;
                   continue;
               }
               
               if (isVowel(s.charAt(i))){
                   vowelFoundAtI = true;
                   valueI = i;
               }
               if (isVowel(s.charAt(j))){
                   vowelFoundAtJ = true;
                   valueJ = j;
               }
               
               if( vowelFoundAtI && vowelFoundAtJ && valueI != -1 && valueJ != -1){
                   
                  char temp = s.charAt(valueI);
                  char temp1 = s.charAt(valueJ);
                  //s.r
                  myNameChars[valueI] = temp1;
                  myNameChars[valueJ] = temp;
                  
                  vowelFoundAtI = false; 
                  vowelFoundAtJ = false;
                  valueI = -1;
                  valueJ = -1;
                  i++;
                  j--;
               }
               
           
            
           }
         s = String.valueOf(myNameChars);
        return s;
    }
        
   public static void main(String[] args){
	   ReverseString obj = new ReverseString();
	   String s = "leetcode";
	   String str = obj.reverseVowels(s);
	   System.out.println(str);
	   
   }

}
