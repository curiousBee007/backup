
public class StackGeneric {
	
	
	//Item make use of generics in Java
	
	public interface Stack <Item>
	{
	    Item getTop(); // return the top item without removing it from stack
	    Item pop(); // return the top item and removes it from stack
	    void push(Item itm); // adds an item to the stack
	    boolean isEmpty(); // returns true if stack is empty, false otherwise
	    int size();  // returns the number of items in stack right now
	 
	}
	
	
	public class ArrayStack<Item> implements Stack<Item>
	{
	    private Item container[];
	    
	    private int top;
	    
	    private final static int DEFAULT_SIZE = 10;
	 
	    public ArrayStack ()
	    {
	        this(DEFAULT_SIZE);
	    }
	 
	    public ArrayStack (int initSize)
	    {
	        container = (Item[]) new Object [initSize];
	        top = -1;
	    }
	 
	    public Item getTop()
	    {
	        if (top == -1)
	            return null;
	        
	        return container[top];
	    }
	 
	    
	    public boolean isEmpty()
	    {
	        return (top == -1);
	    }
	 
	    
	    public Item pop()
	    {
	        if (top == -1)
	            return null;
	        
	        return container[top--];
	        
	    }
	 
	    public void push(Item itm)
	    {
	        container[++top] = itm;
	    }
	 
	    public int size()
	    {
	        return (top + 1);
	    }
	}
	
	
	}
