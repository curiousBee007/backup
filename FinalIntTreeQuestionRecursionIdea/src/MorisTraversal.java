

public class MorisTraversal {
	
	
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
	
	public TreeLinkNode arrayToTree1(Integer[] input){
		TreeLinkNode root = createTreeNode1(input,1);
	    return root;
	}
	
	
	private TreeLinkNode createTreeNode1(Integer[] input, int index){
	    if(index<=input.length){
	        Integer value = input[index-1];
	        if(value!=null){
	        	TreeLinkNode t = new TreeLinkNode(value);
	            t.left = createTreeNode1(input, index*2);
	            t.right = createTreeNode1(input, index*2+1);
	            t.next = null;
	            return t;
	        }
	    }
	    return null;
	}
	
	
	public void inorder(TreeNode root){
		
		TreeNode current = root;
		while(current != null){
			if(current.left == null){
				System.out.print(current.val + "  ");
				current = current.right;
			}
			else{
				TreeNode predecessor = current.left;
				while(predecessor.right != current && predecessor.right != null){
					predecessor = predecessor.right;
					}
				if(predecessor.right == null){
					predecessor.right = current;
					current = current.left;
				}
				
				else{
					predecessor.right = null;
					System.out.print(current.val + "  ");
					current = current.right;
					}
				
			}
			
		}
		
		
	}
	
	
public void preorder(TreeNode root){
		
		TreeNode current = root;
		while(current != null){
			if(current.left == null){
				System.out.print(current.val + "  ");
				current = current.right;
			}
			else{
				TreeNode predecessor = current.left;
				while(predecessor.right != current && predecessor.right != null){
					predecessor = predecessor.right;
					}
				if(predecessor.right == null){
					predecessor.right = current;
					System.out.print(current.val + "  ");
					current = current.left;
				}
				
				else{
					predecessor.right = null;
					//System.out.print(current.val + "  ");
					current = current.right;
					}
				
			}
			
		}
		
		
	}

public class TreeLinkNode {
	     int val;
	      TreeLinkNode left, right, next;
	     TreeLinkNode(int x) { val = x; }
	  }


public void connect(TreeLinkNode root) {
    
    if(root == null) 
    return;

TreeLinkNode lastHead = root;//prevous level's head 
TreeLinkNode lastCurrent = null;//previous level's pointer
TreeLinkNode currentHead = null;//currnet level's head 
TreeLinkNode current = null;//current level's pointer

while(lastHead!=null){
    lastCurrent = lastHead;

    while(lastCurrent!=null){
    	
        if(currentHead == null){
            currentHead = lastCurrent.left;
            current = lastCurrent.left;
        }else{
            current.next = lastCurrent.left;
            current = current.next;
        }

        if(currentHead != null){
            current.next = lastCurrent.right;
            current = current.next;
        }

        lastCurrent = lastCurrent.next;
    }

    //update last head
    lastHead = currentHead;
    currentHead = null;
}

}
    

	
   public static void main(String[] args){
	   MorisTraversal obj = new MorisTraversal();
	   Integer[] input = {10,5,15,2,7,null,30};
	   /*TreeNode root = obj.arrayToTree(input);
	   
	   obj.inorder(root);
	   System.out.println();
	   obj.preorder(root);
	   obj.connect(root);*/
	   
	   TreeLinkNode root = obj.arrayToTree1(input);
	   obj.connect(root);
	   
	   
   }
		
	
	
}
