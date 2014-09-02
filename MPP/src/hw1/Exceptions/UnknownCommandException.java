package hw1.Exceptions;

public class UnknownCommandException extends Exception {

	private String command;

	public UnknownCommandException(String command) {
		super();
		this.command = command.split(" ")[0].trim();
	}
	
	public String getCommand() {
		return command;
	}
	
}
