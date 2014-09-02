package hw1;

import hw1.Database.BirthdayDatabase;
import hw1.Exceptions.DuplicateEntryException;
import hw1.Exceptions.InvalidDateFormatException;
import hw1.Exceptions.InvalidRangeException;
import hw1.Exceptions.UnknownCommandException;
import hw1.Exceptions.WrongArgumentCountException;
import hw1.Model.BirthdayDataModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class Birthdays {
		
	private static BufferedReader br = null;
	private static BufferedWriter bw = null;
	private static String command = "";
	
	public static void main(String[] args) {
		
		try {
			
			br = new BufferedReader(new FileReader(new File("in.txt")));
			bw = new BufferedWriter(new FileWriter(new File("out.txt")));
			
			command = br.readLine();
			
			while(command != null){
				
				try {
					if(command.startsWith("LOAD"))
						load(command);
					else if(command.startsWith("STORE"))
						store(command);
					else if(command.startsWith("CLEAR"))
						clear(command);
					else if(command.startsWith("ADD"))
						add(command);
					else if(command.startsWith("SHOW"))
						show(command);
					else if(command.startsWith("UPDATE"))
						update(command);
					else if(command.startsWith("SEARCH"))
						search(command);
					else
						throw new UnknownCommandException(command);
				} catch (WrongArgumentCountException e) {
					bw.write(e.getCommand() + ": ERROR WRONG_ARGUMENT_COUNT" + "\n");
				} catch (UnknownCommandException e) {
					bw.write(e.getCommand() + ": ERROR UNKNOWN_COMMAND\n");
				}
				
				command = br.readLine();
			}
		}catch (IOException e) {
			
		}finally{
			
			try {
				br.close();
				bw.flush();
				bw.close();
			} catch (IOException e) {}
		}
	}
	
	public static void load(String command) throws WrongArgumentCountException{
		
		String[] input = command.trim().split(" ");
		
		String csvLine = "";
		String[] csvParts = null;
		
		BirthdayDataModel model = null;
		int count = 0;
		BufferedReader brDatabaseLoad = null;
		
		if(input.length != 2)
			throw new WrongArgumentCountException(command);
		
		try {
			brDatabaseLoad = new BufferedReader(new FileReader(input[1].trim()));
			
			csvLine = brDatabaseLoad.readLine();
			
			while(csvLine != null){
				
				csvParts = csvLine.split(",");
				
				model = new BirthdayDataModel(csvParts[0].trim(), csvParts[1].trim(), csvParts[2].trim());
				
				BirthdayDatabase.add(model);
				
				count++;
				
				csvLine = brDatabaseLoad.readLine();
			}
			
			bw.write("LOAD: OK " + count + "\n");
			
		} catch (FileNotFoundException e) {
			try {
				bw.write("LOAD: ERROR FILE_NOT_FOUND" + "\n");
			} catch (IOException e1) {}
		} catch (IOException e) {
			try {
				bw.write("LOAD: ERROR IOException" + "\n");
			} catch (IOException e1) {}
		} catch (ParseException e) {
			try {
				bw.write("LOAD: ERROR INVALID_DATE" + "\n");
			} catch (IOException e1) {};
		} catch (InvalidDateFormatException e) {
			try {
				bw.write("LOAD: ERROR INVALID_DATE_FORMAT " + e.getCausedBy()+ "\n");
			}catch(IOException e1){}
		} catch (DuplicateEntryException e) {
			try {
				bw.write("LOAD: ERROR DUPLICATE_ENTRY " + "\n");
			}catch(IOException e1){}
		}finally{
			
			try {
				if(brDatabaseLoad != null)
					brDatabaseLoad.close();
			} catch (IOException e) {}
			
		}
		
	}

	public static void store(String command) throws WrongArgumentCountException{
		
		String[] input = command.trim().split(" ");
		
		if(input.length != 2)
			throw new WrongArgumentCountException(command);
		
		boolean done = false;
		
		try {
			done = BirthdayDatabase.store(input[1]);
			
			if(done)
				bw.write("STORE: OK " + BirthdayDatabase.dbSize() + "\n");
			
		} catch (IOException e) {
			try {
				bw.write("STORE: ERROR IOException " + "\n");
			}catch(IOException e1){}		
		}
		
	}
	
	public static void clear(String command){
		
		boolean done = false;
		
		try {
			done = BirthdayDatabase.clear();
			
			if(done)
				bw.write("CLEAR: OK " + "\n");
			
		} catch (IOException e) {
			try {
				bw.write("CLEAR: ERROR " + "\n");
			} catch (IOException e1){}
		}
		
	}
	
	public static void add(String command) throws WrongArgumentCountException{
		
		String[] input = command.split(" ");
		
		if(input.length != 4)
			throw new WrongArgumentCountException(command);
		
		boolean done = false;
		
		try {
			BirthdayDataModel model = new BirthdayDataModel(input[3].trim(),input[2].trim(),input[1].trim());
			
			done = BirthdayDatabase.add(model);
			
			if(done)
				bw.write("ADD: OK " + model.getFirstName() + " " + model.getLastName() + "\n");
			
		} catch (InvalidDateFormatException e) {
			try {
				bw.write("ADD: ERROR INVALID_DATE_FORMAT\n");
			} catch (IOException e1) {}
		} catch (ParseException e) {
			try {
				bw.write("ADD: ERROR INVALID_DATE\n");
			} catch (IOException e1) {}
		} catch (DuplicateEntryException e) {
			try {
				bw.write("ADD: ERROR DUPLICATE_ENTRY \n");
			} catch (IOException e1) {}
		} catch (IOException e) {
			try {
				bw.write("ADD: ERROR IOException \n");
			} catch (IOException e1) {}
		}
		
		
		
	}
	
	public static void show(String command) throws WrongArgumentCountException{
		
		String[] input = command.trim().split(" ");
		
		if(input.length != 2)
			throw new WrongArgumentCountException(command);
		
		List<BirthdayDataModel> records = null;
		
		try {
			records = BirthdayDatabase.query(input[1], bw);
			
			bw.write("SHOW: OK " + records.size() + "\n");
			
			for(BirthdayDataModel model : records){
				bw.write(model.toString() + "\n");
			}
			
		} catch (NumberFormatException e) {
			try {
				bw.write("SHOW: ERROR NOT_INT\n");
			} catch (IOException e1) {}
		} catch (InvalidRangeException e) {
			try {
				bw.write("SHOW: ERROR INVALID_INT\n");
			} catch (IOException e1) {}
		} catch (IOException e) {
			try {
				bw.write("SHOW: ERROR IOException\n");
			} catch (IOException e1) {}
		}
		
		
	}
	
	/*
	 * TO DO -- no record found logic is missing
	 */
	public static void update(String command) throws WrongArgumentCountException{
		
		boolean done = false;
		
		try {
			done = BirthdayDatabase.update(command);
			
			String[] input = command.trim().split(" ");
			
			if(done && input.length == 4)
				bw.write("UPDATE: OK " + input[1].trim() + " " + input[2].trim() + "\n");
			else if(done && input.length == 6)
				bw.write("UPDATE: OK " + input[4].trim() + " " + input[5].trim() + "\n");
			
		} catch (ParseException e) {
			try {
				bw.write("UPDATE: ERROR INVALID_DATE\n");
			} catch (IOException e1) {}
		} catch (InvalidDateFormatException e) {
			try {
				bw.write("UPDATE: ERROR INVALID_DATE_FORMAT\n");
			} catch (IOException e1) {}
		} catch (DuplicateEntryException e) {
			try {
				bw.write("UPDATE: ERROR DUPLICATE_ENTRY\n");
			} catch (IOException e1) {}
		} catch (IOException e) {
			try {
				bw.write("UPDATE: ERROR IOException\n");
			} catch (IOException e1) {}
		}
	}
	
	public static void search(String command) throws WrongArgumentCountException{
		
		String[] input = command.trim().split(" ");
		
		if(input.length != 2)
			throw new WrongArgumentCountException(command);
		
		List<BirthdayDataModel> list = BirthdayDatabase.searhNeedle(input[1].trim());
		
		try {
			bw.write("SEARCH: OK " + list.size() + "\n");
			
			for(BirthdayDataModel model : list){
				bw.write(model.toString() + "\n");
			}
		} catch (IOException e) {
			try {
				bw.write("SEARCH: ERROR IOException\n");
			} catch (IOException e1) {}
		}
	}
	
	
}