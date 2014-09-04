package hw1;

import hw1.Database.BirthdayDatabase;
import hw1.Exceptions.InvalidDateFormatException;
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
	
	public static void main(String[] args) {
		
		String command = "";
		try {
			
			br = new BufferedReader(new FileReader(new File("in.txt")));
			bw = new BufferedWriter(new FileWriter(new File("out.txt")));
			
			command = br.readLine().trim();
			
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
						throw new Exception(command.split(" ")[0].trim() + ": ERROR UNKNOWN_COMMAND\n");
				} catch (Exception e) {
					if(e.getMessage() != null)
						bw.write(e.getMessage());
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
	
	public static void load(String command) throws Exception{
		
		String[] input = command.split(" ");
		
		String csvLine = "";
		String[] csvParts = null;
		
		BirthdayDataModel model = null;
		int count = 0;
		BufferedReader brDatabaseLoad = null;
		
		boolean load = false;
		
		if(input.length != 2)
			throw new Exception(input[0].trim() + ": ERROR WRONG_ARGUMENT_COUNT\n");
		
		try {
			brDatabaseLoad = new BufferedReader(new FileReader(input[1].trim()));
			
			csvLine = brDatabaseLoad.readLine();
			
			while(csvLine != null){
				
				csvParts = csvLine.split(",");
				
				/*
				 * in case the input csv file contains any duplicates,
				 * the load process will continue after the duplicate record
				 */
				try {
					model = new BirthdayDataModel(csvParts[0].trim(), csvParts[1].trim(), csvParts[2].trim());
					
					load = BirthdayDatabase.add(model);
					
					if(load)
						count++;
					else
						throw new Exception("LOAD: ERROR DUPLICATE_ENTRY\n");
				} catch (InvalidDateFormatException e) {
					try {
						bw.write("LOAD: ERROR INVALID_DATE_FORMAT " + e.getCausedBy()+ "\n");
					}catch(IOException e1){}
				} catch (ParseException e) {
					try {
						bw.write("LOAD: ERROR INVALID_DATE\n");
					} catch (IOException e1) {};
				} catch (Exception e) {
					try {
						if(e.getMessage() != null)
							bw.write(e.getMessage());
					}catch(IOException e1){}
				}
				
				csvLine = brDatabaseLoad.readLine();
			}
			
			bw.write("LOAD: OK " + count + "\n");
			
		} catch (FileNotFoundException e) {
			try {
				bw.write("LOAD: ERROR FILE_NOT_FOUND\n");
			} catch (IOException e1) {}
		} catch (IOException e) {
			try {
				bw.write("LOAD: ERROR IOException\n");
			} catch (IOException e1) {}
		} finally{
			
			try {
				if(brDatabaseLoad != null)
					brDatabaseLoad.close();
			} catch (IOException e) {}
			
		}
		
	}

	public static void store(String command) throws Exception{
		
		String[] input = command.trim().split(" ");
		
		if(input.length != 2)
			throw new Exception(input[0].trim() + ": ERROR WRONG_ARGUMENT_COUNT\n");
		
		boolean done = false;
		
		try {
			done = BirthdayDatabase.store(input[1].trim());
			
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
				bw.write("CLEAR: OK\n");
			
		} catch (IOException e) {
			try {
				bw.write("CLEAR: ERROR IOException\n");
			} catch (IOException e1){}
		}
		
	}
	
	public static void add(String command) throws Exception{
		
		String[] input = command.split(" ");
		
		if(input.length != 4)
			throw new Exception(input[0].trim() + ": ERROR WRONG_ARGUMENT_COUNT\n");
		
		boolean done = false;
		
		try {
			BirthdayDataModel model = new BirthdayDataModel(input[3].trim(),input[2].trim(),input[1].trim());
			
			done = BirthdayDatabase.add(model);
			
			if(done)
				bw.write("ADD: OK " + model.getFirstName() + " " + model.getLastName() + "\n");
			else
				throw new Exception("ADD: ERROR DUPLICATE_ENTRY \n");
			
		} catch (InvalidDateFormatException e) {
			try {
				bw.write("ADD: ERROR INVALID_DATE_FORMAT\n");
			} catch (IOException e1) {}
		} catch (ParseException e) {
			try {
				bw.write("ADD: ERROR INVALID_DATE\n");
			} catch (IOException e1) {}
		} catch (IOException e) {
			try {
				bw.write("ADD: ERROR IOException\n");
			} catch (IOException e1) {}
		} catch (Exception e) {
			try {
				if(e.getMessage() != null)
					bw.write(e.getMessage());
			} catch (IOException e1) {}
		}
		
		
		
	}
	
	public static void show(String command) throws Exception{
		
		String[] input = command.trim().split(" ");
		
		if(input.length != 2)
			throw new Exception(input[0].trim() + ": ERROR WRONG_ARGUMENT_COUNT\n");
		
		List<BirthdayDataModel> records = null;
		
		try {
			records = BirthdayDatabase.query(input[1]);
			
			bw.write("SHOW: OK " + records.size() + "\n");
			
			for(BirthdayDataModel model : records){
				bw.write(model.toString() + "\n");
			}
			
		} catch (NumberFormatException e) {
			try {
				bw.write("SHOW: ERROR NOT_INT\n");
			} catch (IOException e1) {}
		} catch (Exception e) {
			try {
				if(e.getMessage() != null)
					bw.write(e.getMessage());
			} catch (IOException e1) {}
		} 
		
		
	}
	
	public static void update(String command) throws Exception{
		
		boolean found = false;
		
		try {
			found = BirthdayDatabase.update(command);
			
			String[] input = command.trim().split(" ");
			
			if(found && input.length == 4)
				bw.write("UPDATE: OK " + input[1].trim() + " " + input[2].trim() + "\n");
			else if(found && input.length == 6)
				bw.write("UPDATE: OK " + input[4].trim() + " " + input[5].trim() + "\n");
			else if(!found)
				throw new Exception("UPDATE: ERROR RECORD_NOT_FOUND\n");
			
		} catch (ParseException e) {
			try {
				bw.write("UPDATE: ERROR INVALID_DATE\n");
			} catch (IOException e1) {}
		} catch (InvalidDateFormatException e) {
			try {
				bw.write("UPDATE: ERROR INVALID_DATE_FORMAT\n");
			} catch (IOException e1) {}
		} catch (IOException e) {
			try {
				bw.write("UPDATE: ERROR IOException\n");
			} catch (IOException e1) {}
		} catch (Exception e) {
			try {
				if(e.getMessage() != null)
					bw.write(e.getMessage());
			} catch (IOException e1) {}
		}
	}
	
	public static void search(String command) throws Exception{
		
		String[] input = command.trim().split(" ");
		
		if(input.length != 2)
			throw new Exception(input[0].trim() + ": ERROR WRONG_ARGUMENT_COUNT\n");
		
		List<BirthdayDataModel> list = BirthdayDatabase.searchNeedle(input[1].trim());
		
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