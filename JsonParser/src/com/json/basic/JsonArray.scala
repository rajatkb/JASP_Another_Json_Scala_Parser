package com.json.basic

import com.json.traits.JsonUnit
import com.json.traits.JsonValue
import com.json.traits.JsonListTrait
import com.json.traits.JsonValue
import com.json.traits.JsonKey

/**
 * :JsonArray => Seq[JsonValue]
 * 	It contains the construct of array of JsonValues, The class can be extends and new concrete classes can be passed to use
 * 	the parser.
 */

object JsonArray {
  def apply(a1: JsonValue, a2: JsonValue*) = new JsonArray(a1 +: a2)
  def apply(value: Seq[JsonValue] = Nil) = new JsonArray(value)
}

class JsonArray(value: Seq[JsonValue] = Nil) extends JsonListTrait(value) {
  override def toString() = "[" + value.mkString(",") + "]"
  override def copy(v: Any) = new JsonArray(v.asInstanceOf[Seq[JsonValue]])

  def this(a1: JsonValue, a2: JsonValue*) = this(a1 +: a2)

}

