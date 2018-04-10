import java.util.LinkedList;
import java.util.List;
import java.util.Queue;





public class HouseRobber {
	
	public class TreeNode {
	    public TreeNode left, right;
	    public int val;

	    public TreeNode(int val) {
	        this.val = val;
	    }
	}

	public TreeNode arrayToTree(Integer[] input){
		
   /*for(int i = 0; i < input.length;i++){
	    	System.out.print(input[i]);
	    }*/
		TreeNode root = createTreeNode(input,1);
	  
	    
	    return root;
	}

	private TreeNode createTreeNode(Integer[] input, int index){
		//System.out.println(input);
		
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
	
	
	void printPreorder(TreeNode node)
    {
        if (node == null)
            return;
        
       // arrList.add(node.val);
        
        System.out.println(node.val);
 
        /* then recur on left sutree */
        printPreorder(node.left);
 
        /* now recur on right subtree */
        printPreorder(node.right);
    }
    
	
	
	private TreeNode create (Integer[] input) {   
        TreeNode root = new TreeNode(input[0]);

         Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);

        final int half = input.length / 2;

        for (int i = 0; i < half; i++) {
            if (input[i] != null) {
                final TreeNode current = queue.poll();
                final int left = 2 * i + 1;
                final int right = 2 * i + 2;

                if (input[left] != null) {
                    current.left = new TreeNode(input[left]);
                    queue.add(current.left);
                }
                if (right < input.length && input[right] != null) {
                    current.right = new TreeNode( input[right]);
                    queue.add(current.right);
                }
            }
        }
        return root;
	}
	
	
	
	
	/*static int evenSum = 0;
	static int oddSum = 0;
	
	public void houseRobber(TreeNode root, int level){
		
		if(root == null)
			return ;
		
		if(level %2 == 0){
			
			evenSum = evenSum +root.val;
			//System.out.println("Root Value " +root.val +"   Evening "+evenSum);
			}
		
		else{
			oddSum = oddSum + root.val;
			//System.out.println("Root Value " +root.val +"   Oddsum  "+oddSum);
		
		}
		
		level = level +1;
		
		houseRobber(root.left,level);
		houseRobber(root.right,level);
		
	}
	
	public int rob(TreeNode root) {
		
		houseRobber(root,0);
		int max = Math.max(evenSum, oddSum);
		 return max;
	}*/

	
	public int rob(TreeNode root){
		
		if(root == null)
			return 0;
		
		int[] result = helper(root);
		return Math.max(result[0], result[1]);
		
	}
	
	
	
	
	public int[] helper(TreeNode root){
		
		if(root == null){
			int result[] = {0,0};
			return result;
			
		}
			
		int []result = new int[2];
		int []left = helper(root.left);
		int []right = helper(root.right);
		//result[0] is when root is selected, result[1] is when not
		
		result[0] = root.val + left[1] +right[1];
		result[1] = Math.max(left[0],left[1]) +Math.max(right[0],right[1]);
		System.out.println("  "+result[0] + "  "+ result[1]);
		
		return result;
		
	}
	
	
	
	
	
public static void main(String[] args){
		
		HouseRobber obj = new HouseRobber();
		Integer[] input = {1,2,3,4,5,6,7};
		
		
		//Integer[] input1 = {4,1,null,2,null,3};
		TreeNode root = obj.arrayToTree(input);
		//TreeNode root1 = obj.create(input);
		//obj.printPreorder(root1);
		int max = obj.rob(root);
		System.out.println(max);
		
		
	}

}
