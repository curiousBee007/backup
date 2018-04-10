import java.util.HashMap;

public class FlipGame2 {
	
	public boolean canWin(String s) {
	    if (s == null || s.length() < 2) {
	        return false;
	    }
	    HashMap<String, Boolean> winMap = new HashMap<String, Boolean>();
	    return helper(s, winMap);
	}

	public boolean helper(String s, HashMap<String, Boolean> winMap) {
	    if (winMap.containsKey(s)) {
	        return winMap.get(s);
	    }
	    for (int i = 0; i < s.length() - 1; i++) {
	        if (s.startsWith("++", i)) {
	            String t = s.substring(0, i) + "--" + s.substring(i+2);
	            if (!helper(t, winMap)) {
	            	//System.out.println(t);
	                winMap.put(s, true);
	                return true;
	            }
	        }
	    }
	    winMap.put(s, false);
	    return false;
	   }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FlipGame2 obj = new FlipGame2();
		String s = "-+-+";
		boolean result = obj.canWin(s);
		System.out.println(result);
	}

}
