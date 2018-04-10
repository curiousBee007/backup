
public class BitProblems {

	//We clear all the bits other than the bit at i 
	public static boolean getBit(int num, int i){
		
		System.out.println(1<< i);
		System.out.println(num & (1 << i));
		return((num & (1 << i)) != 0);
	}
	
	//By performing or with num only value at bit i will change
	public static int setBit(int num,int i){
		
		return num | (1 << i);
	}
	
	public static int clearBit(int num , int i){
		
		int mask = ~(1 << i);
		return num & mask;
	}
	
	
	public static int clearBitsMSBthroughI(int num , int i ){
		int mask = (1 << i) -1;
		return num & mask;
		
	}
	
	public static int clearBitsItthrough0(int num , int i){
		
		int mask = ~((1 << (i +1 )) -1);
		return num & mask;
	}
	
	public static int updateBit(int num , int i , int v ){
		
		int mask = ~(1<< i);
		return (num & mask) | (v << i);
		
	}
	
	
	
	public static void main(String[] args){
		
		//int num = 11111111;
		int num = 64;
		int i = 6;
		System.out.println(getBit(num, i));
		
	}
}
