
class TreeNode{
	int val;
	TreeNode left;
	TreeNode right;
	
	TreeNode(int val){
		this.val = val;
		this.left = null;
		this.right = null;
	}
	
}


public class Creating_Minimal_BST {

	public TreeNode createMinBST(int arr[])
	{
		return createBSTHelper(arr,0,arr.length -1);
	}
	
	
	public TreeNode createBSTHelper(int arr[],int start, int end ){
		if(end < start)
			return null;
		
		int mid = (start + end)/2;
		TreeNode root = new TreeNode(arr[mid]);
		root.left = createBSTHelper(arr,start,mid -1);
		root.right = createBSTHelper(arr,mid + 1, end);
		return root;
	}
	
	
	public static void main(String[] args){
		int arr[] = {10,20,30,40,50};
		Creating_Minimal_BST obj = new Creating_Minimal_BST();
		TreeNode root = obj.createMinBST(arr);
		System.out.println(root.val);
		System.out.println(root.left.val);
		System.out.println(root.left.right.val);
		System.out.println(root.right.val);
	}
}
