
public class Count_No_Bits {
	
	static int countSetBits( int n)
	{
	   int count = 0;
	  while(n != 0)
	  {
		int x = n & 1;
		System.out.println(x);
	    count += x;
	    n >>= 1;
	  }
	  return count;
	}
	
	
	public int countSetBits1(int n)
	{
	    int count = 0;
	    while (n != 1)
	    {
	      n &= (n-1) ;
	      count++;
	    }
	    return count;
	}
 
	public static void main(String[] args){
		int countVal = countSetBits(6 );
		System.out.println("Set bits value  "+countVal);
		
	}
	
}
