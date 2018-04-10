import java.util.*;

public class SerializeDesrialize {

	public static class TreeNode  
	 {  
	  int val;  
	  TreeNode left;  
	  TreeNode right;  
	  TreeNode(int data)  
	  {  
	   this.val=data;  
	  }  
	 }  
	
	public String serialize(TreeNode root) {
	    if(root==null){
	        return "";
	    }
	 
	    StringBuilder sb = new StringBuilder();
	 
	    LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
	 
	    queue.add(root);
	    while(!queue.isEmpty()){
	        TreeNode t = queue.poll();
	        if(t!=null){
	            sb.append(String.valueOf(t.val) + ",");
	            queue.add(t.left);
	            queue.add(t.right);
	        }else{
	            sb.append("#,");
	        }
	    }
	 
	    sb.deleteCharAt(sb.length()-1);
	    //System.out.println(sb.toString());
	    return sb.toString();
	}
	
	
	 
	// Decodes your encoded data to tree.
	public TreeNode deserialize(String data) {
	    if(data==null || data.length()==0)
	        return null;
	 
	    String[] arr = data.split(",");
	    
	    /*for(String str : arr){
	    	System.out.println(str);
	    }*/
	    
	    
	    TreeNode root = new TreeNode(Integer.parseInt(arr[0]));
	 
	 
	    LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
	    queue.add(root);
	 
	    int i=1;
	    while(!queue.isEmpty()){
	        TreeNode t = queue.poll();
	        //System.out.println(1);
	        if(t==null)
	            continue;
	        
	        //System.out.println(2);
	        //System.out.println();
	 
	        if(!arr[i].equals("#")){
	            t.left = new TreeNode(Integer.parseInt(arr[i])); 
	            //queue.offer : Adds the specified element at the end of the queue
	            queue.offer(t.left);
	 
	        }
	        
	        else{
	            t.left = null;
	            queue.offer(null);
	        }
	        i++;
	 
	        if(!arr[i].equals("#")){
	            t.right = new TreeNode(Integer.parseInt(arr[i]));    
	            queue.offer(t.right);
	 
	        }else{
	            t.right = null;
	            queue.offer(null);
	        }
	        i++;
	    }
	 
	    return root;
	}
	
	
	 public static void main(String[] args)  
	 {  
	SerializeDesrialize bi=new SerializeDesrialize();  
	  // Creating a binary tree  
	  TreeNode rootNode=createBinaryTree();  
	  System.out.println("Level Order traversal of binary tree will be:");  
	   String str = bi.serialize(rootNode);
	   System.out.println(str);
	   TreeNode newRoot = bi.deserialize(str);
	  
	  //levelOrderTraversal(rootNode);  
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
