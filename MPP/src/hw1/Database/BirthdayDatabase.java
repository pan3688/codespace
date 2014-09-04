package hw1.Database;

import hw1.Model.BirthdayDataModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class BirthdayDatabase {

	private static ArrayList<BirthdayDataModel> inMemoryDb = null;
	private static Calendar calendar = null;
	
	static{
		
		inMemoryDb = new ArrayList<BirthdayDataModel>();
		calendar = GregorianCalendar.getInstance();
		calendar.setLenient(true);
		
	}
	
	public static boolean add(BirthdayDataModel model){
		
		if(isOriginal(model))
			inMemoryDb.add(model);
		else
			return false;
		
		return true;
	}
	
	public static boolean store(String outFileName) throws IOException{
		
		BufferedWriter storeDB = null;
		
		try {
			storeDB = new BufferedWriter(new FileWriter(new File(outFileName)));
		
			for(BirthdayDataModel bdays : inMemoryDb){
				
				storeDB.write(bdays.toString() + "\n");
				
			}
			storeDB.write("STORE: OK " + inMemoryDb.size() + "\n");
		} finally{
			
			try {
				storeDB.flush();
				storeDB.close();
			} catch (IOException e) {}
			
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean update(String command) 
			throws Exception{
		
		String[] input = command.trim().split(" ");
		
		if(input.length != 4 && input.length != 6)
			throw new Exception(input[0].trim() + ": ERROR WRONG_ARGUMENT_COUNT\n");
		
		boolean found = false;
		
		if(BirthdayDataModel.isDateValid(input[3].trim())){
			
			if(input.length == 4){
				
				for(BirthdayDataModel model : inMemoryDb){
					
					if(model.getFirstName().equalsIgnoreCase(input[1].trim()) 
							&& model.getLastName().equalsIgnoreCase(input[2].trim())){
						
						model.setDate(new Date(input[3].trim()));
						found = true;
					}
						
				}
			}else if(input.length == 6){
				
				BirthdayDataModel temp = new BirthdayDataModel(input[3].trim(),input[5].trim(),input[4].trim());
				
				/*
				 * if the oldFirstName matches newFirstName & oldLastName matches newLastName,
				 * then we are essentially updating the same record
				 * the below if condition will check this scenario and call the same update
				 * method with 3 arguments
				 */
				if(input[1].trim().equalsIgnoreCase(temp.getFirstName()) && input[2].trim().equalsIgnoreCase(temp.getLastName()))
					return update("UPDATE " + temp.getFirstName() + " " + temp.getLastName() + " " + input[3].trim());
					
				if(isOriginal(temp)){
					
					for(BirthdayDataModel model : inMemoryDb){
						if(model.getFirstName().equalsIgnoreCase(input[1].trim()) 
								&& model.getLastName().equalsIgnoreCase(input[2].trim())){
							model.setDate(new Date(input[3].trim()));
							model.setFirstName(temp.getFirstName());
							model.setLastName(temp.getLastName());
							found = true;
						}
					}
				}else
					return found;
			}
		}
		
		return found;
	}
	
	@SuppressWarnings("deprecation")
	public static List<BirthdayDataModel> query(String range) 
			throws NumberFormatException, Exception{
		
		int intRange = Integer.parseInt(range);
		ArrayList<BirthdayDataModel> match = new ArrayList<BirthdayDataModel>();
		
		if(intRange < 0)
			throw new Exception("SHOW: ERROR INVALID_INT\n"); 
		
		Date today = calendar.getTime();
		
		Calendar newCalendar = GregorianCalendar.getInstance();
		newCalendar.roll(Calendar.DATE, intRange);
		Date till = newCalendar.getTime();
		
		Calendar tempCal = null;
		Date tempDate = null;
		tempCal = GregorianCalendar.getInstance();
		tempCal.setTime(today);
		
		for(BirthdayDataModel model : inMemoryDb){
			
			tempCal.set(Calendar.DAY_OF_MONTH, model.getDate().getDate());
			tempCal.set(Calendar.MONTH, model.getDate().getMonth());
			
			tempDate = tempCal.getTime();
			
			if(today.compareTo(tempDate) == 0)
				match.add(model);
			else if(today.compareTo(tempDate) < 0 && till.compareTo(tempDate) >=0){
				match.add(model);
			}
		}
		
		return match;
	}
	
	public static boolean clear(){
		
		inMemoryDb.clear();
		
		return true;
	}
	
	/*
	 * returns true if the entry is original,
	 * false otherwise
	 */
	private static boolean isOriginal(BirthdayDataModel model){
		
		for(BirthdayDataModel bdm : inMemoryDb){
			
			if(model.getFirstName().equalsIgnoreCase(bdm.getFirstName()) 
					&& model.getLastName().equalsIgnoreCase(bdm.getLastName()))
				return false;
			else
				continue;
			
		}
		
		return true;
	}
	
	public static int dbSize(){
		return inMemoryDb.size();
	}
	
	public static List<BirthdayDataModel> searchNeedle(String needle){
		
		List<BirthdayDataModel> match = new ArrayList<BirthdayDataModel>();
		
		for(BirthdayDataModel model : inMemoryDb){
			
			if(model.getFirstName().contains(needle) 
					|| model.getLastName().contains(needle))
				match.add(model);
			
		}
		
		return match;
	}
	
}
