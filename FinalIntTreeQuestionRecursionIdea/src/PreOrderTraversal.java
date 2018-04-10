import java.util.ArrayList;
import java.util.List;


public class PreOrderTraversal {
	
	
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
	
	
	
	
    static ArrayList<Integer> arrList = new ArrayList<Integer>();
    
    
    void printPreorder(TreeNode node)
    {
        if (node == null)
            return;
        
        arrList.add(node.val);
 
        /* then recur on left sutree */
        printPreorder(node.left);
 
        /* now recur on right subtree */
        printPreorder(node.right);
    }
    
    void printPostorder(TreeNode node)
    {
        if (node == null)
            return;
 
        // first recur on left subtree
        printPostorder(node.left);
 
        // then recur on right subtree
        printPostorder(node.right);
        
        arrList.add(node.val);
 
        // now deal with the node
        //System.out.print(node.val " ");
    }
 
 
    public List<Integer> preorderTraversal(TreeNode root) {
		printPreorder(root);
        return arrList;
       
        
        }
	
	
	 public static void main(String[] args){
		    
		   PreOrderTraversal obj = new PreOrderTraversal();
			Integer[] input = {1,2};
			TreeNode root = obj.arrayToTree(input);
			List<Integer> arrList = obj.preorderTraversal(root);
			System.out.println( arrList);
			
			
		}
		
	
	

}
