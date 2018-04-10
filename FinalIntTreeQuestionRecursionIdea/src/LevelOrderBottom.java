import java.util.*;

public class LevelOrderBottom {
	
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
	
	public List<List<Integer>> levelOrderBottom(TreeNode root) {
        
        if(root == null){
            return null;
        }
      
       LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
       List<List<Integer>> finalList = new ArrayList<List<Integer>>();
       
		 queue.add(root);
		 
		 int count = 0;
		 
		 List<Integer> arrList = new ArrayList<Integer>();
		 
		 arrList.add(root.val);
		 finalList.add(arrList);
		 count = 2;
		 int level = 1;
		 
		 //System.out.println("Count Value " +count);
		 
		 while(!queue.isEmpty()){
		     
		     List<Integer> arrList1 = new ArrayList<Integer>();
		     
		     TreeNode top = queue.remove();
		     
		     System.out.println("Count Value " +count);
		     
		     for(int i = 0; i < count ;i++){

		     if(top != null){
		       
		       if(top.left != null){
		           queue.add(top.left);
		           arrList1.add(top.left.val);
		           i += 1;
		           
		       }
		           
		        if(top.left == null)
		         i += 1;
		         
		       
		     if(top.right != null){
		           queue.add(top.right);
		           arrList1.add(top.right.val);
		           i += 1;
		     }
		     
		     if(top.right == null)
		         i += 1;
		         
		   }
		 }
		 level = level +1;
		 count = (int)Math.pow(2,level);
		 finalList.add(arrList1);
		 
}
List<List<Integer>> reverseList = new ArrayList<List<Integer>>();

for(int j = finalList.size() -1; j >=0;j--){
  
   if(finalList.get(j).size() >0)	
   reverseList.add(finalList.get(j));
   }

return reverseList;

}
	
	
	public int findingLevel(int size){
		int templevel =  size;
		int level = 0;
		while(templevel >1){
			level = level +1;
			templevel = templevel/2;
			
		}
		return level;
		
	}
	
	
	public ArrayList<Integer> removingElement(int startIndex, int endIndex, ArrayList<Integer> arrList){
		
		int x = startIndex;
		System.out.println(" Start index "+startIndex  +" End index  "+endIndex);
		
		System.out.println("  Before deletion  "+arrList + " Value at index 1  " +arrList.get(1));
		while(x < endIndex){
			if(endIndex < arrList.size()){System.out.print(" Index removed " +x +" Val " +arrList.get(x));
			arrList.remove(x);
			x++;}
		}
		System.out.println();
		System.out.println(" After  deletion"+arrList);
		System.out.println();
		return arrList;
		
		
		
	} 
	
	public void printingArr(ArrayList<Integer> arr){
		
		for(Integer t : arr){
			System.out.print( "  " +t+ " ");
		}
		System.out.println("-------------------------------------------------------------- ");
	}
	
	
	public List<List<Integer>>  arrayValFromTree(TreeNode root){
		
		if(root == null)
			return null;
		
		
		ArrayList<Integer> arrList = new ArrayList<Integer>();
		//arrList.add(root.val);
		
		ArrayList<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		int rootIndex = 0;
		treeNodeList.add(root);
		arrList.add(root.val);
		
		while(root != null && rootIndex <= treeNodeList.size()){
			
			
			
			treeNodeList.add(root.left);
			treeNodeList.add(root.right);
			
			if(root.left == null)
				arrList.add(null);
			else
			  arrList.add(root.left.val);
			
			if(root.right == null)
				arrList.add(null);
			else
			 arrList.add(root.right.val);
			
			rootIndex = rootIndex +1;
			root = treeNodeList.get(rootIndex);
			
			
		}
		
		System.out.println("  Original array ");
		System.out.println(arrList);
		
		int templevel =  arrList.size() +1;
		int level = 0;
		while(templevel >1){
			level = level +1;
			templevel = templevel/2;
			
		}
		List<List<Integer>> finalList = new ArrayList<List<Integer>>();
		
		//System.out.println( " ArrayList size  "+ arrList.size());
		//System.out.println( " Level value  "+ level);
		for(int i = 0; i < level;i++){
			
			int x = (int)(Math.pow(2,i));
			List<Integer> arrList2 = new ArrayList<Integer>();
			
			for(int j = 0;j < x;j++){
				arrList2.add(arrList.get(j));
				}
			
			
			arrList = removingElement(0,x,arrList);
			/*int y = (int)(Math.pow(2,i+1));
			if (y == arrList.size()){
				finalList.add(arrList2);
				break;
				
			}*/
			
			
			//System.out.println( "--Value at index 1---- "+ arrList.get(1));
			//System.out.println( "-- "+ arrList);
			
			//printingArr((ArrayList<Integer>) arrList2);
			finalList.add(arrList2);
		}
		
		
		List<List<Integer>> reverseList = new ArrayList<List<Integer>>();

		for(int j = finalList.size() -1; j >=0;j--){
		  
		   if(finalList.get(j).size() >0)	
		   reverseList.add(finalList.get(j));
		   }
		
		return reverseList;
		
		
		
	}
	
	
	
	
	
	
	public static void printList(ArrayList<ArrayList<Integer>> val){
		
		for(List<Integer> t : val){
			
			for(Integer t1 :t ){
				System.out.print("  " +t +" ");
			}
			
			System.out.println();
		}
		
		
		
	}

	public ArrayList<ArrayList<Integer>> levelOrderBottom1(TreeNode root) {
        // Start typing your Java solution below
        // DO NOT write main() function
        ArrayList<ArrayList<Integer>> result=new ArrayList<ArrayList<Integer>>(); 
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
	
	
	
	
	
	

	public static void main(String[] args){
		
		
		LevelOrderBottom obj = new LevelOrderBottom();
		Integer[] input = {3,9,20,null,null,15,7};
		boolean y = (input[3] == null);
		System.out.println(y);
		TreeNode root = obj.arrayToTree(input);
		
		System.out.println("Root val " +root.val);
		ArrayList<ArrayList<Integer>> val = obj.levelOrderBottom1(root);
		printList(val);
		
		/*ArrayList<Integer> arrList = obj.arrayValFromTree(root);
		
		System.out.println(arrList.size());
		for(Integer t :arrList )
			System.out.print("   "+t);*/
		
		int x = obj.findingLevel(16);
		//System.out.println(x);
		//List<List<Integer>> val = obj.arrayValFromTree(root);
		//System.out.println(  "   Value of list is " +val.size());
		
		//printList(val);
		
		
	}
	
	
	

}
