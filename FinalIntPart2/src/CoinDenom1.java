
public class CoinDenom1 {
	
	public static int changePossibilitiesBottomUp(int amount, int[] denominations){
		
		//Initially all values are mapped to be 0
		int[] waysDoingNCents = new int[amount +1];
		
		waysDoingNCents[0] = 1;
		
		for(int coin:denominations){
			
			for(int higherAmount = coin; higherAmount < amount+1; higherAmount++){
				
				int higherAmountRemainder = higherAmount - coin;
				waysDoingNCents[higherAmount] += waysDoingNCents[higherAmountRemainder];
				
				}
			}
		
		return waysDoingNCents[amount];
		
		}
	
	public static void main(String[] args){
		int amount = 4;
		int a[] = {1,2,7};
		int x = changePossibilitiesBottomUp(amount , a);
		System.out.println(" Number of ways = " +x);
		
		}
}
