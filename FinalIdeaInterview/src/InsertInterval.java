import java.util.ArrayList;

//Definition for an interval.

/*class Interval {
    int start;
    int end;
    Interval() { start = 0; end = 0; }
    Interval(int s, int e) { start = s; end = e; }
}*/



public class InsertInterval {

    public ArrayList<Interval> insert(ArrayList<Interval> intervals, Interval newInterval) {

        ArrayList<Interval> result = new ArrayList<Interval>();

        for(Interval interval: intervals){
            if(interval.end < newInterval.start){
                result.add(interval);
            }

            else if(interval.start > newInterval.end){
                result.add(newInterval);
                newInterval = interval;
            }

            else if(interval.end >= newInterval.start || interval.start <= newInterval.end){
                newInterval = new Interval(Math.min(interval.start, newInterval.start), Math.max(newInterval.end, interval.end));
            }
        }

        result.add(newInterval);

        return result;
    }

    //A more simpler approach and easy to understand
	 /* public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
		    List<Interval> result = new LinkedList<>();
		    int i = 0;
		    // add all the intervals ending before newInterval starts
		    while (i < intervals.size() && intervals.get(i).end < newInterval.start)
		        result.add(intervals.get(i++));
		    // merge all overlapping intervals to one considering newInterval
		    while (i < intervals.size() && intervals.get(i).start <= newInterval.end) {
		        newInterval = new Interval( // we could mutate newInterval here also
		                Math.min(newInterval.start, intervals.get(i).start),
		                Math.max(newInterval.end, intervals.get(i).end));
		        i++;
		    }
		    result.add(newInterval); // add the union of intervals we got
		    // add all the rest
		    while (i < intervals.size()) result.add(intervals.get(i++));
		    return result;
		} */





    public static void main(String[] args){


    }

}
