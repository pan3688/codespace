import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Solution {
	//static int acc = 0;
	static int a = 0,j=0;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input = br.readLine();

		int[] inputNumbers = new int[input.length()];
		
		for(int i=0;i<input.length();i++){
			
			inputNumbers[i] = Integer.parseInt(input.charAt(i)+"");
			
		}
		substrings(inputNumbers,0,inputNumbers.length-1);
		System.out.println(a);
	//	System.out.println("number is " + arrayNum(inputNumbers,0,inputNumbers.length-1));
	}
	
	public static int substrings(int[] inputNumbers,int i,int length){
		System.out.println("a\t" + a);
		a += arrayNum(inputNumbers,i,length);
		j+= 1;
		
		if(i==length || length==0)
			return 0;
		
		return substrings(inputNumbers,i++,length) + substrings(inputNumbers, i, length-1);
	}
	
	public static int arrayNum(int[] inputNumbers,int start,int length){
		
		int acc = 0;
		for(int i=start; i < inputNumbers.length; i++){
			
			acc = acc*10 + inputNumbers[i]; 
			
		}
		
		return acc;
	}
}
