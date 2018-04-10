
public class LongestAbsolutePath {

	public static int lengthLongestPath(String input) {
        
		System.out.println( "Input value is "+input);
		
		String[] paths = input.split("\n");
		
		System.out.println( "Total path length is "+paths.length);
        
        int[] stack = new int[paths.length+1];
        
        int maxLen = 0;
       
       for(String s:paths){
           
    	//If no such character is present then -1 is returned   
       int lev = s.lastIndexOf("\t")+1;
       System.out.println(" String is "+s + " and last index value is " + lev);
       
       stack[lev+1] = stack[lev]+s.length()-lev+1;
       
       int curLen = stack[lev+1]; 
       
       if(s.contains(".")) {
           maxLen = Math.max(maxLen, curLen-1);
           
        }
       }
     return maxLen;
   }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext";
		/*dir =0
		 * \tsubdir1 =1
		 * \t\tfile1.ext =2
		 * \t\tsubsubdir1 = 2
		 * \tsubdir2 = 1
		 * \t\tsubsubdir2 = 2
		 * \t\t\tfile2.ext =3
		 * */
		int x = lengthLongestPath(s);
	}

}
