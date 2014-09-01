package hw1.Exceptions;

public class InvalidDateFormatException extends Exception {
	private String causedBy;
	
	public InvalidDateFormatException(String causedBy) {
		this.causedBy= causedBy;
	}	
	
	public String getCausedBy() {
		// TODO Auto-generated method stub
		return this.causedBy;
	}
	
}
