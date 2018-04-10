import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;



public class SymmetricTree {
	
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
	
	public boolean isSymmetric(TreeNode root) {
        if(root == null)
        return true;
        
        TreeNode left = root.left;
        TreeNode right = root.right;
         boolean val1 = isMirrorImage(left, right);
         //System.out.println(val1);
         return val1;
         }
	
	
	
         
         public boolean isMirrorImage(TreeNode left, TreeNode right){
             if(left == null || right == null)
             return (left == null && right == null);
             
             
             
      return(left.val == right.val && isMirrorImage(left.left,right.right) && isMirrorImage(left.right, right.left));
         }
         
	
         public boolean isSymmetric1(TreeNode root) {
             if(root == null || (root.left == null && root.right == null))
                 return true;
                 
             Queue<TreeNode> lq = new LinkedList<TreeNode>();
             Queue<TreeNode> rq = new LinkedList<TreeNode>();
             
             lq.add(root.left);
             rq.add(root.right);
             TreeNode leftTemp = null;
             TreeNode rightTemp = null;
             
             while(lq.isEmpty() == false && rq.isEmpty() == false){
                 leftTemp = lq.poll();
                 rightTemp = rq.poll();
                 
                 if(leftTemp == null && rightTemp == null)
                     continue;
                 
                 if((leftTemp == null && rightTemp != null) || (leftTemp != null && rightTemp == null))
                     return false;
                 
                 if(leftTemp.val != rightTemp.val)
                     return false;
                 
                 //take care of the order when adding left and right child to left and right queue
                 lq.add(leftTemp.left);
                 lq.add(leftTemp.right);
                 
                 rq.add(rightTemp.right);
                 rq.add(rightTemp.left);
             }
             
             //since the left and right always have same size, at here both of them are empty
             
             return true;
             
         }
         
	
	
	
public static void main(String[] args){
		
		
	  SymmetricTree obj = new SymmetricTree();
		Integer[] input = {1,2,2,3,4,4,3};
		TreeNode root = obj.arrayToTree(input);
		boolean val = obj.isSymmetric1(root);
		System.out.println(val);
		
		
		
		
		
	}
	
	
	

}
