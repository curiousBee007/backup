//http://www.geeksforgeeks.org/find-the-number-occurring-odd-number-of-times/
public class NumberOccuringOddNoTimes {

	int getOddOccurrence(int ar[], int ar_size) 
    {
        int i;
        int res = 0;
        for (i = 0; i < ar_size; i++) 
        {
            res = res ^ ar[i];
            //System.out.println(res);
        }
        return res;
    }
 
    public static void main(String[] args) 
    {
    	NumberOccuringOddNoTimes occur = new NumberOccuringOddNoTimes();
        int ar[] = new int[]{2, 3, 5, 4, 5, 2, 4, 3, 5, 2, 4, 4, 2};
        int n = ar.length;
        System.out.println(occur.getOddOccurrence(ar, n));
    }
}

