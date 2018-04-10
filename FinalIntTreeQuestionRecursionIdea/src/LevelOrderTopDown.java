import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class  LevelOrderTopDown{
	
	public class TreeNode {
	    public TreeNode left, right;
	    public int val;

	    public TreeNode(int val) {
	        this.val = val;
	    }
	}

	public TreeNode arrayToTree(Integer[] input){
	    TreeNode root = createTreeNode(input,1);
	    return root;
	}

	private TreeNode createTreeNode(Integer[] input, int index){
	    if(index<=input.length){
	        Integer value = input[index-1];
	        if(value!=null){
	            TreeNode t = new TreeNode(value);
	            t.left = createTreeNode(input, index*2);
	            t.right = createTreeNode(input, index*2+1);
	            return t;
	        }
	    }
	    return null;
	}
	
	public static List<List<Integer>> findingLevelTree(TreeNode root){

		List<List<Integer>> finalList = new ArrayList<List<Integer>>();
		
		 if(root == null)
			return finalList;
		
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		
		queue.add(root);
		
		while(!queue.isEmpty()){
			
			List<Integer> arrList = new ArrayList<Integer>();
			
			int initialQueueSize = queue.size();
			
			for(int i = 0 ; i < initialQueueSize ; i++){
				TreeNode top = queue.poll();
				arrList.add(top.val);
				
				if(top.left != null)
					queue.add(top.left);
				
				if(top.right != null)
					queue.add(top.right);
				
				
				}
			
			finalList.add(arrList);
			
			}
		
		return finalList;
	}
	
	public int countNodes(TreeNode root) {
        List<List<Integer>> finalList = findingLevelTree(root);
        int count = 0;
        for(List<Integer> t: finalList){
        	count = count +t.size();
        }
        
        return count;
        
    }
	
	
	public List<Integer> rightSideView(TreeNode root){
		
		List<Integer> list1 = new ArrayList<Integer>();
		
		List<List<Integer>> finalList = findingLevelTree(root);
		
		for(List<Integer> t : finalList){
			list1.add(t.get(t.size() - 1));
			
		}
		
		
		return list1;
		
	}
	
	  public boolean isValidBST(TreeNode root) {
	        
	         return isBSTUtil(root, Integer.MIN_VALUE,
	                               Integer.MAX_VALUE,null);
	        
	    }
	    
	    
	     boolean isBSTUtil(TreeNode node, int min, int max,TreeNode prevNode)
	    {
	    	 if (node == null)
	             return true;
	            
	          
	          
	          if(prevNode != null){
	        	 
	          if(prevNode.val == node.val)
	        	 return false;
	          
	          
	          
	          
	          }
	          
	          if(node.left == null && node.right == null)
	          return true;
	      
	  
	         /* false if this node violates the min/max constraints */
	         if (node.val <= min || node.val >= max)
	             return false;
	  
	         /* otherwise check the subtrees recursively
	         tightening the min/max constraints */
	         // Allow only distinct values
	         return (isBSTUtil(node.left, min, node.val,node) &&
	                 isBSTUtil(node.right, node.val, max,node));
	    }
	
	
	     
	     
	     
	
	    public static void main(String[] args){
		    
		    LevelOrderTopDown obj = new LevelOrderTopDown();
			Integer[] input = {5,4,8,11,null,13,4,7,2,null,null,null,null,5,1};
			TreeNode root = obj.arrayToTree(input);
			int count = obj.countNodes(root);
			System.out.println(count);
			
			List<List<Integer>> finalList = findingLevelTree(root);
			System.out.println(finalList);
			
			/*List<Integer> rightSide = obj.rightSideView(root);
			System.out.println(rightSide);
			
			System.out.println(Integer.MAX_VALUE);*/
			
			//System.out.println(obj.isValidBST(root));
			/*int y = 2<<3-1;
			System.out.println(y);
			ArrayList<Integer> arrList = new ArrayList<Integer>();*/
			//arrList.c
			
		}
		
		
	
	
	
}
