package com.json.builder

import com.json.traits.JsonFactory
import com.json.traits.JsonUnit
import com.json.traits.JsonValue
import com.json.traits.JsonMap
import scala.annotation.tailrec
import com.json.traits.JsonKey
import com.json.traits.JsonBuilder
import com.json.traits.JsonList

/**
 *
 * Iterator Builder for the Json Object

 *
 * S -> (P -> (k , v) , L -> (P -> (k , A -> (v , M -> (e , M -> e))), L -> P))
 *
 * Binary tree in order traversal information is available when parsing the language
 * The objective is to traverse it the reverse way (stack) and create the final JsonObject
 *
 * each grammer rule have a distinct function call which will similarly build a
 * abstract syntax tree. But in this case we are building the syntax tree walk reverse of inorder walk
 * The reverse walk allows us to build the tree from ground up.
 *
 *
 *
 * For further optimization to reduce amount of object that exist in the stack for a time
 * we process the stack immediately a end symbol class for a object is encountered.
 *
 * So for example in case of { k:{k1:[v1,v2]} ..}
 * we immediately process the first object that we encounter and set it on a stack.
 * This will keep the size of the stack we are using to build the object smaller
 *
 * NOTE: Should only be used with Smaller JSON with lesser number of key value entry i.e <1000000ps 
 * 			 For the use case of bigger JSON, A parser and builder with lazy evaluation will be added
 * 			 Which should be able to work with stream data , converting the stream into Json in real time
 * 
 * 
 */

class JsonIteratorBuilder(factory: JsonFactory) extends JsonBuilder(factory) {

  private var stack: List[TokenElement] = Nil

  abstract class TokenElement {}

  private case object S extends TokenElement

  private case object P extends TokenElement

  private case class Kn(num: Double) extends TokenElement

  private case class Ks(str: String) extends TokenElement
  
  private case class Kb(b:Boolean) extends TokenElement

  private case class Vs(str: String) extends TokenElement

  private case class Vn(num: Double) extends TokenElement
  
  private case class Vb(b:Boolean) extends TokenElement

  private case object L extends TokenElement

  private case object A extends TokenElement

  private case object VAV extends TokenElement // array value evaluated

  private case object VAS extends TokenElement // array value start

  private case object VAE extends TokenElement // array value end

  private case object VSS extends TokenElement // json map start

  private case object VSE extends TokenElement // json map end

  private case object VSV extends TokenElement // json value evaluated


  override def pushS() = stack = S :: stack

 
  override def pushP() = stack = P :: stack

 
  override def pushL() = stack = L :: stack

 
  override def pushA() = stack = A :: stack

 
  override def pushVAS() = stack = VAS :: stack


  override def pushVAE() = {

    val (tv, tst) = createArray(null, null, this.stack)
    if (tv == null) {
      throw new IllegalStateException("Wrong sequence of builder.push() called, inner value should be empty JsonListTrait()")
    }
    subJsonArrayStack = tv :: subJsonArrayStack
    this.stack = tst
  }

  /**
   * This function handles nested Arrays and JsonMaps that are already in the stack
   */

  private var subJsonArrayStack: List[JsonList] = Nil
  @tailrec
  private def createArray(tempValue: JsonValue, tempA: JsonList, stack: List[TokenElement]): (JsonList, List[TokenElement]) = {
    stack.head match {
      case Vs(null) => createArray(factory.createJsonStringEntity(null), tempA, stack.tail)
      case Vs(str: String) => createArray(factory.createJsonStringEntity(str), tempA, stack.tail)
      case Vn(num: Double) => createArray(factory.createJsonNumberEntity(num), tempA, stack.tail)
      case Vb(b) => createArray(factory.createJsonBooleanEntity(b), tempA, stack.tail)
      case A => createArray(null, constructA(tempValue, tempA), stack.tail)
      case VSV => {
        val subJsonMapStackHead = subJsonMapStack.head
        subJsonMapStack = subJsonMapStack.tail
        createArray(subJsonMapStackHead, tempA, stack.tail)
      }
      case VAV => {
        val subJsonArrayHead = subJsonArrayStack.head
        subJsonArrayStack = subJsonArrayStack.tail
        createArray(subJsonArrayHead, tempA, stack.tail)
      }
      case VAS => (tempA, VAV :: stack.tail)
    }
  }


  override def pushVSS() = stack = VSS :: stack

