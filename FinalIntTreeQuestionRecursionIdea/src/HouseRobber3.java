import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;



public class HouseRobber3 {
	
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
	

	public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result=new ArrayList<List<Integer>>(); 
       if(root==null) return result; 
       Stack<ArrayList<Integer>> stack=new Stack<ArrayList<Integer>>();
       ArrayList<TreeNode> list=new ArrayList<TreeNode>();
       list.add(root);
       while(!list.isEmpty())
       {
           ArrayList<TreeNode> Tplist=new ArrayList<TreeNode>();
           ArrayList<Integer> level=new ArrayList<Integer>();
           while(!list.isEmpty())
           {
               TreeNode node=list.remove(0);
               level.add(node.val);
               if(node.left!=null) Tplist.add(node.left);
               if(node.right!=null) Tplist.add(node.right);
           }
           stack.push(level);
           list=Tplist; 
       }
       while(!stack.isEmpty())
       {
           result.add(stack.pop());
       }
       return result;
       
   }
	
	
	 public int rob(TreeNode root) {
	        
		 List<List<Integer>> arrList = findingLevelTree(root);
		 System.out.println(arrList);
			
		 
		 if(arrList == null)
			 return 0;
		 
		 int evenSum = 0;
		 int oddSum = 0;
		 for(int i = 0 ; i< arrList.size();i++){
			 if(i%2 == 0){
				 List<Integer> arr = arrList.get(i);
				 for(Integer x : arr)
					 evenSum = evenSum + x;
					 
				 
			 }
			 
			 else{
				 List<Integer> arr1 = arrList.get(i);
				 for(Integer x : arr1)
					 oddSum = oddSum + x;
					 
				 
			 }
			 
		 }
	 
		 int max = Math.max(evenSum, oddSum);
		 return max;
	 
	 }
	
	
	
	
	public static void main(String[] args){
		
		HouseRobber3 obj = new HouseRobber3();
		//Integer[] input = {3,2,3,null,3,null,1};
		
		Integer[] input1 = {4,1,null,2,null,3};
		TreeNode root = obj.arrayToTree(input1);
		int max = obj.rob(root);
		System.out.println(max);
		List<List<Integer>> list1 = obj.levelOrderBottom(root);
		System.out.println(list1);
		
		
		
		
		
		
	}


}
