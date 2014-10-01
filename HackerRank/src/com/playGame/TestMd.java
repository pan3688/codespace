package com.playGame;

import java.util.Calendar;

public class TestMd {

	public static void main(String[] args) throws InterruptedException {
		
		int i = -47;
		
		System.out.println(i%2);
		
		long start = System.currentTimeMillis();
		
		System.out.println("Start\t" + start);
		long current = 0L;
		
		do{
			
			Thread.sleep(50);
			current = System.currentTimeMillis();
			
			System.out.println(current);
		}while((current-start)/1000 < 1);
		
		System.out.println("Done!");
	}
	
}
