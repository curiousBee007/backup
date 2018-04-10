
import java.util.*;
public class FindingAllPaths {
	
	
	
	public class TreeNode {
	    public TreeNode left, right;
	    public int val;

	    public TreeNode(int val) {
	        this.val = val;
	    }
	}

	static List<List<Integer>> arrList = new ArrayList<List<Integer>>();
	
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
	
	/*public void  addingList(Stack<TreeNode> stack){
		
		ArrayList<Integer> arrList1 = new ArrayList<Integer>();
		ArrayList<Integer> arrList2 = new ArrayList<Integer>();
		int x = 0;
		while(!stack.empty()){
	      
	    	 TreeNode node1 = stack.pop();
	    	 arrList1.add(x,node1.val);
	    	 x = x+1;
	        }
		
		for(int x1 = arrList1.size()-1; x1>= 0;x1--){
			arrList2.add(arrList1.get(x1));
		}
		arrList.add(arrList2);
		
	}
	
	public void dfssum(int sum , Stack<TreeNode> stack,TreeNode root,int givenSum){
		
		if(root == null)
			return;
		
		if(!stack.contains(root))
		stack.push(root);
		
		sum = sum + root.val;
		
		if(root.left == null && root.right == null && sum == givenSum){
			//System.out.println("Inside equal value");
			addingList(stack);
			//stack.pop()
		}
		if(root.left == null && root.right == null && sum != givenSum){
			//System.out.println("Stack size "+stack.size());
			//addingList(stack);
		   //if(stack.size() >1)
			stack.pop();
		}
		
		//System.out.println(" Sum  val  " +sum + " Stack.size " +stack.size() + " Given sum val  "+givenSum);
		dfssum(sum, stack, root.left,givenSum);
		
		//System.out.println("Root value " +root.right + "Stack size " +stack.size() );
		//System.out.println("Stack size "+stack.size());
		dfssum(sum, stack, root.right,givenSum);
		
	}
	
    public List<List<Integer>> pathSum1(TreeNode root, int sum) {
    	Stack<TreeNode> stack1 = new Stack<TreeNode>();
    	
    	stack1.push(root);
    	dfssum(0,stack1,root,sum);
    	
    	return arrList;
    
    }*/
	
    
  
     
	
	public List<List<Integer>> pathSum(TreeNode root, int sum) {
	    List<List<Integer>> result = new ArrayList<List<Integer>>();
	    if(root == null) 
	        return result;
	 
	    ArrayList<Integer> l = new ArrayList<Integer>();
	    l.add(root.val);
	    dfs(root, sum-root.val, result, l);
	    return result;
	}
	 
	public void dfs(TreeNode t, int sum, List<List<Integer>> result, ArrayList<Integer> l){
	    if(t.left==null && t.right==null && sum==0){
	        ArrayList<Integer> temp = new ArrayList<Integer>();
	        temp.addAll(l);
	        result.add(temp);
	    }
	 
	    //search path of left node
	    if(t.left != null){
	        l.add(t.left.val);
	        dfs(t.left, sum-t.left.val, result, l);
	        l.remove(l.size()-1);
	    }
	 
	    //search path of right node
	    if(t.right!=null){
	        l.add(t.right.val);
	        dfs(t.right, sum-t.right.val, result, l);
	        l.remove(l.size()-1);
	    }
	}
	 
	
	 


	
	public static void main(String[] args){
		      
		    FindingAllPaths obj = new FindingAllPaths();
		    //Integer[] input = {1,2,2};
		    //Integer[] input1 = {1,2,2};
			Integer[] input1 = {5,4,8,11,null,13,4,7,2,null,null,null,null,5,1};
			TreeNode root = obj.arrayToTree(input1);
			//List<ArrayList<Integer>> list1 = obj.pathSum(root,22);
			List<List<Integer>> list1 = obj.pathSum(root,22);
			System.out.println(list1);
			
			
			
			}
	
	}
