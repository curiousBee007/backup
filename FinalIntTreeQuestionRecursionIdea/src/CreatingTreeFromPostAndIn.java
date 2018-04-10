

public class CreatingTreeFromPostAndIn {

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
	
	
	
	
	
public int findIndex(int arr[], int val){
        
        int length = arr.length;
        int index = -1;
        if(length <=0)
        return -1;
        for(int i = 0; i < length;i++){
            if(arr[i] == val){
             index = i;
             break;
            }
        }
        
        return index;
    }
    
    
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        
        if (inorder.length == 0)
        return null;
        TreeNode root = createTree(0,postorder.length -1, 0,inorder.length -1,inorder,postorder );
        return root;
    }
    
    
    public TreeNode createTree(int postStart, int postEnd, int inStart, int inEnd, int[] inorder , int[] postorder){
        
        if(postStart > postEnd || inStart > inEnd)
        return null;
        
        int x = postorder[postEnd];
        
        TreeNode root = new TreeNode(x);
        
        int index = findIndex(inorder,x);
        
        int lengthRightSubtree = inorder.length - (index +1);
        int lengthLeftSubtree = index;
        
        //int postRightEndIndex = postEnd -1;
         int postRightStartIndex = postEnd -lengthRightSubtree;
         System.out.println("My value calculated "+postRightStartIndex);
         
         int y = postStart + index- inStart;
          System.out.println("Actual value used  "+y);
          
          System.out.println();
         
         int postLeftEndIndex = lengthLeftSubtree -1;
         //int postLeftStartIndex = 0;
         
         //int lengthLeftSubtree = 
         
         root.left = createTree(postStart, postStart + index - (inStart + 1) ,inStart, index-1,inorder,postorder );
         
         root.right = createTree(postStart + index- inStart, postEnd -1 ,index +1,inEnd, inorder,postorder );
         
         return root;
         
         
    }
    
    
	
	
     public static void main(String[] args){
		    
    	    CreatingTreeFromPostAndIn obj = new CreatingTreeFromPostAndIn();
			Integer[] input = {1,2,3,null,null,4,5};
			TreeNode root = obj.arrayToTree(input);
			
			/*int [] inorder = {10,30,40,50,60,70,90};
			int [] postorder = {10,40,30,60,90,70,50};
			obj.buildTree(inorder, postorder);*/
			
			
			
			
			
			
			
		}
		



}



