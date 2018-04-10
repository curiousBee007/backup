import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;



public class ZigZagConversion {
	
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
	
	
	
	
	
	public List<List<Integer>> zigzagLevelOrder(TreeNode root){
		
		List<List<Integer>> arrList = findingLevelTree(root);
		int count =0;
		List<List<Integer>> arrList1 = new ArrayList<List<Integer>>();
		
		while(arrList.size() >0 ){
			if(count %2 == 0){
				List<Integer> temp = new ArrayList<Integer>();
				temp = arrList.get(0);
				arrList1.add(temp);
				arrList.remove(0);
				}
			
			if(count %2 == 1){
				List<Integer> temp = new ArrayList<Integer>();
				List<Integer> original = arrList.get(0);
				 for(int x = original.size() -1;x>=0;x--){
				    	temp.add(original.get(x));
				    }
				 arrList1.add(temp);
				 arrList.remove(0);
			}
			count = count + 1;
		}
		
		return arrList1;
		
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
	
	public static void main(String[] args){
	    
		ZigZagConversion obj = new ZigZagConversion();
		Integer[] input = {3,9,20,null,null,15,7};
		TreeNode root = obj.arrayToTree(input);
		 List<List<Integer>> temp = obj.zigzagLevelOrder(root);
		 System.out.println(temp);
		
	}
	
	
}
