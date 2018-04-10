import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.*;

public class ValidatingParenthesis {

	//Set<String> openSet1 = new HashSet<String>();
	//Set<String> closeSet1 = new HashSet<String>();
	
	
	public boolean isValid(String code) {

	    Map<Character, Character> openersToClosers = new HashMap<Character, Character>();
	    openersToClosers.put('(', ')');
	    openersToClosers.put('[', ']');
	    openersToClosers.put('{', '}');

	    Set<Character> openers = openersToClosers.keySet();
	    Set<Character> closers = new HashSet<Character>(openersToClosers.values());

	    Stack<Character> openersStack = new Stack<Character>();

	    for (char c : code.toCharArray()) {
	        if (openers.contains(c)) {
	            openersStack.push(c);
	        } 
	        
	        else if (closers.contains(c)) {
	            if (openersStack.empty()) {
	                return false;
	            } 
	            else {
	                char lastUnclosedOpener = openersStack.pop();

	                // if this closer doesn't correspond to the most recently
	                // seen unclosed opener, short-circuit, returning false
	                if (openersToClosers.get(lastUnclosedOpener) != c) {
	                    return false;
	                }
	            }
	        }
	    }
	    return openersStack.empty();
	}
	
	
	public boolean validParenthesis(String braces){
	
	Set openSet1 = new HashSet();
	openSet1.add('(');
	openSet1.add('{');
	openSet1.add('[');
	
	
	boolean balanced = true;
	
	Stack<Character> stack1 = new Stack<Character>();
	for(int i = 0 ; i < braces.length();i++){
		
		char ch = braces.charAt(i);
		boolean Val1 = openSet1.contains(ch);
		
		
		if(Val1){
			stack1.push(ch);
			continue;
			
		}
		
		
		if(!Val1)
		
		{
			
			if(stack1.size() >0){
				char top = stack1.pop();
			
				if(top == '('){
					
					if(ch == ')'){
						
						continue;
					}
					
					else{
						balanced = false;
						return balanced;
						//break;
					}
					
					
				}
				
				
                  if(top == '{'){
					
					if(ch == '}'){
						
						continue;
					}
					
					else{
						balanced = false;
						return balanced;
						//break;
					}
					
					
				}
				
                  if(top == '['){
  					
  					if(ch == ']'){
  						
  						continue;
  					}
  					
  					else{
  						balanced = false;
  						return balanced;
  						//break;
  					}
  					
  					
  				}
			}
			
		}
		
		
		
	}
	
	//System.out.println( "Parentheses are balanced " +balanced);
	return(balanced);
	
	}
	
	public static void main(String[] args){
		
		ValidatingParenthesis obj = new ValidatingParenthesis();
		//String braces = "{[](}";
		//String braces1 = "{[(])}";
		String braces2 = "{[}";
		
		boolean balanced_val = obj.validParenthesis(braces2);
		System.out.println( "Parentheses are balanced " +balanced_val);
	}
	
	
	
	
}
