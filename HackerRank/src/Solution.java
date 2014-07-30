import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution {
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input = br.readLine();

		int[] inputNumbers = new int[input.length()];
		
		for(int i=0;i<input.length();i++){
			
			inputNumbers[i] = Integer.parseInt(input.charAt(i)+"");
			
		}
		long sum = substringNew(inputNumbers);
		System.out.println((long)(sum % (Math.pow(10, 9) + 7)));;
	}
	
	public static long substringNew(int[] inputNumbers){
		
		long sum = 0;
		int length = inputNumbers.length;
		
		for(int i=0;i<length;i++){
			for(int j=length-i; j>0;j--){
				if(inputNumbers[i]!=0)
					sum =(long)(sum + inputNumbers[i]*Math.pow(10, j-1)*(i+1));
				
				sum = (long)(sum % (Math.pow(10, 9) + 7));
			}
		}
		return sum;
	}
	
	
}
