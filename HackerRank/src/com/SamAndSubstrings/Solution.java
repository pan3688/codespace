package com.SamAndSubstrings;

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
		long time1 = System.currentTimeMillis();
		long sum = subNew(inputNumbers);
		long time2 = System.currentTimeMillis();
		
		System.out.println(sum);
//		System.out.println((time2-time1)/1000);
	}
	
	public static long subNew(int[] inputNum){
		long sum = 0;
		int length = inputNum.length;

		long[] array1 = new long[length];
		array1[0] = 1;
		for(int i=1;i<length; i++){
			
			array1[i] = (array1[i-1] * 10 +1) % 1000000007; 
		//	System.out.println(array1[i]);
		}
		
		for(int i=0;i<length;i++){
		//	sum = sum.multiply(a);
			sum += inputNum[i] * array1[length-i-1] * (i+1);
			sum= sum % 1000000007;
		}
		return sum;
		
	}
}
