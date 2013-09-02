package utils;

public class Constants {
	public String getCategory(int id){
		String category = null;
		switch (id){
			case 1 : category = "Food";
						break;
			case 2 : category = "Rent";
						break;
			case 3 : category = "Gas";
						break;
			case 4 : category = "Transport";
						break;
			case 5 : category = "Car Note";
						break;
			case 6 : category = "Phone";
						break;
			case 7 : category = "Misc";
						break;
			case 8 : category = "Entertainment";
						break;
			case 9 : category = "Electricity";
						break;
			case 10 : category = "Internet";
						break;
		}
		return category;
		
	}
}
