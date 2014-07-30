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
		long sum = substrings(inputNumbers);
		System.out.println((long)(sum % (Math.pow(10, 9) + 7)));;
	//	System.out.println("number is " + arrayNum(inputNumbers,0,inputNumbers.length-1));
	}
	
	public static long substrings(int[] inputNumbers){
		
		/*if(i==length || length==0)
			return 0;
		
		return substrings(inputNumbers,i++,length) + substrings(inputNumbers, i, length-1);*/
		long sum = 0;
		for(int i=0;i<inputNumbers.length;i++){
			for(int j=1;j+i <= inputNumbers.length; j++){
				sum += arrayNum(inputNumbers, i, j);
				sum = (long)(sum % (Math.pow(10, 9) + 7));
			}
		}
		return sum;
	}
	
	public static long arrayNum(int[] inputNumbers,int start,int length){
		
		long acc = 0;
		for(int i=0; i < length; i++){
			
			acc = acc*10 + inputNumbers[i + start]; 
			
		}
	//	System.out.println(acc);
		return acc;
	}
}
