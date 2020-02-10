package com.json.basic

import com.json.traits.JsonUnit
import com.json.traits.JsonValue
import com.json.traits.JsonList
import com.json.traits.JsonKey
import com.json.traits.JsonWriteable

/**
 * :JsonArray => Seq[JsonValue]
 * 	It contains the construct of array of JsonValues, The class can be extends and new concrete classes can be passed to use
 * 	the parser.
 */

object JsonArray {
  def apply(a1: JsonValue, a2: JsonValue*) = new JsonArray(a1 +: a2)
  def apply(value: Seq[JsonValue] = Nil) = new JsonArray(value)
  implicit def value2Array(a:JsonValue) = a.asInstanceOf[JsonArray]
}

class JsonArray(value: Seq[JsonValue] = Nil) extends JsonList(value)  {
  override def toString() = "[" + value.mkString(",") + "]"
  def this(a1: JsonValue, a2: JsonValue*) = this(a1 +: a2)
}

