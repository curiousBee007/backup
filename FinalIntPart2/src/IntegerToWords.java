import java.util.HashMap;
import java.util.Stack;

public class IntegerToWords {
	
	public String numberToWords(int num) {
		if(num == 0)
	         return "Zero";
		
		HashMap<Integer,String> hashmap = new HashMap<Integer,String>();
        hashmap.put(0,"");
        hashmap.put(1,"One");
        hashmap.put(2,"Two");
        hashmap.put(3,"Three");
        hashmap.put(4,"Four");
         hashmap.put(5,"Five");
        hashmap.put(6,"Six");
        hashmap.put(7,"Seven");
        hashmap.put(8,"Eight");
        hashmap.put(9,"Nine");
        hashmap.put(10,"Ten");
        hashmap.put(11,"Eleven");
        hashmap.put(12,"Twelve");
        hashmap.put(13,"Thirteen");
        hashmap.put(14,"Fourteen");
         hashmap.put(15,"Fifteen");
        hashmap.put(16,"Sixteen");
        hashmap.put(17,"Seventeen");
        hashmap.put(18,"Eighteen");
        hashmap.put(19,"Nineteen");
         hashmap.put(20,"Twenty");
         hashmap.put(30,"Thirty");
         hashmap.put(40,"Forty");
         hashmap.put(50,"Fifty");
         hashmap.put(60,"Sixty");
         hashmap.put(70,"Seventy");
         hashmap.put(80,"Eighty");
         hashmap.put(90,"Ninety");
         
        HashMap<Integer,String> valuePos = new HashMap<Integer,String>();
       
        valuePos.put(3, "Thousand");
         valuePos.put(6, "Million");
         valuePos.put(9, "Billion");
         
         
         //int valuePos = 0;
         Stack<Integer> stack = new Stack<Integer>();
         int noOfDigits = 0;
         int origNo = num;
         String s = "";
         
         while(num != 0){
             int d = num% 10;
             stack.push(d);
             noOfDigits = noOfDigits + 1;
             num = num/10;
             }
         while(stack.size() != 0){
             int countNo = 0;
             int num1 = -1;
             int num2 = -1;
              if(stack.size() > 0 && stack.size()%3 != 0){
                 num1 = stack.pop();
                 
                 if(stack.size() > 0 && stack.size()%3 != 0)
                  num2 = stack.pop();
                  
                  if(num1 >=0 && num2 >=0)
                    noOfDigits = noOfDigits -2;
                
                  if(num1 >=0 && num2 == -1)
                     noOfDigits = noOfDigits -1;
                  
                  s = s + helperTwoDigit(num1,num2,hashmap);
                  
                  if(valuePos.containsKey(noOfDigits))
                   s = s +" "+valuePos.get(noOfDigits)+ " ";
                  
                 }
                 
                 /*if(valuePos.containsKey(noOfDigits))
                   s = s +valuePos.get(noOfDigits);*/
                  
                  
             
             if(stack.size() >= 3 && stack.size() % 3 == 0){
                 int numHundred = stack.pop();
                 int numTen = stack.pop();
                 int numUnit = stack.pop();
                 s = s + helperThreeDigit(numHundred,numTen,numUnit,hashmap);
                 noOfDigits = noOfDigits -3;
                 
                 if(valuePos.containsKey(noOfDigits)){
                     
                	 s = s +" " + valuePos.get(noOfDigits)+" ";
                 }
                }
              
              
                  
             
         }
         String newString = s.trim();
         String s2 = "";
         for(int i = 0 ; i < newString.length()-1;i++){
             if(newString.charAt(i)== ' ' && newString.charAt(i+1) == ' '){
                 continue;
               }
            else{
                s2 = s2 + newString.charAt(i);
            }
           }
         s2 = s2 + newString.charAt(newString.length()-1);
          return s2;
         
        }
        
        private String helperThreeDigit(int numH,int numD,int numU,HashMap<Integer,String> hashmap){
            
            String val1 = "";
            
            if(numH > 0)
              val1 = val1 + hashmap.get(numH)+ " "+ "Hundred";
            
            String two = helperTwoDigit(numD,numU,hashmap);
            val1 = val1 + two;
            
            return val1;
            
            
        }
        
         private String helperTwoDigit(int num1,int num2,HashMap<Integer,String> hashmap){
            
            String val1 = "";
            if(num1 >= 0 && num2 >= 0){
               //Ten , eleven , twelve , thirteen case
            	if(num1 == 1)
                {
                   int newD = 10 +num2;
                   val1 = val1 +" " + hashmap.get(newD);
                   return val1; 
                }
                else
                {
                    int newD1 = num1 * 10;
                    if(newD1 != 0)
                      val1 = val1 +" "+ hashmap.get(newD1) + " " +hashmap.get(num2);
                    else
                    	val1 = val1 + hashmap.get(newD1) + " " +hashmap.get(num2);
                    	
                    return val1;
                }
            }
         
           else{
               val1 = val1 + hashmap.get(num1);
               return val1;
               
           }
         
             
         }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n1 = 12345;
		int n2 = 1234567;
		IntegerToWords obj = new IntegerToWords();
		String s1 = obj.numberToWords(1001);
		String s2 = obj.numberToWords(n2);
		System.out.println(s1);
		System.out.println(s2);
		
		

	}

}
