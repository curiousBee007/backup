import java.util.*;
public class WordLadder {
	
	
	  class WordNode{
		    String word;
		    int numSteps;
		    WordNode pre;
		 
		    public WordNode(String word, int numSteps, WordNode pre){
		        this.word = word;
		        this.numSteps = numSteps;
		        this.pre = pre;
		    }
		}
		 
		    
		    public List<List<String>> findLadders(String beginWord, String endWord, Set<String> wordList) {
		       List<List<String>> result = new ArrayList<List<String>>();
		 
		        LinkedList<WordNode> queue = new LinkedList<WordNode>();
		        queue.add(new WordNode(beginWord, 1, null));
		 
		        wordList.add(endWord);
		 
		        int minStep = 0;
		 
		        HashSet<String> visited = new HashSet<String>();  
		        HashSet<String> unvisited = new HashSet<String>();  
		        unvisited.addAll(wordList);
		 
		        int preNumSteps = 0;
		 
		        while(!queue.isEmpty()){
		        	//Removes the first element of the list
		            WordNode top = queue.remove();
		            String word = top.word;
		            int currNumSteps = top.numSteps;
		 
		            if(word.equals(endWord)){
		                if(minStep == 0){
		                    minStep = top.numSteps;
		                }
		   
		                if(top.numSteps == minStep && minStep !=0){
		                    //nothing
		                    ArrayList<String> t = new ArrayList<String>();
		                    t.add(top.word);
		                    while(top.pre !=null){
		                        t.add(0, top.pre.word);
		                        top = top.pre;
		                    }
		                    
		                   result.add(t);
		                   
		                }
		 
		            }
		 
		            if(preNumSteps < currNumSteps){
		                unvisited.removeAll(visited);
		            }
		 
		            preNumSteps = currNumSteps;
		 
		            char[] arr = word.toCharArray();
		            for(int i=0; i<arr.length; i++){
		                for(char c='a'; c<='z'; c++){
		                    char temp = arr[i];
		                    if(arr[i]!=c){
		                        arr[i]=c;
		                    }
		 
		                    String newWord = new String(arr);
		                    if(unvisited.contains(newWord)){
		                        queue.add(new WordNode(newWord, top.numSteps+1, top));
		                        visited.add(newWord);
		                    }
		 
		                    arr[i]=temp;
		                }
		            }
		 
		 
		        }
		 
		        return result;
		    }
		    
		    
		    
		    
		    public static void main(String[] args){
		    	WordLadder obj = new WordLadder();
		    	String beginWord = "hit";
		    	String endWord = "cog"; 
		    	Set<String> wordList = new HashSet<String>();
		    	
		    	wordList.add("hot");
		    	wordList.add("dot");
		    	wordList.add("dog");
		        wordList.add("lot");
		    	wordList.add("log");
		    	
		    	List<List<String>> result = obj.findLadders(beginWord, endWord, wordList);
		    	for(int i = 0; i <result.size();i++){
		    		ArrayList<String> t = (ArrayList<String>) result.get(i);
		    		for(String t1 :t){
		    			System.out.print(" " +t1 +" ");
		    			
		    		}
		    		System.out.println();
		    	}
		    	
		    	
		    	
		    }
		    
		    
		}


