package com.playGame;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;


public class Solution {
	
	public static ArrayList<Long> scoresPlayer1,scoresPlayer2 = null;
	public static Long acc = 0L;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int t = Integer.parseInt(br.readLine());
		
		for(int i=0; i<t;i++){
			int n = Integer.parseInt(br.readLine());
			
			String line = br.readLine();
			scoresPlayer1 = new ArrayList<Long>();
			scoresPlayer2 = new ArrayList<Long>();
			
			String []brickstrings = line.split(" ");
			long[] bricks = new long[brickstrings.length];
			
			for(int j=0;j<brickstrings.length;j++){
				bricks[j] = Long.parseLong(brickstrings[j]);
			}
			
			for(long bricktotal : bricks)
				acc += bricktotal;
			
			//System.out.println(acc);
			playGame(bricks,0,0,0,true);
			//playGame(bricks,0,0,0,false);
			
			//long player2max = Collections.max(scoresPlayer2);
			//System.out.println(acc-player2max);
			for(int k=0;k<scoresPlayer1.size();k++){
				System.out.println("Player1\t" + scoresPlayer1.get(k) +"\tPlayer2\t" + scoresPlayer2.get(k));
			}
			//System.out.println("Player 1\t" + scoresPlayer1.size() + "\tPlayer 2\t" + scoresPlayer2.size());
		}
	}
	// i must have separate counters for player 1 and player 2 scores
	// i may try to make it like a consumer provider problem
	public static void playGame(long[] bricks,long scoreP1,long scoreP2,int offset,boolean countOrNot){
		
		if(offset >= bricks.length){
			 
		//	if(scoreP1 > scoreP2){
				scoresPlayer1.add(scoreP1);
				scoresPlayer2.add(scoreP2);
		//	}
			
			return;
		}
		
		long score1 = 0;
		if(offset < bricks.length){
			score1 = bricks[offset];
		}
		
		if(countOrNot){
			score1 += scoreP1;
			playGame(bricks, score1,scoreP2, offset + 1, false);
		}else{
			score1 += scoreP2;
			playGame(bricks, scoreP1,score1, offset + 1, true);
		}
		
		long score2 = 0;
		if(offset+1 < bricks.length){
			score2 = bricks[offset] + bricks[offset + 1];
		}else if(offset + 1 == bricks.length){
			score2 = bricks[offset];
		}
		
		if(countOrNot){
			score2 += scoreP1;
			playGame(bricks, score2,scoreP2, offset + 2, false);
		}else{
			score2 += scoreP2;
			playGame(bricks, scoreP1,score2, offset + 2, true);
		}
		
		long score3=0;
		
		if(offset+2 < bricks.length){
			score3 = bricks[offset] + bricks[offset + 1] + bricks[offset + 2];
		}else if(offset + 2 == bricks.length){
			score3 = bricks[offset] + bricks[offset + 1];
		}else if(offset + 1 == bricks.length)
			score3 = bricks[offset];
		
		if(countOrNot){
			score3 += scoreP1;
			playGame(bricks, score3,scoreP2, offset + 3, false);
		}else{
			score3 += scoreP2;
			playGame(bricks, scoreP1,score3, offset + 3, true);
		}
		
	}
}
