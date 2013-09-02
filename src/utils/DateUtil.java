package utils;

import java.util.Calendar;

public class DateUtil {
	
	private int lastMonth;	
	private int lastMonthYear;
	private int currentMonth;
	private int currentYear;
	private Calendar cal;
	private final String[] months = {"January", "February", "March", "April", "May", "June",
									"July", "August", "September", "October", "November", "December"};
	
	public DateUtil(){
		cal = Calendar.getInstance();
	}
	
	/*Note: Calendar months start from 0 so Calendar.Month 
	actually returns current month - 1. Example Dec will be 11*/
	public int getLastMonth(){
		lastMonth = cal.get(Calendar.MONTH);
		//If Statement to  check if the current month is Jan which will return 0
		//If index is 0, then last Month should be 12
		if(lastMonth == 0){
			lastMonth = 12;
		}
		return lastMonth;
	}
	
	public int getCurrentYear(){
		currentYear = cal.get(Calendar.YEAR);
		return currentYear;
	}
	// This method fixes the condition where Last Month is in the previous year
	public int getLastMonthYear(){
		lastMonthYear = cal.get(Calendar.YEAR);
		if(getLastMonth() == 12){
			lastMonthYear = lastMonthYear - 1;
		}
		return lastMonthYear;
	}
	
	public int getCurrentMonth(){
		currentMonth = cal.get(Calendar.MONTH) + 1;
		return currentMonth;
	}
	
	public String getLastMonthString(){
		return getMonthString(lastMonth);
	}
	
	public String getCurrentMonthString(){
		return getMonthString(currentMonth);
	}
	
	private String getMonthString(int month){
		return months[month - 1];
	}
	

	
	
	
}
