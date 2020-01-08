package com.json.builder


import com.json.traits.JsonFactory
import com.json.traits.JsonUnit
import com.json.traits.JsonKey
import com.json.traits.JsonValue
import com.json.traits.JsonMapTrait
import scala.annotation.tailrec
import com.json.traits.JsonKey
import com.json.traits.JsonMapTrait
import com.json.traits.JsonListTrait
import com.json.traits.JsonBuilderTrait

class JsonIteratorBuilderV2(factory:JsonFactory) extends JsonBuilderTrait(factory)  {
  
  // Light weight Stack implementation
  private class Stack[T] {
    
    private class Node(val value:T, val tail:Node)
    
    private var stack:Node = null
    
    def push(v:T) = {
        stack = new Node(v,stack)
    }
    
    def pop() = {
        val value = stack.value
        stack = stack.tail
        value
    }
    
    def isEmpty():Boolean = {
      stack match {
        case null => true
        case _ => false
      }
    }
    
  }
  
  // Stack symbols to be inserted
  val S = 1
  val P = 2
  val Kn = 3
  val Ks = 4
  val Kb = 5
  val Vn = 6
  val Vs = 7
  val Vb = 8
  val L = 9
  val A = 10
  val VAV = 10
  val VAS = 11
  val VAE = 12
  val VSS = 13
  val VSE = 14
  val VSV = 15
  
  // Stack that will be 
  private val symbolStack: Stack[Int] = new Stack[Int]()

  private val stringValueStack: Stack[String] = new Stack[String]()
  
  
  private val numberValueStack: Stack[Double] = new Stack[Double]() 
  
  private val booleanValueStack: Stack[Boolean] = new Stack[Boolean]()
  
  
  

  override def pushS() = symbolStack.push(S)

  override def pushP() = symbolStack.push(P)

  override def pushL() = symbolStack.push(L)

  override def pushA() = symbolStack.push(A)

  override def pushVAS() = symbolStack.push(VAS)

  override def pushVAE() = {

    val tv = createArray(null, null)
    if (tv == null) {
      throw new IllegalStateException("Wrong sequence of builder.push() called, inner value should be empty JsonListTrait()")
    }
    subJsonArrayStack.push(tv)
  }

  /**
   * This function handles nested Arrays and JsonMaps that are already in the stack
   */
  
  private var subJsonArrayStack: Stack[JsonListTrait] = new Stack[JsonListTrait]()
  @tailrec
  private def createArray(tempValue: JsonValue, tempA: JsonListTrait): JsonListTrait = {
    symbolStack.pop match {
      case `Vs` => {
        val value = stringValueStack.pop()
        createArray(factory.createJsonStringEntity(value), tempA)
      }

      case `Vn` => {
        val value = numberValueStack.pop()
        createArray(factory.createJsonNumberEntity(value), tempA)
      }
      case `Vb` => {
        val value = booleanValueStack.pop()
        createArray(factory.createJsonBooleanEntity(value), tempA)
      }
      case `A` => {
        createArray(null, constructA(tempValue, tempA))
      }
      case `VSV` => {
        val subJsonMapStackHead = subJsonMapStack.pop()
        createArray(subJsonMapStackHead, tempA)
      }
      case `VAV` => {
        val subJsonArrayHead = subJsonArrayStack.pop()
        createArray(subJsonArrayHead, tempA)
      }
      case `VAS` => {symbolStack.push(VAV); tempA}
    }
  }


  override def pushVSS() = symbolStack.push(VSS)

  override def pushVSE() = {

    val tv = visitNode(null, null, null, null, null)
    if (tv == null) {
      throw new IllegalStateException("Wrong sequence of builder.push() called, inner value should be empty JsonMapTrait()")
    }
    subJsonMapStack.push(tv)
  }

