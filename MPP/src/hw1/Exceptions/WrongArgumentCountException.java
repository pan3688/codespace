package hw1.Exceptions;

public class WrongArgumentCountException extends Exception {

	private String command;
	
	public WrongArgumentCountException(String command) {
		this.command = command;
	}
	
	public String getCommand() {
		return command;
	}
}
