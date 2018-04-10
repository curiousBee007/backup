
public class RearrangeLastN {
	
	ListNode<Integer> rearrangeLastN(ListNode<Integer> l, int n) {
		
		if(l == null || n <=0)
			return l;

	    ListNode dummyPointer = new ListNode(-1);
	    dummyPointer.next = l;
	    ListNode slowPointer = dummyPointer;
	    ListNode fastPointer = dummyPointer;
	    int i = 0;
	    while(i < n && fastPointer != null){
	        fastPointer = fastPointer.next;
	        i = i +1;
	     }
	    if(fastPointer.next == null)
	    	return l;
	    
	    ListNode prevPointer = null;
	    ListNode futureLastNode = null;
	    while(fastPointer != null){
	    	futureLastNode = slowPointer; 
	        slowPointer = slowPointer.next;
	        prevPointer = fastPointer; 
	        fastPointer = fastPointer.next;
	    }
	    
	    ListNode head = slowPointer;
	    prevPointer.next = dummyPointer.next;
	    futureLastNode.next = null;
	    
	    return head;
	    

	}

	 public static void main(String[] args){
   	  ListNode head = new ListNode(1);
   	  head.next = new ListNode(2);
   	  head.next.next = new ListNode(3);
   	  head.next.next.next = new ListNode(4);
   	  head.next.next.next.next = new ListNode(5);
   	  //head.next.next.next.next.next = new ListNode(1);
   	  int n = 3;
   	  
   	  RearrangeLastN obj = new RearrangeLastN();
   	  ListNode head1 = obj.rearrangeLastN(head,n);
   	  while(head1 != null){
   		  System.out.println(head1.value);
   		  head1 = head1.next;
   	  }
   	  
   	 
     }
}
