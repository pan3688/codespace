import java.util.concurrent.ThreadLocalRandom;


public class Test {
	
	public static void main(String[] args) {
		
		ThreadLocalRandom myRand = ThreadLocalRandom.current();
		
		for(int i = 0;i < 100 ;i++){
			int j = myRand.nextInt();
			System.out.println("j\t"+j+"\t" + (j & 100) + "\tj\t" + j);
		}
	}
}
