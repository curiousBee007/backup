import java.util.Stack;

public class DecodeString {
	
	 public String gettingConcatvalue(String s,int digit){
		 StringBuilder sb = new StringBuilder();
		 
		 for(int i = 0; i < digit ; i++){
			 sb.append(s);
		 }
		 //System.out.println(sb.toString());
		 return sb.toString();
	 }
	
	 public String decodeString(String s) {
	        
	        Stack<String> tempVal = new Stack<String>();
	        Stack<Character> brace = new Stack<Character>();
	        Stack<Integer> digitStack = new Stack<Integer>();
	        int length = s.length();
	        
	        for(int i = 0 ; i < length;i++){
	           
	            if(i < length && s.charAt(i) >= '0' && s.charAt(i) <= '9'){
	                digitStack.push((int)(s.charAt(i)-48));
	            }
	          
	            
	            if(i < length && s.charAt(i) == '['){
	                brace.push('[');
	                i = i + 1;
	            }
	            
	            String newTemp = "";
	            while(i < length && !(s.charAt(i) >= '0' && s.charAt(i) <= '9') && s.charAt(i) != ']'){
	                newTemp = newTemp + s.charAt(i);
	                i = i +1;
	               // System.out.println(newTemp);
	            }
	            
	            if(newTemp.equals("") == false)
	            {   System.out.println(newTemp);
	            	tempVal.push(newTemp);
	            }
	            
	            if(i < length && s.charAt(i) == ']'){
	                //String newVal = "";
	                int digit1 = digitStack.pop();
	                
	                String topString = tempVal.pop();
	                System.out.println("digit value is "+digit1 + " and top string value is "+topString);
	                String newVal = gettingConcatvalue(topString,digit1);
	                System.out.println(newVal);
	                System.out.println("-------------------------------------------------------------------------");
	                if(tempVal.size() == 0){
	                    tempVal.push(newVal);
	                }
	                else{
	                    String top2 = tempVal.pop();
	                    top2 = top2 + newVal;
	                    tempVal.push(top2);
	                }
	            }
	            
	        }
	        
	        System.out.println(tempVal.size());
	        
	        
	        if(tempVal.size() == 1)
	           return tempVal.pop();
	           
	       String newVal2 = "";   
	        while(tempVal.size() > 1){
	            String firstPop = tempVal.pop();
	            String secondPop = tempVal.pop();
	            tempVal.push(secondPop + firstPop);
	        }
	        
	       return tempVal.pop(); 
	        
	    }
	 public static void main(String[] args){
		 
		 DecodeString obj = new DecodeString();
		 String s = "3[a]2[bc]";
		 String s1 = "3[a2[c]]";
		 String result = obj.decodeString(s1);
		 System.out.println(result);
		// String x = "Anu";
		 //String r = obj.gettingConcatvalue(x, 3);
		 //System.out.println(r);
	 }

}
