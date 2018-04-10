import java.util.ArrayList;
import java.util.List;


public class RightSideView {
	
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

	
	List<Integer> arrList = new ArrayList<Integer>();
    public List<Integer> rightSideView(TreeNode root) {
        
        if(root == null)
        return arrList ;
        
        System.out.println(" Root val "+root.val);
        arrList.add(root.val);
        
        List<Integer> arrList1 = rightSideView(root.right);
        return arrList1;
        
        
        
        
    }
	
	public static void main(String[] args){
		
		
		RightSideView obj = new RightSideView();
			Integer[] input = {1,2};
			TreeNode root = obj.arrayToTree(input);
			
			List<Integer> arr1 = obj.rightSideView(root);
			System.out.println(arr1);
			
			
			
			
			
			
		}
		

}
