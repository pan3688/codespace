package hw1.Database;

import hw1.Exceptions.DuplicateEntryException;
import hw1.Exceptions.InvalidDateFormatException;
import hw1.Exceptions.InvalidRangeException;
import hw1.Exceptions.WrongArgumentCountException;
import hw1.Model.BirthdayDataModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
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
	
	public static boolean add(BirthdayDataModel model) throws DuplicateEntryException{
		
		if(isOriginal(model))
			inMemoryDb.add(model);
		else
			throw new DuplicateEntryException();
		
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
	
	public static boolean update(String command) 
			throws WrongArgumentCountException, ParseException, InvalidDateFormatException, DuplicateEntryException{
		
		String[] input = command.split(" ");
		
		if(BirthdayDataModel.isDateValid(input[3].trim())){
			
			if(input.length == 4){
				
				for(BirthdayDataModel model : inMemoryDb){
					
					if(model.getFirstName().equalsIgnoreCase(input[1].trim()) 
							&& model.getLastName().equalsIgnoreCase(input[2].trim()))
						model.setDate(new Date(input[3].trim()));
				}
			}else if(input.length == 6){
				
				BirthdayDataModel temp = new BirthdayDataModel(input[3].trim(),input[5].trim(),input[4].trim());
				
				/*
				 * if the oldFirstName matches newFirstName & oldLastName matches newLastName,
				 * then we are essentially updating the same record
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
						}
					}
				}else
					throw new DuplicateEntryException();
			}else
				throw new WrongArgumentCountException(input[0].trim());
		}
		
		return true;
	}
	
	public static List<BirthdayDataModel> query(String range,BufferedWriter bw) throws InvalidRangeException,NumberFormatException, IOException{
		
		int intRange = Integer.parseInt(range);
		ArrayList<BirthdayDataModel> match = new ArrayList<BirthdayDataModel>();
		
		if(intRange < 0)
			throw new InvalidRangeException(); 
		
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
	
}
