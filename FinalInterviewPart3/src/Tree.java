
public class Tree {

	int nodeVal;
	Tree left,right;
	//Object cargo
	
	public Tree(int nodeVal, Tree left, Tree right){
		
		this.nodeVal = nodeVal;
		this.left = left;
		this.right = right;
		
	}
	
	
	//first print the contents of the root , then print entire left subtree and then right subtree
	public static void printPreOrder (Tree tree) { 
        if (tree == null) return; 
        System.out.print (tree.nodeVal + " "); 
        printPreOrder (tree.left); 
        printPreOrder (tree.right); 
    } 

	
	public static void printPostorder (Tree tree) { 
        if (tree == null) return; 
        printPostorder (tree.left); 
        printPostorder (tree.right); 
        System.out.print (tree.nodeVal + " "); 
    } 
	
	
	public static void printInorder (Tree tree) { 
        if (tree == null) return; 
        printInorder (tree.left); 
        System.out.print (tree.nodeVal + " "); 
        printInorder (tree.right); 
       
    }
	
	
	
	
	public static void main(String[] args){
		
		Tree left = new Tree(10,null,null);
		Tree right = new Tree(20,null,null);
		
		Tree root = new Tree(15,left,right);
		
		System.out.println("  Printing inOrder ");
		printInorder(root);
		System.out.println();
		
		System.out.println("  Printing preOrder ");
		printPreOrder(root);
		System.out.println();
		
		
		System.out.println("  Printing postOrder ");
		printPostorder(root);
		System.out.println();
	}
	
}
