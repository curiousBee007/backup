import java.util.ArrayList;

public class SolutionMeetTime {
	
	public static class Meeting {

	    int startTime;
	    int endTime;

	    public Meeting(int startTime, int endTime) {
	        // number of 30 min blocks past 9:00 am
	        this.startTime = startTime;
	        this.endTime   = endTime;
	    }

	    public String toString() {
	        return String.format("(%d, %d)", startTime, endTime);
	    }
	}
	
	
	
	 public static void  meetingVal(ArrayList<Meeting> arrList) {
	        // write the body of your function here
	        ArrayList<Meeting> arrList1  = arrList;
	        int endTime = -1;
	        for(int x = 0 ; x < arrList1.size(); x++){
	            Meeting obj = arrList1.get(x);
	            endTime = Math.max(endTime, obj.endTime);
	         }
	        
	         boolean a[] = new boolean[endTime];
	         //System.out.println(endTime);
	          
	         for(int j = 0 ; j < arrList1.size(); j++){
	            Meeting obj = arrList1.get(j);
	            int start = obj.startTime;
	            int end = obj.endTime;
	              for(int k = start; k < end; k++){
	                a[k] = true;
	              }
	         }
	        
	        int finalStart = -1;
	        int finalEnd = -1;
	        boolean startValFound = false;
	        
	        ArrayList<Meeting> finalArrList = new ArrayList<Meeting>();
	        for(int i = 0; i < a.length;i++){
	            if(a[i] == true && !startValFound){
	            finalStart = i;
	            startValFound = true;
	            }
	            if(a[i] == false && startValFound  ){
	            finalEnd = i ;
	            Meeting tempObj = new Meeting(finalStart,finalEnd);
	            finalArrList.add(tempObj);
	            startValFound = false;
	           }
	            if (startValFound && i == a.length -1){
	            finalEnd = i+1;
	            Meeting tempObj1 = new Meeting(finalStart,finalEnd);
	            finalArrList.add(tempObj1);
	                
	            }
	            
	       }
	         
	        
	        
	        for(int i = 0 ; i < finalArrList.size(); i++){
	        
	            System.out.print(finalArrList.get(i).toString());
	            System.out.print("  ");
	        
	        }
	         
	             
	            
	            
	        
	    }
	    public static void main(String[] args) {
	        // run your function through some test cases here
	        // remember: debugging is half the battle!
	       
	        Meeting newObj =   new Meeting(1, 10);
	        Meeting newObj1 =   new Meeting(2, 6);
	        Meeting newObj2 =   new Meeting(3, 5);
	        Meeting newObj3 =   new Meeting(7, 9);
	       // Meeting newObj4 =   new Meeting(9, 10);
	        
	        ArrayList<Meeting> arrList = new ArrayList<Meeting>();
	        arrList.add(newObj);
	        arrList.add(newObj1);
	        arrList.add(newObj2);
	        arrList.add(newObj3);
	        //arrList.add(newObj4);
	        meetingVal(arrList);
	    }
	}


