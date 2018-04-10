import java.util.ArrayList;

//rotation of element
public class Rotation_Value {

public int[] rotationVal(int a[],int rotate){
	int n[] = new int[a.length];
	ArrayList<Integer> arrList = new ArrayList<Integer>();
	for(int i = 0; i < rotate;i++){
		System.out.println(a[i]);
		arrList.add(a[i]);
	}
	System.out.println("---------------------------------------------------");
	//int lastpoint = 0;
	for(int j = 0; j < a.length - rotate;j++){
		System.out.println(a[j +rotate]);
		n[j] = a[j +rotate];
		//lastpoint = j;
	}
	int x = 0;
	for(int k = a.length - rotate ; k < a.length;k++){
		n[k] = arrList.get(x);
		x = x+1;
	}
	return n;
}


public static void main(String[] args){
	Rotation_Value obj = new Rotation_Value();
	int a[] = {2,3,4,5};
	int [] n = obj.rotationVal(a,2);
	for(int i = 0; i < n.length;i++){
		System.out.print(" "+n[i]);
	}
	
}


}
