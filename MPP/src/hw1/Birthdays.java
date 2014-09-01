package hw1;

import hw1.Exceptions.InvalidDateFormatException;
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
import java.util.ArrayList;

public class Birthdays {
		
	private static BufferedReader br = null;
	private static BufferedWriter bw = null;
	private static String command = "";
	private static ArrayList<BirthdayDataModel> inMemoryDb = null;
	
	public static void main(String[] args) {
		
		try {
			
			br = new BufferedReader(new FileReader(new File("in.txt")));
			bw = new BufferedWriter(new FileWriter(new File("out.txt")));
			inMemoryDb = new ArrayList<BirthdayDataModel>();
			
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
				} catch (WrongArgumentCountException e) {
					try {
						bw.write(e.getCommand() + ": ERROR " + "WRONG_ARGUMENT_COUNT" + "\n");
					} catch (IOException e1) {}
				}
				
				command = br.readLine();
			}
		}catch (IOException e) {
			
		} finally{
			
			try {
				br.close();
				bw.flush();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public static void load(String command) throws WrongArgumentCountException{
		
		String[] input = command.split(" ");
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
				System.out.println(model);
				
				inMemoryDb.add(model);
				
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
		}finally{
			
			try {
				if(brDatabaseLoad != null)
					brDatabaseLoad.close();
			} catch (IOException e) {}
			
		}
		
	}

	public static void store(String command) throws WrongArgumentCountException{
		
		String[] input = command.split(" ");
		BufferedWriter storeDB = null;
		
		if(input.length != 2)
			throw new WrongArgumentCountException(command);
		
		try {
			storeDB = new BufferedWriter(new FileWriter(new File(input[1].trim())));
		
			for(BirthdayDataModel bdays : inMemoryDb){
				
				storeDB.write(bdays.toString() + "\n");
				
			}
			storeDB.write("STORE: OK " + inMemoryDb.size() + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				bw.write("STORE: ERROR ");
			} catch (IOException e1){}
		}finally{
			
			try {
				storeDB.flush();
				storeDB.close();
			} catch (IOException e) {}
			
		}
		
	}
	
	public static void clear(String command){
		
		try {
			inMemoryDb = new ArrayList<BirthdayDataModel>();
			
			bw.write("CLEAR: OK " + "\n");
		} catch (IOException e) {
			try {
				bw.write("CLEAR: ERROR " + "\n");
			} catch (IOException e1){}
		}
		
	}
	
	public static void add(String command){}
	
	public static void show(String command){}
	
	public static void update(String command){}
	
	public static void search(String command){}
	
	
}