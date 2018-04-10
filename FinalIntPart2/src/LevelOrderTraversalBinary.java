import java.util.Queue;  
import java.util.LinkedList;  


public class LevelOrderTraversalBinary {
	public static class TreeNode  
	 {  
	  int data;  
	  TreeNode left;  
	  TreeNode right;  
	  TreeNode(int data)  
	  {  
	   this.data=data;  
	  }  
	 }  
	   
	// prints in level order  
	 public static void levelOrderTraversal(TreeNode startNode) {  
	  Queue<TreeNode> queue=new LinkedList<TreeNode>();  
	  Queue<TreeNode> checkQueue=new LinkedList<TreeNode>();  
	  queue.add(startNode); 
	  checkQueue.add(startNode);
	 
	  while(!queue.isEmpty())   {  
	   TreeNode tempNode=queue.poll(); 
	   if(tempNode != null){
		   
	   //System.out.printf("%d ",tempNode.data);  
	   //if(tempNode.left!=null)  
	    queue.add(tempNode.left);  
	    checkQueue.add(tempNode.left);
	   //if(tempNode.right!=null)  
	    queue.add(tempNode.right); 
	    checkQueue.add(tempNode.right);
	  } 
	  }
	   
	 System.out.println(" before trying to print queue ");
	 while(!checkQueue.isEmpty()) {
		 TreeNode tempNode=checkQueue.poll(); 
		 if(tempNode != null)
		 {
		 System.out.print( " "+tempNode.data);
		 }
		 else
		 {
		  System.out.print( " "+tempNode); 
		 }
		 
	 }
	 
	  System.out.println(" After trying to print queue ");     
	 }  
	 
	 
	 public static void main(String[] args)  
	 {  
	 LevelOrderTraversalBinary bi=new LevelOrderTraversalBinary();  
	  // Creating a binary tree  
	  TreeNode rootNode=createBinaryTree();  
	  System.out.println("Level Order traversal of binary tree will be:");  
	  levelOrderTraversal(rootNode);  
	 }  
	   
	 public static TreeNode createBinaryTree()  
	 {  
	    
	  TreeNode rootNode =new TreeNode(40);  
	  TreeNode node20=new TreeNode(20);  
	  //TreeNode node10=new TreeNode(10);  
	  //TreeNode node30=new TreeNode(30);  
	  TreeNode node60=new TreeNode(60);  
	  TreeNode node50=new TreeNode(50);  
	  TreeNode node70=new TreeNode(70);  
	    
	  rootNode.left=node20;  
	  rootNode.right=node60;  
	    
	  //node20.left=node10;  
	  //node20.right=node30;  
	    
	  node60.left=node50;  
	  node60.right=node70;  
	    
	  return rootNode;  
	 }  
	}  

