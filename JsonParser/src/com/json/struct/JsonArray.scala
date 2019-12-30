package com.json.struct

import com.json.traits.JsonUnit
import com.json.traits.JsonValue
import com.json.traits.JsonListTrait
/**
 * :JsonArray => Iterable[JsonValue]
 * 	It contains the construct of array of JsonValues, The class can be extends and new concrete classes can be passed to use
 * 	the parser.
 */
class JsonArray (value:Iterable[JsonValue]) extends JsonListTrait(value) {
  override def toString() = "[" + value.mkString(",") + "]"
  override def getValue() = value
  override def copy(v:Any) = new JsonArray(v.asInstanceOf[Iterable[JsonValue]])
}