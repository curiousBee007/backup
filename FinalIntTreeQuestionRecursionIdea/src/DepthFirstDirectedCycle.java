import java.util.Iterator;
import java.util.LinkedList;

class Graph{
	
	private int V;
	LinkedList<Integer> adj[];
	
	Graph(int v){
		V = v;
		adj = new LinkedList[V];
		for(int i = 0; i < V;i++){
			adj[i] = new LinkedList<Integer>();
		}
		}
		
		public void addEdge(int v,int w){
			adj[v].add(w);
		}
		
	}
	
	
	

public class DepthFirstDirectedCycle {
	
	public boolean isCyclicUtil(int v , boolean visited[],boolean recurseStack[],Graph g){
		
		if(visited[v] == false){
			
			visited[v] = true;
			recurseStack[v] = true;
			
			Iterator<Integer> i = g.adj[v].listIterator();
			while(i.hasNext()){
				int n = i.next();
				
				if(!visited[n] && isCyclicUtil(n,visited,recurseStack,g))
					return true;
				//Self loop condition and parallel edges //and child has been an ancestor then it is a tree
				
				else if(recurseStack[n])
					return true;
			
			}
		}
		recurseStack[v] = false;
		return false;
	}
	
	public boolean isCyclic(){
		Graph g = new Graph(4);
		g.addEdge(0, 1);
		//g.addEdge(0, 2);
		g.addEdge(1, 2);
		//g.addEdge(2, 0);
		g.addEdge(2, 3);
		//g.addEdge(3, 3);
		
		boolean visited[] = new boolean[4];
		boolean recursionStack[] = new boolean[4];
		
		for(int i = 0; i < 4;i++){
			
			if (isCyclicUtil(i, visited, recursionStack, g))
				return true;
			
		}
		
		return false;
	}
	
	
	
	
	public static void main(String[] args){
		
		DepthFirstDirectedCycle obj = new DepthFirstDirectedCycle();
		if(obj.isCyclic())
			System.out.println("Graph is cyclic");
		else{System.out.println("Graph is not cyclic");}
	}

}
