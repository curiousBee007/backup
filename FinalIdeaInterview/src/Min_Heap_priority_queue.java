import java.util.PriorityQueue;
public class Min_Heap_priority_queue {
	PriorityQueue<Integer> pq;

	public Min_Heap_priority_queue() {
		pq = new PriorityQueue<Integer>();
	}

	public void insert(int[] x) {
		for (int i = 0; i < x.length; i++) {
			pq.offer(x[i]);
		}
	}

	public int peek() {
		return pq.peek();
	}

	public int extractMin() {
		return pq.poll();
	}

	public int getSize() {
		return pq.size();
	}

	public void print() {
		System.out.println(pq);
	}

	public static void main(String[] args) {
		int[] arrA = { 1, 6, 2, 9, 4, 3, 8 };
		Min_Heap_priority_queue i = new Min_Heap_priority_queue();
		i.insert(arrA);
		i.print();
		System.out.println("Min Element in the Priority Queue: "
				+ i.extractMin());
		System.out.println("Min Element in the Priority Queue: "
				+ i.extractMin());
		System.out.println("Min Element in the Priority Queue: "
				+ i.extractMin());
		System.out.println("Priority Queue Size: " + i.getSize());
	}
}