  private var subJsonMapStack: Stack[JsonMapTrait] = new Stack[JsonMapTrait]()
  @tailrec
  private def visitNode(tempS: JsonMapTrait, tempP: (JsonKey, JsonValue),
    tempL: List[(JsonKey, JsonValue)], tempKey: JsonKey, tempValue: JsonValue): JsonMapTrait = {
    symbolStack.isEmpty() match {
      case true => tempS
      case false => symbolStack.pop() match {
        case `Ks` => {
          val value = stringValueStack.pop()
          visitNode(tempS, tempP, tempL, factory.createJsonStringEntity(value), tempValue)
        }
        case `Kn` => {
          val value = numberValueStack.pop()
          visitNode(tempS, tempP, tempL, factory.createJsonNumberEntity(value), tempValue)
        }
        case `Kb` => {
          val value = booleanValueStack.pop()
          visitNode(tempS, tempP, tempL, factory.createJsonBooleanEntity(value) , tempValue)
        }
        case `Vs` => {
          val value = stringValueStack.pop()
          visitNode(tempS, tempP, tempL, tempKey, factory.createJsonStringEntity(value))
        }
        case `Vn` => {
          val value = numberValueStack.pop()
          visitNode(tempS, tempP, tempL, tempKey, factory.createJsonNumberEntity(value))
        }
        case `Vb` => {
          val value = booleanValueStack.pop()
          visitNode(tempS, tempP, tempL, tempKey, factory.createJsonBooleanEntity(value))
        }
        case `P` => visitNode(tempS, (tempKey, tempValue), tempL, null, null)
        case `L` => visitNode(tempS, null, (constructL(tempP, tempL)), tempKey, tempValue)
        case `S` => visitNode((constructS(tempP, tempL)), null, null, tempKey, tempValue)
        case `VSS` => {
          symbolStack.push(VSV)
          tempS
        }
        case `VSV` => {
          val subJsonMapStackHead = subJsonMapStack.pop()
          visitNode(tempS, tempP, tempL, tempKey, subJsonMapStackHead)
        }
        case `VAV` => {
          val subJsonArrayHead = subJsonArrayStack.pop()
          visitNode(tempS, tempP, tempL, tempKey, subJsonArrayHead)
        }
      }
    }
  }

  override def pushK(str: String) = {symbolStack.push(Ks);  stringValueStack.push(str)}

  override def pushV(str: String) = {symbolStack.push(Vs); stringValueStack.push(str)}

  override def pushK(num: Double) = {symbolStack.push(Kn); numberValueStack.push(num)}

  override def pushV(num: Double) = {symbolStack.push(Vn); numberValueStack.push(num)}
  
  override def pushK(b:Boolean) = {symbolStack.push(Kb); booleanValueStack.push(b)}
  
  override def pushV(b:Boolean) = {symbolStack.push(Vb); booleanValueStack.push(b)}
  
  
  
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
  private def constructS(tempP: (JsonKey, JsonValue), tempL: List[(JsonKey, JsonValue)]): JsonMapTrait = (tempP, tempL) match {
    case (null, null) => factory.createJsonMap(Map())
    case (null, Nil) => factory.createJsonMap(Map())
    case (_, _) => factory.createJsonMap((tempP :: tempL).toMap)
  }
  /**
   * construction rule for A -> (V L)
   */
  private def constructA(tempV: JsonValue, tempA: JsonListTrait) = (tempV, tempA) match {
    case (null, null) => factory.createJsonList(Nil)
    case (_, null) => factory.createJsonList(tempV :: List[JsonValue]())
    case (_, _) => factory.createJsonList(tempV :: List[JsonValue]() ++ tempA.getValue())
  }


  /**
   * Traverse the stack from top to bottom and build the
   * Json
   */
  override def build(): JsonMapTrait = {
    if (subJsonMapStack.isEmpty() == true)
      throw new IllegalStateException("Wrong sequence of builder.push() called, JsonMapStack can never be emprty !")
    subJsonMapStack.pop()
  }
  
}