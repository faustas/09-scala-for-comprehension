import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object Main {

  def main(args: Array[String]): Unit = {

    // add 1 to the provided Int
    def addOne(n: Int): Int = n + 1
    // multiply the provided Int by 2
    def multTwo(n: Int): Int = n * 2
    // reduce the provided Int by 1
    def minusOne(n: Int): Int = n - 1

    val l = List(1, 2, 3, 4)

    // Mapping over list and executing different methods
    val r1 = l.map(addOne).map(multTwo).map(minusOne)
    // same as: l.map(l1 => addOne(l1)).map(l2 => multTwo(l2)).map(l3 => minusOne(l3))

    println(r1) // List(3, 5, 7, 9)

    // with for-comprehension
    val r2 =
      for {
        l1 <- l
        l2 = addOne(l1)
        l3 = multTwo(l2)
        result = minusOne(l3)
      } yield result

    println(r2) // List(3, 5, 7, 9)

    // Return a future as result
    def getFuture(i: Int): Future[Int] = Future.successful(i + 1)

    val f =
      for {
        rf <- getFuture(2)
      } yield rf

    // Blocking outside the future -> we should NEVER do this
    val r3 = Await.result(f, 5.seconds)

    println(r3) // 3
  }

}
