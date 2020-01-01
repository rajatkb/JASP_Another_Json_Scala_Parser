package com.json.basic

import com.json.traits.JsonUnit
import com.json.traits.JsonNumberTrait
import com.json.traits.JsonValue
import com.json.traits.JsonKey

/**
 * :JsonNumber => Double
 *
 *  Represents a number entity in a Json, can be extended for creating new prototypes
 */

object JsonNumber {
  def apply(value: Double) = new JsonNumber(value)
}

case class JsonNumber(value: Double) extends JsonNumberTrait(value) {
  override def toString() = value.toString()
  override def copy(v: Any) = new JsonNumber(v.asInstanceOf[Double])

}



