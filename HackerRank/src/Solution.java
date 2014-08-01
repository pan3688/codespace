import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;


public class Solution {
	
	public static ArrayList<Long> scores = null;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int t = Integer.parseInt(br.readLine());
		
		for(int i=0; i<t;i++){
			int n = Integer.parseInt(br.readLine());
			
			String line = br.readLine();
			scores = new ArrayList<Long>();
			
			String []brickstrings = line.split(" ");
			long[] bricks = new long[brickstrings.length];
			
			for(int j=0;j<brickstrings.length;j++){
				bricks[j] = Long.parseLong(brickstrings[j]);
			}
			playGame(bricks,0,0,true);
			
			//System.out.println(Collections.max(scores));
			for(long score : scores)
				System.out.println(score);
		}
	}
	
	public static void playGame(long[] bricks,long score,int offset,boolean countOrNot){
		
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
		/*else
			return;*/
				
		if(countOrNot){
			score1 += score;
			score2 += score;
			score3 += score;
		}
		
		if(offset >= bricks.length && countOrNot){
			scores.add(score);
			return;
		}

		if(countOrNot){
			playGame(bricks, score1, offset + 1, false);
			playGame(bricks, score2, offset + 2, false);
			playGame(bricks, score3, offset + 3, false);
		}else{
			playGame(bricks, score1, offset + 1, true);
			playGame(bricks, score2, offset + 2, true);
			playGame(bricks, score3, offset + 3, true);
		}
	}
}
