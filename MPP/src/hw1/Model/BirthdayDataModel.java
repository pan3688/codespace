package hw1.Model;

import hw1.Exceptions.InvalidDateFormatException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class BirthdayDataModel {

	private Date dateOfBirth;
	private String lastName;
	private String firstName;
	private static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	private static Pattern p = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
	
	
	static{
		formatter.setLenient(false);
	}
	
	public BirthdayDataModel(String dateOfBirth, String lastName, String firstName) throws ParseException, InvalidDateFormatException {
		super();
		
		if(p.matcher(dateOfBirth).matches()){
			this.dateOfBirth = formatter.parse(dateOfBirth);
			this.lastName = lastName;
			this.firstName = firstName;
		}else
			throw new InvalidDateFormatException(dateOfBirth);
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
		// TODO Auto-generated method stub
		return formatter.format(dateOfBirth) + "," + firstName + "," + lastName;
	}
}
