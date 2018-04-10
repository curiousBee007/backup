import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
/*A Queue is an interface, which means you cannot construct a Queue directly.

The best option is to construct off a class that already 
implements the Queue interface, like one of the following: 
AbstractQueue, ArrayBlockingQueue, ConcurrentLinkedQueue, DelayQueue, 
LinkedBlockingQueue, LinkedList, PriorityBlockingQueue, PriorityQueue, or SynchronousQueue.*/

class WordObject{
	
	String s;
	int length;
	WordObject(String s1,int length1){
		s = s1;
		length = length1;
	}
	
}




public class WordLadderFreshBFS {
	
	
	public boolean isAdjacent(String s1, String s2){
		
		if(s1.equals(s2))
			return false;
		
		if(s1 == null || s2 == null|| (s1.length() != s2.length()))
			return false;
		
		int change = 0;
		
		for(int i = 0 ; i < s1.length();i++){
			
			if(s1.charAt(i) != s2.charAt(i))
				change = change +1;
			if(change > 1)
				return false;
				
		}
		
		if(change == 1)
			return true;
		
	 return false;
	
	 }


	public int ladderLength(String beginWord, String endWord, List<String> wordList) {
		
		Queue<WordObject> queue1 = new LinkedList<WordObject>();
		WordObject firstObject = new WordObject(beginWord,1);
		HashSet<String> hashSet = new HashSet<String>();
		hashSet.addAll(wordList);
		queue1.add(firstObject);
		
		//int prevLength = 1;
		
		while(!queue1.isEmpty()){
			
			WordObject currentWord = queue1.poll();
			
			Iterator<String> it = wordList.iterator();
			
			while (it.hasNext()) {
				
				String s1 = it.next();
				boolean isNeigh = isAdjacent(s1,currentWord.s);
                 
				if(isNeigh){
					System.out.println(s1);
					
					WordObject itemWord = new WordObject(s1,currentWord.length+1);
					
					queue1.add(itemWord);
					it.remove();
					
					
					if(s1.equals(endWord)){
						return (itemWord.length);
					}
					
				}
				
				
			}
			    
		}
    
		return 0;
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WordLadderFreshBFS obj = new WordLadderFreshBFS();
		String beginWord = "hit";
		String	endWord = "cog";
		//POON, PLEE, SAME, POIE, PLEA, PLIE, POIN
		//String[]wordList = {"hot","dot","dog","lot","log","cog"};
		List<String> arrList = new ArrayList<String>();
		arrList.add("hot");
		arrList.add("dot");
		arrList.add("dog");
		arrList.add("lot");
		arrList.add("log");
		arrList.add("cog");
		
		/*String beginWord = "a";
		String	endWord = "c";
		List<String> arrList = new ArrayList<String>();
		arrList.add("a");
		arrList.add("b");
		arrList.add("c");*/
		
		/*String beginWord = "toon";
		String	endWord = "plea";
		
		List<String> arrList = new ArrayList<String>();
		arrList.add("poon");
		arrList.add("plee");
		arrList.add("same");
		arrList.add("poie");
		arrList.add("plea");
		arrList.add("plie");
		arrList.add("poin");*/
		
		int length = obj.ladderLength(beginWord,endWord,arrList);
		System.out.println("Length of ladder is "+length);

	}

}
