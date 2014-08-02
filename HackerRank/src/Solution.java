import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;


public class Solution {
	
	public static ArrayList<Long> scoresPlayer1,scoresPlayer2 = null;
	
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
			playGame(bricks,0,0,true);
			
			//System.out.println(Collections.max(scores));
			for(long score : scoresPlayer1)
				System.out.println(score);
		}
	}
	// i must have separate counters for player 1 and player 2 scores
	// i may try to make it like a consumer provider problem
	public static void playGame(long[] bricks,long score,int offset,boolean countOrNot){
		
		if(offset >= bricks.length){
			
			if(countOrNot)
				scoresPlayer1.add(score);
			
			return;
		}
		
		long score1 = 0;
		if(offset < bricks.length)
			score1 = bricks[offset];
		/*else
			return;*/
		
		long score2 = 0;
		if(offset+1 < bricks.length)
			score2 = bricks[offset] + bricks[offset + 1];
		else if(offset + 1 == bricks.length)
			score2 = bricks[offset];
		/*else
			return;*/
		
		long score3=0;
		
		if(offset+2 < bricks.length)
			score3 = bricks[offset] + bricks[offset + 1] + bricks[offset + 2];
		else if(offset + 2 == bricks.length)
			score3 = bricks[offset] + bricks[offset + 1];
		else if(offset + 1 == bricks.length)
			score3 = bricks[offset];
				
		if(countOrNot){
			score1 += score;
			score2 += score;
			score3 += score;
		}
		boolean nextPlayer = false;
		
		if(countOrNot)
			nextPlayer = false;
		else
			nextPlayer = true;
		
		if(countOrNot){
			playGame(bricks, score1, offset + 1, false);
			playGame(bricks, score2, offset + 1, false);
			playGame(bricks, score3, offset + 1, false);
			playGame(bricks, score1, offset + 2, false);
			playGame(bricks, score2, offset + 2, false);
			playGame(bricks, score3, offset + 2, false);
			playGame(bricks, score1, offset + 3, false);
			playGame(bricks, score2, offset + 3, false);
			playGame(bricks, score3, offset + 3, false);
		}else{
			playGame(bricks, score, offset + 1, true);
			playGame(bricks, score, offset + 2, true);
			playGame(bricks, score, offset + 3, true);
		}
	}
}
