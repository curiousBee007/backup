import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

//http://algorithms.tutorialhorizon.com/in-a-binary-tree-create-linked-lists-of-all-the-nodes-at-each-depth/

class Node2{
	int data;
	Node2 left;
	Node2 right;
	public Node2(int data){
		this.data = data;
		this.left = null;
		this.right =null;
	}
}


class ListNode{
	int data;
	ListNode next;
	public ListNode(int data){
		this.data = data;
		this.next = null;
	}	

  }


public class LinkedList_Nodes_Depth {
   
	ArrayList al = new ArrayList();
	
	public void display(ArrayList al){
			Iterator it = al.iterator();
			while(it.hasNext()){
			  ListNode head = (ListNode) it.next();
			 
			  while(head!=null){
				  System.out.print(" " + head.data);
				  head = head.next;
			  }
			  System.out.println("");
			}
			}
		
		
		

		public void levelOrderQueue(Node2 root){
	 		Queue q = new LinkedList();
	 		int levelNodes =0;
	 		if(root==null) return;
	 		q.add(root);
	 		while(!q.isEmpty()){
	 		      levelNodes = q.size();
	               	ListNode head = null;
	        	    ListNode curr = null;
	     			while(levelNodes > 0){
					Node2 n = (Node2)q.remove();
					ListNode ln = new ListNode(n.data);
					if(head==null){
						head = ln;
						curr = ln;
					}else{
						curr.next = ln;
						curr = curr.next;
					}
					if(n.left!=null) q.add(n.left);
					if(n.right!=null) q.add(n.right);
					levelNodes--;
				}
				al.add(head);
			}
			display(al);
		}
	
		public static void main (String[] args) 
		{
			
			Node2 root = new Node2(5);
			root.left = new Node2(10);
			root.right = new Node2(15);
			root.left.left = new Node2(20);
			root.left.right = new Node2(25);
			root.right.left = new Node2(30);
			root.right.right = new Node2(35);

			LinkedList_Nodes_Depth i  = new LinkedList_Nodes_Depth();
			//i.levelOrder(root);
			i.levelOrderQueue(root);
		}
	
	}
