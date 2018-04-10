
  


public class AddingNumbersLinkedList {
	
	public class ListNode {
	      int val;
	      ListNode next;
	      ListNode(int x) { val = x; }
	 }
	 

public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        
        if(l1 == null || l2 == null)
        return null;
        
        ListNode rootNode = new ListNode(0);
        ListNode tempNode = rootNode;
        int sum = 0;
        int carry = 0;
        
         while(l1 != null && l2 != null){
          
             if(l1 == null || l2 == null){
            	 
                if(l1 == null && l2!= null){
                   sum = l2.val + carry;
                   carry = sum/10;
                   l2 = l2.next;
                    }
                else {
                    sum = l1.val + carry;
                    carry = sum/10;
                    l1 = l1.next;
                }
            
             }
             
             else{
                 
                 int a1 = l1.val;
                 int b1 = l2.val;
                 
                 sum =a1 + b1 + carry;
                 carry = sum/10;
                 sum = sum % 10;
                 l1 = l1.next;
                 l2 = l2.next;
                 }
             
            ListNode newNode = new ListNode(0);
            tempNode.val = sum;
            tempNode.next = newNode;
            
            tempNode = tempNode.next;
           
            
                
            }
            
            return rootNode;
        }
	
	

	
	
	
	

}
