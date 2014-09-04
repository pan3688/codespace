package hw1.Model;

import hw1.Exceptions.InvalidDateFormatException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/*
 * The class performs data validation for DateOfBirth field
 */
public class BirthdayDataModel {

	private Date dateOfBirth;
	private String lastName;
	private String firstName;
	
	/*
	 * SimpleDateFormat checks for invalid dates such as 11/31/2011, 14/23/2008
	 * Pattern is used to check the date format, every format other than MM/dd/yyyy is invalid
	 */
	private static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	private static Pattern p = Pattern.compile("\\d+/\\d+/\\d{4}");
	
	
	static{
		formatter.setLenient(false);
	}
	
	public BirthdayDataModel(String dateOfBirth, String lastName, String firstName) throws ParseException, InvalidDateFormatException {
		super();
		
		if(isDateValid(dateOfBirth)){
			this.dateOfBirth = formatter.parse(dateOfBirth);
			this.lastName = lastName;
			this.firstName = firstName;
		}
		
	}
	
	public void setDate(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Date getDate() {
		return dateOfBirth;
	}
	public String getLastName() {
		return lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	
	@Override
	public String toString() {
		return formatter.format(dateOfBirth) + "," + lastName + "," + firstName;
	}
	
	public static boolean isDateValid(String date) throws ParseException, InvalidDateFormatException{
		
		if(!p.matcher(date).matches())
			throw new InvalidDateFormatException(date);
		
		formatter.parse(date);
		
		return true;
	}
}
