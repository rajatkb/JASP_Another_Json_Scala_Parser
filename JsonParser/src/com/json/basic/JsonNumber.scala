package com.json.basic

import com.json.traits.JsonUnit
import com.json.traits.JsonNumberTrait
import com.json.traits.JsonValue
import com.json.traits.JsonKey
import com.json.traits.JsonWriteable

/**
 * :JsonNumber => Double
 *
 *  Represents a number entity in a Json, can be extended for creating new prototypes
 */

object JsonNumber {
  def apply(value: Double) = new JsonNumber(value)
  implicit def value2Number(a:JsonValue) = a match { case e:JsonNumber => e; 
                                                     case _ => throw new ClassCastException("Cannot cast "+a.getClass +" to "+this.getClass)}
}

case class JsonNumber(value: Double) extends JsonNumberTrait(value) {
  override def toString() = value.toString()
  override def copy(v: Double) = new JsonNumber(v)

}



