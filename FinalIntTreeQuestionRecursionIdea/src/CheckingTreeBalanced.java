

class Node1 {
 
    int data;
    Node left, right;
 
    Node1(int d) {
        data = d;
        left = right = null;
    }
}

public class CheckingTreeBalanced {

	public static int checkHeight(Node root){
		
		if(root == null){
			return 0;
		}
		//Check if left is balanced
		int leftHeight = checkHeight(root.left);
		
		if(leftHeight == -1){
			//System.out.println("Left"+root.data);
			return -1;//Not balanced
		}
		
		
		//Check if left is balanced
		 int rightHeight = checkHeight(root.right);
		 
		 if(rightHeight == -1){
			//System.out.println("Right"+root.data);
					return -1;//Not balanced
		 }
		 
		 //Check if current node is balanced
		 int heightDiff = leftHeight - rightHeight;
		 
		 if (Math.abs(heightDiff) > 1){
			 return -1;//Not balanced
		 }
		 
		 else{
			 //Return height
			 //System.out.println(" Root data "+root.data + "Height " + Math.max(leftHeight, rightHeight) + 1 );
			 return Math.max(leftHeight, rightHeight) + 1;
		 }
				
		}
	
	
	public static boolean isBalanced(Node root){
		
		if( checkHeight(root) == -1)
		{return false;
		
		}
		
		else{
			return true;
		}
		
		}
	
	
	
	
	
	
	
	
	
	 public static void main(String args[])
	    {
	        //Height height = new Height();
	 
	        /* Constructed binary tree is
	                   1
	                 /   \
	                2      3
	              /  \    /
	            4     5  6
	            /
	           7
	           /
	          8*          */
		    CheckingTreeBalanced tree = new CheckingTreeBalanced();
		    /*Node root = new Node(1);
	        root.left = new Node(2);
	        root.right = new Node(3);
	        root.left.left = new Node(4);
	        root.left.right = new Node(5);
	        root.right.right = new Node(6);
	        root.left.left.left = new Node(7);
	        root.left.left.left.left = new Node(8);*/
		    /*    1
		    *    / \
		    *    25
		    * *
		    */

		    
		    Node root = new Node(1);
	        root.left = new Node(2);
	        root.right = new Node(5);
	        root.left.left = new Node(3);
	        //root.left.right = new Node(5);
	        //root.right.right = new Node(6);
	        root.left.left.left = new Node(4);
	        //root.left.left.left.left = new Node(8);
		    
	        if (tree.isBalanced(root))
	            System.out.println("Tree is balanced");
	        else
	            System.out.println("Tree is not balanced");
	    }
	
}
