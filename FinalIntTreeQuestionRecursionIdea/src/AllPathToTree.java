import java.util.ArrayList;
import java.util.List;

//This program prints all root to leaf paths
public class AllPathToTree {

	List<String> arrList3 = new ArrayList<String>();
	
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
	
	
	
	 void printPaths(TreeNode node) {
	        int path[] = new int[1000];
	        printPathsRecur(node, path, 0);
	        System.out.println(arrList3);
	    }
	 
	    /* Recursive helper function -- given a node, and an array containing
	     the path from the root node up to but not including this node,
	     print out all the root-leaf paths.*/
	    void printPathsRecur(TreeNode node, int path[], int pathLen) {
	        if (node == null) {
	            return;
	        }
	 
	        /* append this node to the path array */
	        path[pathLen] = node.val;
	        pathLen++;
	 
	        /* it's a leaf, so print the path that led to here  */
	        if (node.left == null && node.right == null) {
	            printArray(path, pathLen);
	        }
	        else {
				/* otherwise try both subtrees */
	            printPathsRecur(node.left, path, pathLen);
	            printPathsRecur(node.right, path, pathLen);
	        }
	    }
	 
	    /* Utility function that prints out an array on a line. */
	    void printArray(int ints[], int len) {
	        int i;
	        String temp = "";
	        for (i = 0; i < len; i++) {
	        	
	            temp = temp + String.valueOf(ints[i]);
	            temp = temp +"->";
	           // System.out.print(ints[i] + " ");
	        }
	        String newStr = temp.substring(0, temp.length()-2);
	        arrList3.add(newStr);
	        System.out.println("");
	    }
	
	public static void main(String[] args){
		
		
		    AllPathToTree obj = new AllPathToTree();
			Integer[] input = {1,2,3,null,5,null,null};
			TreeNode root = obj.arrayToTree(input);
			
			obj.printPaths(root);
			
	}

}
