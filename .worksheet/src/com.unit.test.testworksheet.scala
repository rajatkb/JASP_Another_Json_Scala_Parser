package com.unit.test


object testworksheet {


/*
Current implmentation uses immediate resolution of JsonObject to maps this implmentation
is modified for lazy evaluatipn maps for faster parsing

*/


/// interesting implementation //
// def readFile(src:String):Stream[String] = {
//   val file = new BufferedReader(new FileReader(src))
//   def readFile$(file:BufferedReader):Stream[String] = {
//     val res = file.readLine()
//     if(res != null)
//         res #:: readFile$(file)
//     else{
//       file.close()
//       Stream(res)
//     }
//
//   }
//   readFile$(file)
// }

///************************ RECURSIVE VARIANT OF CRAFTING THE JSON ********************************* /////
/// stackoverflow after 4000 recursive calls.
	
//      def S() = (list:List[(JsonKey,JsonValue)]) =>{
//    //        println("at S: "+(new Throwable()).getStackTrace().mkString(", "));
//            new JsonObject(list.toMap)
//        }
//
//      def B1(f: List[(JsonKey,JsonValue)] => JsonMapTrait) = (k:JsonKey,v:JsonValue) => (subList:List[(JsonKey,JsonValue)]) => {
//    //          println("at B1 " +(new Throwable()).getStackTrace().mkString(", "));
//              f(List((k,v)):::subList)
//            }
//
//      def T(f:(JsonKey,JsonValue) => List[(JsonKey,JsonValue)] => JsonMapTrait) =  (k:JsonKey,v:JsonValue) => {
//    //      println("at T "+(new Throwable()).getStackTrace().mkString(", "));
//           f(k,v)
//        }
//
//      def K(num: Double , f:(JsonKey,JsonValue) => List[(JsonKey,JsonValue)] => JsonMapTrait) = (v:JsonValue) => {
//    //        println("at K "+(new Throwable()).getStackTrace().mkString(", "));
//            f(new JsonNumber(num) , v)
//          }
//
//      def V1(num:Double , f: JsonValue => List[(JsonKey,JsonValue)] => JsonMapTrait) =  f(new JsonNumber(num))
//
//
//
//      def V2(f: JsonValue => List[(JsonKey,JsonValue)] => JsonMapTrait) =  (v:JsonValue) => (subList:List[JsonValue]) => {
//          f(new JsonArray(List(v):::subList))
//      }
//
//
//      def L2(f :List[(JsonKey,JsonValue)] => JsonMapTrait) = f(Nil)
//
//
//      var v = V1(4,K(1 , T(B1 (V1(4,K(3 , T(B1(S()))))))))
//
//
//
//      print(v(Nil))
  
  
/**

This is a stack based implementation where operations are objects
It works but is not fast enough, gets extremely slow when creating Json
We dont need AST for this implementation work
**/

//package com.json.builder
//
//import com.json.traits.JsonFactory
//import com.json.traits.JsonUnit
//import com.json.struct.JsonPair
//import com.json.struct.JsonPair
//import com.json.struct.JsonObject
//import com.json.traits.JsonKey
//import com.json.traits.JsonValue
//import com.json.traits.JsonValue
//import com.json.struct.JsonString
//import com.json.traits.JsonMapTrait
//import scala.annotation.tailrec
//import com.json.traits.JsonMapTrait
//import com.logger.Logger
//
//
//
//
//
///**
// * JsonIteratorBuilder class uses a operation stack to create the end result Json
// * Each
// * 	pushElement() operation pushes and operation down a stack
// *
// * Which is then evaluated using a tail recursive function and the accumulator is
// * used to accumulate results in a list.
// * In order to keep the builder sepparate from the Lexer and more akin to parser
// * This design was chosen. However using a purely character based builder may also speed up the
// * operation where the stack is not made of operation object but characters
// * and the operation is consolidated in one single recursive function
// *
// */
//class JsonIteratorBuilder(factory: JsonFactory) {
//
//
//
//  abstract class JsonIteratorElement(val next:JsonIteratorElement) {
//    def create(list:List[JsonUnit]):List[JsonUnit]
//  }
//
//  class MapElement(next:JsonIteratorElement) extends JsonIteratorElement(next) {
//    override def create(list:List[JsonUnit]) = List[JsonUnit](factory.createJsonMap(list.map(f => f.getValue()).asInstanceOf[List[(JsonKey,JsonValue)]].toMap))
//  }
//
//  class StringKeyElement(str:String, next:JsonIteratorElement) extends JsonIteratorElement(next) {
//    override def create(list:List[JsonUnit]) =  factory.createJsonPair( factory.createJsonStringEntity(str), list.head.asInstanceOf[JsonValue])::list.tail
//  }
//
//  class NumberKeyElement(num:Double, next:JsonIteratorElement) extends JsonIteratorElement(next) {
//    override def create(list:List[JsonUnit]) =  factory.createJsonPair( factory.createJsonNumberEntity(num), list.head.asInstanceOf[JsonValue])::list.tail
//  }
//
//  class StringValueElement(str:String, next:JsonIteratorElement) extends JsonIteratorElement(next) {
//     override def create(list:List[JsonUnit]) = factory.createJsonStringEntity(str)::list
//  }
//
//  class NumberValueElement(num:Double, next:JsonIteratorElement) extends JsonIteratorElement(next) {
//     override def create(list:List[JsonUnit]) = factory.createJsonNumberEntity(num)::list
//  }
//
//  class ArrayElement(next:JsonIteratorElement) extends JsonIteratorElement(next) {
//    override def create(list:List[JsonUnit]) = {
//
//        @tailrec def createList(list:List[JsonUnit] , acc:List[JsonUnit]):List[JsonUnit] = list match {
//          case l::ls if l.getValue() != Nil => createList(ls , acc:::List[JsonUnit](l))
//          case l::ls if l.getValue() == Nil => factory.createJsonList(acc.asInstanceOf[List[JsonValue]])::ls
//        }
//
//        createList(list, Nil)
//    }
//
//  }
//
//  class ArrayDelimeterElement(next:JsonIteratorElement) extends JsonIteratorElement(next) {
//    override def create(list:List[JsonUnit]) = List[JsonUnit](factory.createJsonList(Nil)):::list
//  }
//
//  class NullElement(next:JsonIteratorElement) extends JsonIteratorElement(next) {
//    override def create(list:List[JsonUnit]) = List[JsonUnit](factory.createJsonStringEntity(null)):::list
//  }
//
//
//
//  def pushMapElement(stack:JsonIteratorElement) =  new MapElement(stack)
//
//  def pushKey(str:String , stack:JsonIteratorElement) =  new StringKeyElement(str,stack)
//
//  def pushKey(num:Double , stack:JsonIteratorElement) = new NumberKeyElement(num , stack)
//
//  def pushValue(str:String , stack:JsonIteratorElement) =  new StringValueElement(str,stack)
//
//  def pushValue(num:Double , stack:JsonIteratorElement) = new NumberValueElement(num , stack)
//
//  def pushArray(stack:JsonIteratorElement) = new ArrayElement(stack)
//
//  def pushNull(stack:JsonIteratorElement) = new NullElement(stack)
//
//  def pushArrayDelim(stack:JsonIteratorElement) = new ArrayDelimeterElement(stack)
//
//  /**
//   * Traverse the stack from top to bottom and build the
//   * Json
//   */
//  def build(stack:JsonIteratorElement):JsonMapTrait = {
//
//    @tailrec
//    def visitNode(stack:JsonIteratorElement, list:List[JsonUnit]):List[JsonUnit] = stack match{
//      case null => list
//      case s:JsonIteratorElement => {
//        Logger.debug(list.toString())
//        visitNode(s.next , s.create(list))
//      }
//    }
//
//    val res = visitNode(stack, Nil)
//    res.head.asInstanceOf[JsonMapTrait]
//  }
//}




	
}
