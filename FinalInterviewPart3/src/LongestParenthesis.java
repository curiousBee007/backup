import java.util.Stack;

public class LongestParenthesis {
	
	/*public int longestValidParentheses(String s) {
        if (s == null)
        return 0;
        int count = 0;
        Stack<Character> stack = new Stack<Character>();
        for(int i = 0; i < s.length();i++){
            
        	if(stack.isEmpty()){
                stack.push(s.charAt(i));
                continue;
            }
            if(!stack.isEmpty()){
            	
            	char s1 = stack.pop();
            	
                if (s1 == '(' && s.charAt(i) == ')'){
                 count = count +2;
                   }
                 else
                {
                 stack.push(s1);
                 stack.push(s.charAt(i));
                }
            }
            
            
            
        }
        
    
    return count;
    }*/
	
	public int longestValidParentheses(String s){
		
		 int len = s.length(), maxLen = 0, last = -1;
		    if (len == 0 || len == 1)
		        return 0;

		    //use this stack to store the index of '('
		    Stack<Integer> stack = new Stack<Integer>();
		    for (int i = 0; i < len; i++) {
		        if (s.charAt(i) == '(') 
		            stack.push(i);
		        else {
		            //if stack is empty, it means that we already found a complete valid combo
		            //update the last index.
		            if (stack.isEmpty()) {
		                last = i;        
		            } else {
		                stack.pop();
		                //found a complete valid combo and calculate max length
		                if (stack.isEmpty()) 
		                    maxLen = Math.max(maxLen, i - last);
		                else
		                //calculate current max length
		                    maxLen = Math.max(maxLen, i - stack.peek());
		            }
		        }
		    }

		    return maxLen;
	}
	
	
	
	public static void main(String[] args){
		
		LongestParenthesis obj = new LongestParenthesis();
		String s2 = "()(()";
		String s1 = "())";
		String s = ")()())";
		int count = obj.longestValidParentheses(s2);
		System.out.println("Count value  "+count);
				
	}
	
	
}