


public class DifferenceBetweenDates {
	
	
	
	public int helperFindingDayfunction(int startDate[],int endDate[]){
		
		int startDay = startDate[0];
		int startMonth = startDate[1];
		int startYear = startDate[2];
		
		int totalDays = 0;
		int  endDay =  endDate[0];
		int  endMonth = endDate[1];
		int  endYear = endDate[2];
		
		
	    int yearList[] = {0,31,28,31,30,31,30,31,31,30,31,30,31};
	
	    for(int i = startMonth +1 ; i < 13 ;i++){
		
		     totalDays = totalDays + yearList[i];
	      }
	
	
	    for(int j = 1; j < endMonth;j++){
		    totalDays = totalDays + yearList[j];
		
	     }
	
	 int totstartMonthDay = yearList[startMonth];
		
	  totalDays = totalDays +(totstartMonthDay - startDay);
	  
	  if(startYear % 4 == 0 && startMonth<= 2 ){
			
			totalDays = totalDays +1;
			
		}
	  
	  if(endYear % 4 == 0 && endMonth > 2 ){
			
			totalDays = totalDays +1;
			
		}
	 
	 
      totalDays = totalDays + endDay;
      
      return totalDays ;
	
		
		
	}
	
	
	
public int findDifference(int startDate[],int endDate[]){
		
		int startDay = startDate[0];
		int startMonth = startDate[1];
		int startYear = startDate[2];
		
		int totalDays = 0;
		int  endDay =  endDate[0];
		int  endMonth = endDate[1];
		int  endYear = endDate[2];
		
		int yearList[] = {0,31,28,31,30,31,30,31,31,30,31,30,31};
		
		int diffYear = endYear - startYear;
		
		//If the end years and start years are same , then two cases , same month , different months and leap year check
		
		if(diffYear == 0){
			
			
			if(endMonth == startMonth){
				totalDays = endDay -startDay;
			}
			
			
		if(endMonth > startMonth){
			
			for(int i = startMonth +1; i < endMonth ;i++){
				totalDays = totalDays + yearList[i];
			}
			
			 int totstartMonthDay = yearList[startMonth];
			
			  totalDays = totalDays +(totstartMonthDay - startDay);
			  
             if(startYear % 4 == 0 && startMonth<= 2 && endMonth > 2){
					
					totalDays = totalDays +1;
					
				}
				
             
             totalDays = totalDays + endDay;
			}
             
             return totalDays;
			
			
		}
		
		
		else if(diffYear == 1){
			
			totalDays = helperFindingDayfunction( startDate,endDate);
			return totalDays;
			
		}
	
		
		else{
			int noOfFullYears = diffYear -1;
			totalDays = totalDays + noOfFullYears * 365;
			int leapYearElapsedYears = ((startYear % 4)+noOfFullYears)/4;
			totalDays = totalDays + leapYearElapsedYears;
			 
			int extraDays = helperFindingDayfunction( startDate,endDate);
            totalDays = totalDays + extraDays;
            return totalDays;
			
			}
		}
		
		public int[] sizeMatrix(int a[][]){
			
			int row = a.length;
			int column = a[0].length;
			int n[] = {row,column};
			System.out.println(row);
			System.out.println(column);
			return n;
		}
		
	   

	
	public static void main(String[] args){
		
		DifferenceBetweenDates obj = new DifferenceBetweenDates();
		int a[] = {29,2,2016};
		int b[] = {29,11,2018};
		
		int findDifferenceDays = obj.findDifference(a, b);
		System.out.println(findDifferenceDays);
		
		int a11[][] = {{1,2},{3,4},{5,6}};
		obj.sizeMatrix(a11);
		
		
		
		
	}
	
	
	
	}


