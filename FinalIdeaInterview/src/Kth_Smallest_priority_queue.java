import java.util.PriorityQueue;

public class Kth_Smallest_priority_queue {

	public static int find(int [] A, int k){
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		for(int i=0;i<A.length;i++){
			//Insert the specified element in the priority queue
			pq.offer(A[i]);
		}
		int n = -1;
		while(k>0){
			//Retreives or returns the head of this queue
			n = pq.poll();
			k--;
		}
		return n;
	}
	public static void main(String[] args) {
		int[] A = { 1, 2, 10, 20, 40, 32, 44, 51, 6 };
		int k = 4;
		System.out.println("4th smallest element:" + find(A,4));

	}
	
}
