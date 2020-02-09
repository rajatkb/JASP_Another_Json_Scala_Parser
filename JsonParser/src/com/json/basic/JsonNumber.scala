package com.json.basic

import com.json.traits.JsonUnit
import com.json.traits.JsonNumeric
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
  implicit def value2Number(a:JsonValue) = a.asInstanceOf[JsonNumber]
}

case class JsonNumber(value: Double) extends JsonNumeric(value) {
  override def toString() = value.toString()
}



