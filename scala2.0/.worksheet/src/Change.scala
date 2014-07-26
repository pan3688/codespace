object Change {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(59); 
  println("Welcome to the Scala worksheet");$skip(555); 
  
  def countChange(money: Int, coins: List[Int]): Int = {
  
  	val coinList : List[Int] = coins.sorted
  	
  	def innerCountChange(coinsList: List[Int],m: Int, money:Int): Int = {
//  		println("m\t\t\t" + m)
// 		println("money\t" + money)
  		
  		if(money == 0)
  			return 1
  		
  		
  		if(money < 0)
  			return 0
  			
  		if(m<=0 && money >=0)
  			return 0
  			
 		return innerCountChange(coinsList,m - 1,money) + innerCountChange(coinsList, m, money - coinsList(m-1))
  	
  	}
  	
  	innerCountChange(coinList, coinList.size, money)
  
  };System.out.println("""countChange: (money: Int, coins: List[Int])Int""");$skip(30); val res$0 = 
  
  countChange(4,List(1,2));System.out.println("""res0: Int = """ + $show(res$0));$skip(48); val res$1 = 
  countChange(300,List(500,5,50,100,20,200,10));System.out.println("""res1: Int = """ + $show(res$1))}
 
}
