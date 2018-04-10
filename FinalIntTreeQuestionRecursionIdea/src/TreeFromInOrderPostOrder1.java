

public class TreeFromInOrderPostOrder1 {
	
	
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
	

    static int preIndex = 0;
   public TreeNode buildTree(int[] preorder, int[] inorder) {
       int len = inorder.length;
       TreeNode mynode = buildTree1(inorder, preorder, 0, len - 1);
       return mynode;
       
     }
   
   
   TreeNode buildTree1(int inorder[], int preorder[], int inStrt, int inEnd) {

       if (inStrt > inEnd) {
           return null;
       }
       
      

       /* Pick current node from Preorder traversal using preIndex
        and increment preIndex */
       
       int x = preIndex++;
      // System.out.println(x) ;
       TreeNode tNode = new TreeNode(preorder[x]);

       /* If this node has no children then return */
       if (inStrt == inEnd) {
           return tNode;
       }

       /* Else find the index of this node in Inorder traversal */
       int inIndex = search(inorder, inStrt, inEnd, tNode.val);

       /* Using index in Inorder traversal, construct left and
        right subtress */
       tNode.left = buildTree1(inorder, preorder, inStrt, inIndex - 1);
       tNode.right = buildTree1(inorder, preorder, inIndex + 1, inEnd);

       return tNode;
   }

   /* UTILITY FUNCTIONS */
   /* Function to find index of value in arr[start...end]
    The function assumes that value is present in in[] */
   int search(int arr[], int strt, int end, int value) {
       int i;
       for (i = strt; i <= end; i++) {
           if (arr[i] == value) {
               return i;
           }

       }
       return i;
   }
   
 //This funtcion is here just to test buildTree() */
   void printInorder(TreeNode node) {
       if (node == null) {
           return;
       }

       /* first recur on left child */
      printInorder(node.left);

       /* then print the data of node */
       System.out.print(node.val + " ");

       /* now recur on right child */
       printInorder(node.right);
  }
   
   
   public static void main(String args[]) {
   	TreeFromInOrderPostOrder1 tree = new TreeFromInOrderPostOrder1();
       int in[] = new int[]{4,2,5,1,6,3,7};
       int pre[] = new int[]{1,2,4,5,3,6,7};
       int len = in.length;
       TreeNode mynode = tree.buildTree(pre,in);

       // building the tree by printing inorder traversal
       System.out.println("Inorder traversal of constructed tree is : ");
       tree.printInorder(mynode);
   }
	
	
	
	
	

}
