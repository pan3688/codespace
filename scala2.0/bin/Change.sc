object Change {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
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
  
  }                                               //> countChange: (money: Int, coins: List[Int])Int
  
  countChange(4,List(1,2))                        //> res0: Int = 3
  countChange(300,List(500,5,50,100,20,200,10))   //> res1: Int = 1022
 
}