  override def pushVSE() = {

    val (tv, tst) = visitNode(null, null, null, null, null, this.stack)
    if (tv == null) {
      throw new IllegalStateException("Wrong sequence of builder.push() called, inner value should be empty JsonMapTrait()")
    }
    subJsonMapStack = tv :: subJsonMapStack
    this.stack = tst
  }


  private var subJsonMapStack: List[JsonMap] = Nil
  @tailrec
  private def visitNode(tempS: JsonMap, tempP: (JsonKey, JsonValue),
    tempL: List[(JsonKey, JsonValue)], tempKey: JsonKey, tempValue: JsonValue, stack: List[TokenElement]): (JsonMap, List[TokenElement]) = {
    stack match {
      case Nil => (tempS, Nil)
      case _ => stack.head match {
        case Ks(str: String) => visitNode(tempS, tempP, tempL, factory.createJsonStringEntity(str), tempValue, stack.tail)
        case Kn(num: Double) => visitNode(tempS, tempP, tempL, factory.createJsonNumberEntity(num), tempValue, stack.tail)
        case Kb(b) => visitNode(tempS, tempP, tempL, factory.createJsonBooleanEntity(b) , tempValue, stack.tail)
        case Vs(str: String) => visitNode(tempS, tempP, tempL, tempKey, factory.createJsonStringEntity(str), stack.tail)
        case Vs(null) => visitNode(tempS, tempP, tempL, tempKey, factory.createJsonStringEntity(null), stack.tail)
        case Vn(num: Double) => visitNode(tempS, tempP, tempL, tempKey, factory.createJsonNumberEntity(num), stack.tail)
        case Vb(b) => visitNode(tempS, tempP, tempL, tempKey, factory.createJsonBooleanEntity(b), stack.tail)
        case P => visitNode(tempS, (tempKey, tempValue), tempL, null, null, stack.tail)
        case L => visitNode(tempS, null, (constructL(tempP, tempL)), tempKey, tempValue, stack.tail)
        case S => visitNode((constructS(tempP, tempL)), null, null, tempKey, tempValue, stack.tail)
        case VSS => (tempS, VSV :: stack.tail)
        case VSV => {
          val subJsonMapStackHead = subJsonMapStack.head
          subJsonMapStack = subJsonMapStack.tail
          visitNode(tempS, tempP, tempL, tempKey, subJsonMapStackHead, stack.tail)
        }
        case VAV => {
          val subJsonArrayHead = subJsonArrayStack.head;
          subJsonArrayStack = subJsonArrayStack.tail
          visitNode(tempS, tempP, tempL, tempKey, subJsonArrayHead, stack.tail)
        }
      }
    }
  }

  override def pushK(str: String) = stack = new Ks(str) :: stack

  override def pushV(str: String) = stack = new Vs(str) :: stack

  override def pushK(num: Double) = stack = new Kn(num) :: stack

  override def pushV(num: Double) = stack = new Vn(num) :: stack
  
  override def pushK(b:Boolean) = stack = new Kb(b) :: stack
  
  override def pushV(b:Boolean) = stack = new Vb(b) :: stack
  
  
  
  /**
   * constrcution rule for L -> (P L)
   */
  private def constructL(tempP: (JsonKey, JsonValue), tempL: List[(JsonKey, JsonValue)]) = (tempP, tempL) match {
    case (null, null) => Nil
    case (null, Nil) => Nil
    case (_, _) => tempP :: tempL
  }

  /**
   * construction rule for S -> (P L)
   */
  private def constructS(tempP: (JsonKey, JsonValue), tempL: List[(JsonKey, JsonValue)]): JsonMap = (tempP, tempL) match {
    case (null, null) => factory.createJsonMap(Map())
    case (null, Nil) => factory.createJsonMap(Map())
    case (_, _) => factory.createJsonMap((tempP :: tempL).toMap)
  }
  /**
   * construction rule for A -> (V L)
   */
  private def constructA(tempV: JsonValue, tempA: JsonList) = (tempV, tempA) match {
    case (null, null) => factory.createJsonList(Nil)
    case (_, null) => factory.createJsonList(tempV :: List[JsonValue]())
    case (_, _) => factory.createJsonList(tempV :: List[JsonValue]() ++ tempA.getValue())
  }


  /**
   * Traverse the stack from top to bottom and build the
   * Json
   */
  override def build(): JsonMap = {
    if (subJsonMapStack == Nil)
      throw new IllegalStateException("Wrong sequence of builder.push() called, JsonMapStack can never be emprty !")
    subJsonMapStack.head
  }
}