package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if(c==0 || c==r) 1 else pascal(c-1,r-1) + pascal(c,r-1)
  }
  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
		var j = 0;
				
		def innerBalanced(j:Int, chars:List[Char]):Boolean = {
		
			if(!chars.isEmpty && chars.head == '('){
				innerBalanced(j + 1,chars.tail)
			}else if(!chars.isEmpty && chars.head == ')'){
				if(j-1<0)
					return false
				else
					innerBalanced(j - 1,chars.tail)
			}else if(!chars.isEmpty){
				innerBalanced(j,chars.tail)
			}else{
				true
			}
		}
		
		if(!chars.isEmpty)
			innerBalanced(j,chars)
		else
			true
  }
  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    
    val coinList : List[Int] = coins.sorted
  	
  	def innerCountChange(coinsList: List[Int],m: Int, money:Int): Int = {
  		
  		if(money == 0)
  			return 1
  		
  		if(money < 0)
  			return 0
  			
  		if(m<=0 && money >=0)
  			return 0
  			
 		return innerCountChange(coinsList,m - 1,money) + innerCountChange(coinsList, m, money - coinsList(m-1))
  	
  	}
  	
  	innerCountChange(coinList, coinList.size, money)
    
  }
}
