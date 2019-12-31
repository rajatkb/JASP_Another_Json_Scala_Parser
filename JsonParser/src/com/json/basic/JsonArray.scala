package com.json.basic

import com.json.traits.JsonUnit
import com.json.traits.JsonValue
import com.json.traits.JsonListTrait
/**
 * :JsonArray => Seq[JsonValue]
 * 	It contains the construct of array of JsonValues, The class can be extends and new concrete classes can be passed to use
 * 	the parser.
 */
class JsonArray (value:Seq[JsonValue]) extends JsonListTrait(value) {
  override def toString() = "[" + value.mkString(",") + "]"
  override def copy(v:Any) = new JsonArray(v.asInstanceOf[Seq[JsonValue]])
 
}