

class ListNode<T> {
ListNode(T x) {
  value = x;
}
T value;
ListNode<T> next;
}

public class Palindrome {

	boolean isListPalindrome(ListNode<Integer> l) {
		   ListNode head = l;
		   ListNode fast = head, slow = head;
		   while(fast != null && fast.next != null){
		      
		      fast = fast.next.next;
		      slow = slow.next;
		   }
		   if(fast != null){
		      //for tackling lists with odd size
		      slow = slow.next;
		   }
		   slow = reverse(slow);
		   fast = head;
		   
		   while(slow != null){
			  int fast1 = (int) fast.value;
			  int slow1 = (int) slow.value;
			  
		      if(fast1 != slow1)
		         {
		    	  System.out.println("Fast value "+fast1);
		    	  System.out.println("Slow value "+slow1);
		    	  return false;
		         }
		      System.out.println();
		      
		      fast = fast.next;
		      slow = slow.next;
		   }
		   return true;
		}

		public ListNode reverse(ListNode head){
		   
		   ListNode prev = null;
		   while(head != null){
		      ListNode next = head.next;
		      head.next = prev;
		      prev = head;
		      head = next;
		   }
		  return prev;

		}
		
		ListNode<Integer> rearrangeLastN(ListNode<Integer> l, int n) {

		    ListNode dummyPointer = new ListNode(-1);
		    dummyPointer.next = l;
		    ListNode slowPointer = dummyPointer;
		    ListNode fastPointer = dummyPointer;
		    int i = 0;
		    while(i < n && fastPointer != null){
		        fastPointer = fastPointer.next;
		        i = i +1;
		     }
		    ListNode prevPointer = null;
		    while(fastPointer != null){
		        slowPointer = slowPointer.next;
		        prevPointer = fastPointer; 
		        fastPointer = fastPointer.next;
		    }
		    System.out.println("Slow pointer val "+slowPointer.value);
		    System.out.println("Prev pointer val "+prevPointer.value);
		    ListNode head = slowPointer;
		    prevPointer.next = dummyPointer.next;
		    return head;
		    

		}
		
		ListNode<Integer> addTwoHugeNumbers(ListNode<Integer> a, ListNode<Integer> b) {
			   a = reverse(a);
			   b = reverse(b);
			   
			   //When u are creating a new linked list 
			   ListNode head = new ListNode(0);
		       ListNode sumList = head;
			   int carry = 0;
			   
			   while(a != null && b!= null){
			      int sum = a.value + b.value + carry;
			      carry = sum / 10000;
			      sum = sum % 10000;
			      head.next = new ListNode(sum);
			      head = head.next;
			      a = a.next;
			      b = b.next;
			   }
			   while(a != null){
			      int sum = a.value + carry;
			      carry = sum / 10000;
			      sum = sum % 10000;
			      head.next = new ListNode(sum);
			      head = head.next;
			      a =a.next;
			   }
			   while(b != null){
			      int sum = b.value + carry;
			      carry = sum / 10000;
			      sum = sum % 10000;
		          head.next = new ListNode(sum);
			      head = head.next;
			      b = b.next;
			   
			   }
			   if(carry > 0){
				   head.next = new ListNode(carry);
			    }
			   
			   ListNode head1 = reverse(sumList.next);
			   return head1;
			   
			}

      public static void main(String[] args){
    	  ListNode head = new ListNode(1);
    	  head.next = new ListNode(1000000000);
    	  head.next.next = new ListNode(-1000000000);
    	  head.next.next.next = new ListNode(-1000000000);
    	  head.next.next.next.next = new ListNode(1000000000);
    	  head.next.next.next.next.next = new ListNode(1);
    	  
    	  ListNode a = new ListNode(0);
    	  //a.next = new ListNode(5432);
    	  //a.next.next = new ListNode(1999);
    	  
    	  ListNode b = new ListNode(1234);
    	  b.next = new ListNode(123);
    	  b.next.next = new ListNode(0);
    	  
    	  
    	  
    	 // a.next.next = new ListNode(5432);
    	  
    	  Palindrome obj = new Palindrome();
    	  ListNode head1 = obj.addTwoHugeNumbers(a,b);
    	  while(head1 != null){
    		  System.out.println(head1.value);
    		  head1 = head1.next;
    	  }
    	  
    	  //boolean val = obj.isListPalindrome(head);
    	  //System.out.println(val);
      }


}