package hw1.Exceptions;

public class WrongArgumentCountException extends Exception {

	private String command;
	
	public WrongArgumentCountException(String command) {
		this.command = command.split(" ")[0].trim();
	}
	
	public String getCommand() {
		return command;
	}
}
